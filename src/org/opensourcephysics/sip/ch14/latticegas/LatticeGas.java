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

/**
 * LatticeGas models fluid flow using a cellular automaton based algorithm.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class LatticeGas implements Drawable {
  // input parameters from user
  public double flowSpeed;           // controls pressure
  public double arrowSize;           // size of velocity arrows displayed
  public int spatialAveragingLength; // spatial averaging of velocity
  public int Lx, Ly;                 // linear dimensions of lattice
  public int[][] lattice, newLattice;
  private double numParticles;
  static final double SQRT3_OVER2 = Math.sqrt(3)/2;
  static final double SQRT2 = Math.sqrt(2);
  static final int RIGHT = 1, RIGHT_DOWN = 2, LEFT_DOWN = 4;
  static final int LEFT = 8, LEFT_UP = 16, RIGHT_UP = 32;
  static final int STATIONARY = 64, BARRIER = 128;
  static final int NUM_CHANNELS = 7; // maximum number of particles per site
  static final int NUM_BITS = 8;     // 7 channel bits plus 1 barrier bit per site
  static final int NUM_RULES = 1<<8; // total number of possible site configurations = 2^8
  // 1 << 8 means move the zeroth bit over 8 places to the left to the eighth bit

  static final double ux[] = {
    1.0, 0.5, -0.5, -1.0, -0.5, 0.5, 0
  };
  static final double uy[] = {
    0.0, -SQRT3_OVER2, -SQRT3_OVER2, 0.0, SQRT3_OVER2, SQRT3_OVER2, 0
  };
  static final double[] vx, vy; // averaged velocities for every site configuration
  static final int[] rule;

  static { // set rule table
    // default rule is the identity rule
    rule = new int[NUM_RULES];
    for(int i = 0;i<BARRIER;i++) {
      rule[i] = i;
    }
    // abbreviations for channel bit indices
    int RI = RIGHT, RD = RIGHT_DOWN, LD = LEFT_DOWN;
    int LE = LEFT, LU = LEFT_UP, RU = RIGHT_UP;
    int S = STATIONARY;
    // three particle zero momentum rules
    rule[LU|LD|RI] = RU|LE|RD;
    rule[RU|LE|RD] = LU|LD|RI;
    // three particle rules with unperturbed particle
    rule[RU|LU|LD] = LU|LE|RI;
    rule[LU|LE|RI] = RU|LU|LD;
    rule[RU|LU|RD] = RU|LE|RI;
    rule[RU|LE|RI] = RU|LU|RD;
    rule[RU|LD|RD] = LE|RD|RI;
    rule[LE|RD|RI] = RU|LD|RD;
    rule[LU|LD|RD] = LE|LD|RI;
    rule[LE|LD|RI] = LU|LD|RD;
    rule[RU|LD|RI] = LU|RD|RI;
    rule[LU|RD|RI] = RU|LD|RI;
    rule[LU|LE|RD] = RU|LE|LD;
    rule[RU|LE|LD] = LU|LE|RD;
    // two particle cyclic rules
    rule[LE|RI] = RU|LD;
    rule[RU|LD] = LU|RD;
    rule[LU|RD] = LE|RI;
    // four particle cyclic rules
    rule[RU|LU|LD|RD] = RU|LE|LD|RI;
    rule[RU|LE|LD|RI] = LU|LE|RD|RI;
    rule[LU|LE|RD|RI] = RU|LU|LD|RD;
    // stationary particle creation rules
    rule[LU|RI] = RU|S;
    rule[RU|LE] = LU|S;
    rule[LU|LD] = LE|S;
    rule[LE|RD] = LD|S;
    rule[LD|RI] = RD|S;
    rule[RD|RU] = RI|S;
    rule[LU|LE|LD|RD|RI] = RU|LE|LD|RD|S;
    rule[RU|LE|LD|RD|RI] = LU|LD|RD|RI|S;
    rule[RU|LU|LD|RD|RI] = RU|LE|RD|RI|S;
    rule[RU|LU|LE|RD|RI] = RU|LU|LD|RI|S;
    rule[RU|LU|LE|LD|RI] = RU|LU|LE|RD|S;
    rule[RU|LU|LE|LD|RD] = LU|LE|LD|RI|S;
    // add all rules indexed with a stationary particle (dual rules)
    for(int i = 0;i<S;i++) {
      rule[i^(RU|LU|LE|LD|RD|RI|S)] = rule[i]^(RU|LU|LE|LD|RD|RI|S); // ^ is the exclusive or operator
    }
    // add rules to bounce back at barriers
    for(int i = BARRIER;i<NUM_RULES;i++) {
      int highBits = i&(LE|LU|RU); // & is bitwise and operator
      int lowBits = i&(RI|RD|LD);
      rule[i] = BARRIER|(highBits>>3)|(lowBits<<3);
    }
  }
  static { // set average site velocities
    // for every particle site configuration i, calculate total net velocity
    // and place in vx[i], vy[i]
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
  public void initialize(int Lx, int Ly, double density) {
    this.Lx = Lx;
    this.Ly = Ly-Ly%2;                         // Ly must be even
    numParticles = Lx*Ly*NUM_CHANNELS*density; // approximate total number of particles
    // density is the number of particles divided by the maximum number possible
    lattice = new int[Lx][Ly];
    newLattice = new int[Lx][Ly];
    int sevenParticleSite = ((1<<NUM_CHANNELS)-1); // equals 127
    for(int i = 0;i<Lx;i++) {
      lattice[i][1] = lattice[i][Ly-2] = BARRIER; // wall at top and bottom
      for(int j = 2;j<Ly-2;j++) {
        // occupy site by 0 or 7 particles, average occupation will be about the density
        int siteValue = Math.random()<density ? sevenParticleSite : 0;
        lattice[i][j] = siteValue; // random particle configuration
      }
    }
    for(int j = 3*Ly/10;j<7*Ly/10;j++) {
      lattice[2*Lx/10][j] = BARRIER; // obstruction toward the left
    }
  }

  public void step() {
    // move all particles forward
    for(int i = 0;i<Lx;i++) {
      // define the columns of a 2-dim array
      int[] left = newLattice[(i-1+Lx)%Lx];
      int[] cent = newLattice[i]; // use abbreviations to align expressions
      int[] rght = newLattice[(i+1)%Lx];
      for(int j = 1;j<Ly-2;j += 2) {
        // loop j in increments of 2 in order to decrease reads and writes of neighbors
        int site1 = lattice[i][j];
        int site2 = lattice[i][j+1];
        // move all particles in site1 and site2 to their neighbors
        rght[j-1] |= site1&RIGHT_DOWN;
        cent[j-1] |= site1&LEFT_DOWN;
        rght[j] |= site1&RIGHT;
        cent[j] |= site1&(STATIONARY|BARRIER)|site2&RIGHT_DOWN;
        left[j] |= site1&LEFT|site2&LEFT_DOWN;
        rght[j+1] |= site1&RIGHT_UP|site2&RIGHT;
        cent[j+1] |= site1&LEFT_UP|site2&(STATIONARY|BARRIER);
        left[j+1] |= site2&LEFT;
        cent[j+2] |= site2&RIGHT_UP;
        left[j+2] |= site2&LEFT_UP;
      }
    } // handle collisions, find average x velocity
    double vxTotal = 0;
    for(int i = 0;i<Lx;i++) {
      for(int j = 0;j<Ly;j++) {
        int site = rule[newLattice[i][j]]; // use collision rule
        lattice[i][j] = site;
        newLattice[i][j] = 0;              // reset newLattice values to 0
        vxTotal += vx[site];
      }
    }
    /* inject horizontal momentum at random sites to obtain desired flowSpeed.
 The magnitude of scale is arbitrary. If it is too small, then the actual flow speed
 will overshoot the desired value and be unstable. If scale is too large, the equilibration time will be too big and rate will change too slowly */
    int scale = 4;
    int injections = (int) ((flowSpeed*numParticles-vxTotal)/scale);
    for(int k = 0;k<Math.abs(injections);k++) {
      int i = (int) (Math.random()*Lx); // choose site at random
      int j = (int) (Math.random()*Ly);
      // flip direction of horizontally moving particle if possible
      if((lattice[i][j]&(RIGHT|LEFT))==((injections>0) ? LEFT : RIGHT)) {
        lattice[i][j] ^= RIGHT|LEFT;
      }
    }
  }

  public void draw(DrawingPanel panel, Graphics g) {
    if(lattice==null) {
      return;
    }
    // if s = 1 draw lattice and particle details explicitly
    // otherwise average velocity over an s by s square
    int s = spatialAveragingLength;
    Graphics2D g2 = (Graphics2D) g;
    AffineTransform toPixels = panel.getPixelTransform();
    Line2D.Double line = new Line2D.Double();
    for(int i = 0;i<Lx;i++) {
      for(int j = 2;j<Ly-2;j++) {
        double x = i+(j%2)*0.5;
        double y = j*SQRT3_OVER2;
        if(s==1) {
          g2.setPaint(Color.BLACK);
          for(int dir = 0;dir<NUM_CHANNELS;dir++) {
            if((lattice[i][j]&(1<<dir))!=0) {
              line.setLine(x, y, x+ux[dir]*0.4, y+uy[dir]*0.4);
              g2.draw(toPixels.createTransformedShape(line));
            }
          }
        }
        if((lattice[i][j]&BARRIER)==BARRIER||s==1) { // draw points at lattice sites
          Circle c = new Circle(x, y);
          c.pixRadius = ((lattice[i][j]&BARRIER)==BARRIER) ? 2 : 1;
          c.draw(panel, g);
        }
      }
    }
    if(s==1) {
      return;
    }
    for(int i = 0;i<Lx;i += s) {
      for(int j = 0;j<Ly;j += s) {
        double x = i+s/2.0;
        double y = (j+s/2.0)*SQRT3_OVER2;
        double wx = 0, wy = 0; // compute coarse grained average velocity
        for(int m = i;m!=(i+s)%Lx;m = (m+1)%Lx) {
          for(int n = j;n!=(j+s)%Ly;n = (n+1)%Ly) {
            wx += vx[lattice[m][n]];
            wy += vy[lattice[m][n]];
          }
        }
        Arrow a = new Arrow(x, y, arrowSize*wx/s, arrowSize*wy/s);
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
