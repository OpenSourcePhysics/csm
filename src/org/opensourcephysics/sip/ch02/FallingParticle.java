/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;

/**
 * FallingParticle models a falling ball by subclassing the Particle class.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0 05/07/05
 */
public class FallingParticle extends Particle {
  final static double g = 9.8;   // constant
  private double y0 = 0, v0 = 0; // initial position and velocity

  /**
   * Constructs a Falling Particle with the given initial conditions.
   *
   * @param y double
   * @param v double
   */
  public FallingParticle(double y, double v) { // constructor
    System.out.println("A new FallingParticle object is created.");
    this.y = y; // instance value set equal to passed value
    this.v = v; // instance value set equal to passed value
    y0 = y;     // no need to use "this" because there is only one y0
    v0 = v;
  }

  /**
   * Steps (advances) the dynamical variables using the Euler method.
   */
  public void step() {
    y = y+v*dt; // Euler algorithm
    v = v-g*dt;
    t = t+dt;
  }

  /**
   * Computes the position of the ball at the current time using the analytic solution of the equation of motion.
   * @param y0 double
   * @param v0 double
   * @return double
   */
  public double analyticPosition() {
    return y0+v0*t-(g*t*t)/2.0;
  }

  /**
   * Computes the velocity of the ball at the current time using the analytic solution of the equation of motion.
   * @param y0 double
   * @param v0 double
   * @return double
   */
  public double analyticVelocity() {
    return v0-g*t;
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
