/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.latticegas;
import org.opensourcephysics.display.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class FastLatticeGas implements Drawable {
  public double flowSpeed = 0.2;
  public double velocityScale = 1;
  public int spatialAveragingLength = 1;
  private int[][] lattice, newLattice;
  private int lx_4, lx, ly;              // length of lattice
  private double numParticles;
  static final double SQRT3_OVER2 = Math.sqrt(3)/2;
  static final double SQRT2 = Math.sqrt(2);
  static final int RIGHT = 1|1<<8|1<<16|1<<24;
  static final int RIGHT_DOWN = 2|2<<8|2<<16|2<<24;
  static final int LEFT_DOWN = 4|4<<8|4<<16|4<<24;
  static final int LEFT = 8|8<<8|8<<16|8<<24;
  static final int LEFT_UP = 16|16<<8|16<<16|16<<24;
  static final int RIGHT_UP = 32|32<<8|32<<16|32<<24;
  static final int STATIONARY = 64|64<<8|64<<16|64<<24;
  static final int BARRIER = 128|128<<8|128<<16|128<<24;
  static final int NUM_CHANNELS = 7;     // maximum number of particles per site
  static final int NUM_BITS = 8;         // 7 channels bits plus 1 barrier bit per site
  static final int SITE_MASK = (1<<8)-1; // all site bits filled in the right-most site
  static final int NUM_RULES = 1<<8; // total number of possible site configurations
  static final int SITES_PER_INT = 4; // number of sites packed into an integer
  static final double ux[] = {
    1.0, 0.5, -0.5, -1.0, -0.5, 0.5, 0
  };
  static final double uy[] = {
    0.0, -SQRT3_OVER2, -SQRT3_OVER2, 0.0, SQRT3_OVER2, SQRT3_OVER2, 0
  };
  static final double[] vx, vy;       // averaged velocities for every site configuration
  static final int[] rule;

  static { // set rule table
    rule = new int[NUM_RULES];
    // shortcuts for channel indices to the right-most site
    int M = SITE_MASK;
    int RI = RIGHT&M, DR = RIGHT_DOWN&M, DL = LEFT_DOWN&M;
    int LE = LEFT&M, UL = LEFT_UP&M, UR = RIGHT_UP&M;
    int S = STATIONARY&M, B = BARRIER&M;
    // default rule is the identity rule
    for(int i = 0;i<B;i++) {
      rule[i] = i;
    }
    // three particle zero momentum rules
    rule[UL|DL|RI] = UR|LE|DR;
    rule[UR|LE|DR] = UL|DL|RI;
    // three particle rules with unperturbed particle
    rule[UR|UL|DL] = UL|LE|RI;
    rule[UL|LE|RI] = UR|UL|DL;
    rule[UR|UL|DR] = UR|LE|RI;
    rule[UR|LE|RI] = UR|UL|DR;
    rule[UR|DL|DR] = LE|DR|RI;
    rule[LE|DR|RI] = UR|DL|DR;
    rule[UL|DL|DR] = LE|DL|RI;
    rule[LE|DL|RI] = UL|DL|DR;
    rule[UR|DL|RI] = UL|DR|RI;
    rule[UL|DR|RI] = UR|DL|RI;
    rule[UL|LE|DR] = UR|LE|DL;
    rule[UR|LE|DL] = UL|LE|DR;
    // two particle cyclic rules
    rule[LE|RI] = UR|DL;
    rule[UR|DL] = UL|DR;
    rule[UL|DR] = LE|RI;
    // four particle cyclic rules
    rule[UR|UL|DL|DR] = UR|LE|DL|RI;
    rule[UR|LE|DL|RI] = UL|LE|DR|RI;
    rule[UL|LE|DR|RI] = UR|UL|DL|DR;
    // stationary particle creation rules
    rule[UL|RI] = UR|S;
    rule[UR|LE] = UL|S;
    rule[UL|DL] = LE|S;
    rule[LE|DR] = DL|S;
    rule[DL|RI] = DR|S;
    rule[DR|UR] = RI|S;
    rule[UL|LE|DL|DR|RI] = UR|LE|DL|DR|S;
    rule[UR|LE|DL|DR|RI] = UL|DL|DR|RI|S;
    rule[UR|UL|DL|DR|RI] = UR|LE|DR|RI|S;
    rule[UR|UL|LE|DR|RI] = UR|UL|DL|RI|S;
    rule[UR|UL|LE|DL|RI] = UR|UL|LE|DR|S;
    rule[UR|UL|LE|DL|DR] = UL|LE|DL|RI|S;
    // add all rules indexed with a stationary particle (dual rules)
    for(int i = 0;i<S;i++) {
      rule[i^(UR|UL|LE|DL|DR|RI|S)] = rule[i]^(UR|UL|LE|DL|DR|RI|S);
    }
    // add rules to bounce back at barriers
    for(int i = B;i<NUM_RULES;i++) {
      int high_bits = i&(LE|UL|UR);
      int low_bits = i&(RI|DR|DL);
      rule[i] = B|(high_bits>>3)|(low_bits<<3);
    }
  }
  static { // set velocities
    // for every particle configuration i, calculate total net velocity
    // in vx[i], vy[i]
    vx = new double[NUM_RULES];
    vy = new double[NUM_RULES];
    for(int i = 0;i<NUM_RULES;i++) {
      for(int dir = 0;dir<NUM_CHANNELS;dir++) {
        if((i&(1<<dir))!=0) {
          vx[i] += ux[dir];
          vy[i] += uy[dir];
        }
      }
    }
  }
  public void initialize(int _lx, int _ly, double density) {
    lx = _lx-_lx%4;
    ly = _ly-_ly%2;
    lx_4 = lx/4;
    numParticles = lx*ly*NUM_CHANNELS*density;
    lattice = new int[lx_4][ly];
    newLattice = new int[lx_4][ly];
    for(int i = 0;i<lx_4;i++) {
      lattice[i][1] = lattice[i][ly-2] = BARRIER; // wall at top and bottom
      for(int j = 2;j<ly-2;j++) {
        int randomSite = Math.random()<density ? (-1^BARRIER) : 0;
        lattice[i][j] = randomSite;               // random particle configuration
      }
    }
    for(int j = 3*ly/10;j<7*ly/10;j++) {
      lattice[2*lx_4/10][j] |= BARRIER&SITE_MASK; // obstruction at left
    }
  }

  public void step() {
    // move all particles forward
    for(int i = 0;i<lx_4;i++) {
      int[] newLattice_left = newLattice[(i-1+lx_4)%lx_4];
      int[] newLattice_cent = newLattice[i];
      int[] newLattice_rght = newLattice[(i+1)%lx_4];
      for(int j = 2;j<ly-1;j += 2) {
        int site = lattice[i][j];
        newLattice_cent[j] |= (site>>>NUM_BITS)&RIGHT|(site<<NUM_BITS)&LEFT|site&(STATIONARY|BARRIER);
        newLattice_left[j] |= (site>>>(3*NUM_BITS))&LEFT;
        newLattice_rght[j] |= (site<<(3*NUM_BITS))&RIGHT;
        newLattice_cent[j+1] |= site&RIGHT_UP|(site<<NUM_BITS)&LEFT_UP;
        newLattice_cent[j-1] |= site&RIGHT_DOWN|(site<<NUM_BITS)&LEFT_DOWN;
        newLattice_left[j+1] |= (site>>>(3*NUM_BITS))&LEFT_UP;
        newLattice_left[j-1] |= (site>>>(3*NUM_BITS))&LEFT_DOWN;
      }
      for(int j = 1;j<ly-1;j += 2) {
        int site = lattice[i][j];
        newLattice_cent[j] |= (site>>>NUM_BITS)&RIGHT|(site<<NUM_BITS)&LEFT|site&(STATIONARY|BARRIER);
        newLattice_left[j] |= (site>>>(3*NUM_BITS))&LEFT;
        newLattice_rght[j] |= (site<<(3*NUM_BITS))&RIGHT;
        newLattice_cent[j+1] |= site&LEFT_UP|(site>>>NUM_BITS)&RIGHT_UP;
        newLattice_cent[j-1] |= site&LEFT_DOWN|(site>>>NUM_BITS)&RIGHT_DOWN;
        newLattice_rght[j+1] |= (site<<(3*NUM_BITS))&RIGHT_UP;
        newLattice_rght[j-1] |= (site<<(3*NUM_BITS))&RIGHT_DOWN;
      }
    }
    // handle collisions
    double vxTotal = 0;
    for(int i = 0;i<lx_4;i++) {
      for(int j = 0;j<ly;j++) {
        int site = newLattice[i][j];
        int s1 = rule[site&SITE_MASK];
        int s2 = rule[(site>>>1*NUM_BITS)&SITE_MASK];
        int s3 = rule[(site>>>2*NUM_BITS)&SITE_MASK];
        int s4 = rule[(site>>>3*NUM_BITS)&SITE_MASK];
        lattice[i][j] = s1+(s2<<1*NUM_BITS)+(s3<<2*NUM_BITS)+(s4<<3*NUM_BITS);
        newLattice[i][j] = 0;
        vxTotal += vx[s1]+vx[s2]+vx[s3]+vx[s4];
      }
    }
    // inject horizontal momentum into random sites
    int injections = (int) ((flowSpeed*numParticles-vxTotal)/4);
    for(int k = 0;k<Math.abs(injections);k++) {
      int i = (int) (Math.random()*lx_4);
      int j = (int) (Math.random()*ly);
      int L = LEFT&SITE_MASK, R = RIGHT&SITE_MASK;
      if((lattice[i][j]&(L|R))==(injections>0 ? L : R)) {
        lattice[i][j] ^= L|R;
      }
    }
  }

  boolean isBarrier(int i, int j) {
    return(getSite(i, j)&BARRIER)!=0;
  }

  int getSite(int i, int j) {
    return(lattice[i/4][j]>>((3-i%4)*NUM_BITS))&SITE_MASK;
  }

  public void draw(DrawingPanel panel, Graphics g) {
    if(lattice==null) {
      return;
    }
    int s = spatialAveragingLength;
    Graphics2D g2 = (Graphics2D) g;
    AffineTransform toPixels = panel.getPixelTransform();
    Line2D.Double line = new Line2D.Double();
    for(int i = 0;i<lx;i++) {
      for(int j = 2;j<ly-2;j++) {
        double x = i+(j%2)*0.5;
        double y = j*SQRT3_OVER2;
        if(s==1) {
          g2.setPaint(Color.BLACK);
          for(int dir = 0;dir<NUM_CHANNELS;dir++) {
            if((getSite(i, j)&(1<<dir))!=0) {
              line.setLine(x, y, x+ux[dir]*0.4, y+uy[dir]*0.4);
              g2.draw(toPixels.createTransformedShape(line));
            }
          }
        }
        if(isBarrier(i, j)||s==1) {
          Circle c = new Circle(x, y);
          c.pixRadius = isBarrier(i, j) ? 2 : 1;
          c.draw(panel, g);
        }
      }
    }
    if(s==1) {
      return;
    }
    for(int i = 0;i<lx;i += s) {
      for(int j = 2;j<ly-2;j += s) {
        double x = i+s/2.0;
        double y = (j+s/2.0)*SQRT3_OVER2;
        double wx = 0, wy = 0;
        for(int m = i;m!=(i+s)%lx;m = (m+1)%lx) {
          for(int n = j;n!=(j+s)%ly;n = (n+1)%ly) {
            int site = getSite(m, n);
            wx += vx[site];
            wy += vy[site];
          }
        }
        Arrow a = new Arrow(x, y, velocityScale*wx/s, velocityScale*wy/s);
        a.setHeadSize(2);
        a.draw(panel, g);
      }
    }
  }
}

/* 
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.

 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must must also be be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
