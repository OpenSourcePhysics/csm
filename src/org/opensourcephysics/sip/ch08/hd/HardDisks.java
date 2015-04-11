/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch08.hd;
import java.awt.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

/**
 * HardDisks evolves a two-dimensional system of hard disks.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.1 revised 04/04/2006
 */
public class HardDisks implements Drawable {
  public double x[], y[], vx[], vy[];
  public double collisionTime[];
  public int partner[];
  public int N;
  public double Lx;
  public double Ly;
  public double keSum = 0, virialSum = 0;
  public int nextCollider, nextPartner;
  public double timeToCollision;
  public double t = 0;
  public double bigTime = 1.0E10;
  public double temperature;
  public int numberOfCollisions = 0;
  // end break

  // start break
  // initialize
  public void initialize(String configuration) {
    resetAverages();
    x = new double[N];
    y = new double[N];
    vx = new double[N];
    vy = new double[N];
    collisionTime = new double[N];
    partner = new int[N];
    if(configuration.equals("regular")) {
      setRegularPositions();
    } else {
      setRandomPositions();
    }
    setVelocities();
    for(int i = 0;i<N;++i) {
      collisionTime[i] = bigTime; // sets unknown collision times to a big number
    }
    // finds initial collision times for all particles
    for(int i = 0;i<N-1;i++) {
      for(int j = i+1;j<N;j++) {
        checkCollision(i, j);
      }
    }
  }

  public void resetAverages() {
    t = 0;
    virialSum = 0;
  }
  // end break

  // start break
  // setConfiguration
  public void setVelocities() {
    double vxSum = 0;
    double vySum = 0;
    for(int i = 0;i<N;++i) {
      vx[i] = Math.random()-1.0;
      vy[i] = Math.random()-1.0;
      vxSum += vx[i];
      vySum += vy[i];
    }
    double vxCM = vxSum/N;
    double vyCM = vySum/N;
    double v2Sum = 0;
    for(int i = 0;i<N;++i) { // zero center of mass velocity
      vx[i] -= vxCM;
      vy[i] -= vyCM;
      v2Sum += vx[i]*vx[i]+vy[i]*vy[i];
    }
    temperature = 0.5*v2Sum/N;
  }

  public void setRandomPositions() {
    boolean overlap;
    for(int i = 0;i<N;++i) {
      do {
        overlap = false;
        x[i] = Lx*Math.random();
        y[i] = Ly*Math.random();
        int j = 0;
        while((j<i)&&!overlap) {
          double dx = PBC.separation(x[i]-x[j], Lx);
          double dy = PBC.separation(y[i]-y[j], Ly);
          if(dx*dx+dy*dy<1.0) {
            overlap = true;
          }
          j++;
        }
      } while(overlap);
    }
  }

  public void setRegularPositions() {
    double dnx = Math.sqrt(N);
    int nx = (int) dnx;
    if(dnx-nx>0.00001) {
      nx++; // N is not a perfect square
    }
    double ax = Lx/nx; // distance between columns of molecules
    double ay = Ly/nx; // distance between rows
    int i = 0;
    int iy = 0;
    while(i<N) {
      for(int ix = 0;ix<nx;++ix) { // loops through disks in a row
        if(i<N) {
          y[i] = ay*(iy+0.5);
          if(iy%2==0) {            // even rows displaced from odd rows
            x[i] = ax*(ix+0.25);
          } else {
            x[i] = ax*(ix+0.75);
          }
          i++;
        }
      }
      iy++;
    }
  }
  // end break

  // start break
  // checkCollision
  public void checkCollision(int i, int j) {
    // consider collisions between i and j and periodic images of j
    double dvx = vx[i]-vx[j];
    double dvy = vy[i]-vy[j];
    double v2 = dvx*dvx+dvy*dvy;
    for(int xCell = -1;xCell<=1;xCell++) {
      for(int yCell = -1;yCell<=1;yCell++) {
        double dx = x[i]-x[j]+xCell*Lx;
        double dy = y[i]-y[j]+yCell*Ly;
        double bij = dx*dvx+dy*dvy;
        if(bij<0) {
          double r2 = dx*dx+dy*dy;
          double discriminant = bij*bij-v2*(r2-1);
          if(discriminant>0) {
            double tij = (-bij-Math.sqrt(discriminant))/v2;
            if(tij<collisionTime[i]) {
              collisionTime[i] = tij;
              partner[i] = j;
            }
            if(tij<collisionTime[j]) {
              collisionTime[j] = tij;
              partner[j] = i;
            }
          }
        }
      }
    }
  }
  // end break

  // start break
  // step
  public void step() {
    minimumCollisionTime(); // finds minimum collision time from list of collision times
    move(); // moves particles for time equal to minimum collision time
    t += timeToCollision;
    contact(); // changes velocities of two colliding particles
    // sets collision times to bigTime for those particles set to collide with
    // two colliding particles.
    setDefaultCollisionTimes();
    newCollisionTimes(); // finds new collision times between all particles
    // and the two colliding particles
    numberOfCollisions++;
  }

  public void minimumCollisionTime() {
    timeToCollision = bigTime; // sets collision time very large
    // so that can find minimum collision time
    for(int k = 0;k<N;k++) {
      if(collisionTime[k]<timeToCollision) {
        timeToCollision = collisionTime[k];
        nextCollider = k;
      }
    }
    nextPartner = partner[nextCollider];
  }

  public void move() {
    for(int k = 0;k<N;k++) {
      collisionTime[k] -= timeToCollision;
      x[k] = PBC.position(x[k]+vx[k]*timeToCollision, Lx);
      y[k] = PBC.position(y[k]+vy[k]*timeToCollision, Ly);
    }
  }

  public void contact() {
    // computes collision dynamics between nextCollider and nextPartner at contact
    double dx = PBC.separation(x[nextCollider]-x[nextPartner], Lx);
    double dy = PBC.separation(y[nextCollider]-y[nextPartner], Ly);
    double dvx = vx[nextCollider]-vx[nextPartner];
    double dvy = vy[nextCollider]-vy[nextPartner];
    double factor = dx*dvx+dy*dvy;
    double delvx = -factor*dx;
    double delvy = -factor*dy;
    vx[nextCollider] += delvx;
    vy[nextCollider] += delvy;
    vx[nextPartner] -= delvx;
    vy[nextPartner] -= delvy;
    virialSum += delvx*dx+delvy*dy;
  }

  public void setDefaultCollisionTimes() {
    collisionTime[nextCollider] = bigTime;
    collisionTime[nextPartner] = bigTime;
    // sets collision times to very big time for all particles set to collide with
    // the two colliding particles
    for(int k = 0;k<N;k++) {
      if(partner[k]==nextCollider) {
        collisionTime[k] = bigTime;
      } else if(partner[k]==nextPartner) {
        collisionTime[k] = bigTime;
      }
    }
  }

  public void newCollisionTimes() {
    // finds new collision times for all particles which were set to collide
    // with two colliding particles; also finds new collision
    // times for two colliding particles.
    for(int k = 0;k<N;k++) {
      if((k!=nextCollider)&&(k!=nextPartner)) {
        checkCollision(k, nextPartner);
        checkCollision(k, nextCollider);
      }
    }
    // end break
    // start break
    // pressure
  }

  /**
   * Computes the pressure.
   * Corrected 04/04/2006
   *
   * @return double
   */
  public double pressure() {
    return 1+virialSum/(2*t*N*temperature);
  }
  // end break

  /**
   * Draws the hard disks by painting circles at each hard disk location.
   *
   * @param drawingPanel DrawingPanel
   * @param g Graphics
   */
  public void draw(DrawingPanel drawingPanel, Graphics g) {
    double radius = 0.5;
    if(x==null) {
      return;
    }
    int pxRadius = Math.abs(drawingPanel.xToPix(radius)-drawingPanel.xToPix(0));
    int pyRadius = Math.abs(drawingPanel.yToPix(radius)-drawingPanel.yToPix(0));
    g.setColor(Color.red);
    for(int i = 0;i<N;i++) {
      int xpix = drawingPanel.xToPix(x[i])-pxRadius;
      int ypix = drawingPanel.yToPix(y[i])-pyRadius;
      g.fillOval(xpix, ypix, 2*pxRadius, 2*pyRadius);
    } // draw cell boundaries
    g.setColor(Color.black);
    int xpix = drawingPanel.xToPix(0);
    int ypix = drawingPanel.yToPix(Ly);
    int lx = drawingPanel.xToPix(Lx)-drawingPanel.xToPix(0);
    int ly = drawingPanel.yToPix(0)-drawingPanel.yToPix(Ly);
    g.drawRect(xpix, ypix, lx, ly);
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
