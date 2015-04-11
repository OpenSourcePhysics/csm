/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;
import java.awt.Graphics;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

/**
 * RadiatingCharge models the fields from an accelerating charged particle.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class RadiatingCharge implements Drawable, Function {
  Circle circle = new Circle(0, 0, 5);
  double t = 0;                          // time
  double dt = 0.5;                       // time step
  int numPts = 0;                        // current number of points in storage
  double[][] path = new double[3][1024]; // storage for t,x,y
  double[] r = new double[2];
  double[] v = new double[2];
  double[] u = new double[2];
  double[] a = new double[2];
  double vmax;                           // maximum velocity for charge in units where c = 1

  /**
   *  MovingCharge constructor
   */
  public RadiatingCharge() {
    resetPath();
  }

  /**
   *  Resizes the arrays used to store the path.
   */
  private void resizePath() {
    int length = path[0].length;
    if(length>32768) { // drop half the points
      System.arraycopy(path[0], length/2, path[0], 0, length/2);
      System.arraycopy(path[1], length/2, path[1], 0, length/2);
      System.arraycopy(path[2], length/2, path[2], 0, length/2);
      numPts = length/2;
      return;
    }
    double[][] newPath = new double[3][2*length]; // new path
    System.arraycopy(path[0], 0, newPath[0], 0, length);
    System.arraycopy(path[1], 0, newPath[1], 0, length);
    System.arraycopy(path[2], 0, newPath[2], 0, length);
    path = newPath;
  }

  /**
   * Steps the position and stores a new space-time coordinate in the path.
   *
   * @param dt
   */
  void step() {
    t += dt;
    if(numPts>=path[0].length) {
      resizePath();
    }
    path[0][numPts] = t;
    path[1][numPts] = evaluate(t); // x position of charge
    path[2][numPts] = 0;
    numPts++;
  }

  /**
   * Resets the stored path.
   */
  void resetPath() {
    numPts = 0;
    t = 0;
    path = new double[3][1024];    // storage for t,x,y
    path[0][numPts] = t;
    path[1][numPts] = evaluate(t); // x position of charge
    path[2][numPts] = 0;
    numPts++; // initial position has been added
  }

  /**
   * Calculates the Coulomb field for a stationary particle.
   *
   * @param x
   * @param y
   * @param field
   */
  void electrostaticField(double x, double y, double[] field) {
    double dx = x-path[1][0];
    double dy = y-path[2][0];
    double r2 = dx*dx+dy*dy;
    double r3 = r2*Math.sqrt(r2);
    double ex = dx/r3;
    double ey = dy/r3;
    field[0] = ex;
    field[1] = ey;
    field[2] = 0; // magnetic field
  }

  /**
   * Returns the square of the space-time separation between a space-time coordinate
   * and a space-time point on the path.
   *
   * @param i  the point on the path
   * @param t  the current time
   * @param x  the x location
   * @param y  the y location
   *
   * @return
   */
  double dsSquared(int i, double t, double x, double y) {
    double dt = t-path[0][i];
    double dx = x-path[1][i];
    double dy = y-path[2][i];
    return dx*dx+dy*dy-dt*dt;
  }

  /**
   * Calculates the retarded field at the location (x,y).
   *
   * @param x
   * @param y
   * @param field  E&M field at the location
   */
  void calculateRetardedField(double x, double y, double[] field) {
    int first = 0;
    int last = numPts-1;
    double ds_first = dsSquared(first, t, x, y);
    if(ds_first>=0) { // field has not yet propagated to the location
      electrostaticField(x, y, field);
      return;
    }
    while((ds_first<0)&&(last-first)>1) {
      int i = first+(last-first)/2; // bisect the interval
      double ds = dsSquared(i, t, x, y);
      if(ds<=0) {
        ds_first = ds;
        first = i;
      } else {
        last = i;
      }
    }
    double t_ret = path[0][first]; // time where ds changes sign
    r[0] = x-evaluate(t_ret);                 // evaluate x at retarded time
    r[1] = y;                                 // evaluate y at retarded time
    v[0] = Derivative.first(this, t_ret, dt); // derivative of x at retarded time
    v[1] = 0;                                  // derivative of y at retarded time
    a[0] = Derivative.second(this, t_ret, dt); // acceleration of x at retarded time
    a[1] = 0; // acceleration of y at retarded time
    double rMag = Vector2DMath.mag2D(r); // magnitdue of r
    u[0] = r[0]/rMag-v[0];
    u[1] = r[1]/rMag-v[1];
    double r_dot_u = Vector2DMath.dot2D(r, u);
    double k = rMag/r_dot_u/r_dot_u/r_dot_u;
    double u_cross_a = Vector2DMath.cross2D(u, a); // u cross a is perpendicular to plane of motion
    double[] temp = {r[0], r[1]};
    temp = Vector2DMath.crossZ(temp, u_cross_a); // temp now equals r cross u
    double c2v2 = 1-Vector2DMath.dot2D(v, v); // (c*c - v*v) where c = 1
    double ex = k*(u[0]*c2v2+temp[0]);
    double ey = k*(u[1]*c2v2+temp[1]);
    field[0] = ex;
    field[1] = ey;
    field[2] = k*Vector2DMath.cross2D(temp, r)/rMag;
  }

  /**
   * Draws the charge.
   *
   * @param panel
   * @param g
   */
  public void draw(DrawingPanel panel, Graphics g) {
    circle.setX(evaluate(t));
    circle.draw(panel, g); // draw the charged particle on the screen
  }

  /**
   * Evaluates the charge position at time t.
   *
   * @param t
   * @return the x position
   */
  public double evaluate(double t) {
    return 5*Math.cos(t*vmax/5.0);
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
