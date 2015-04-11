/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;

/**
 * SHOParticle models a simple harmonic oscillator by subclassing the Particle class.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.01  04/04/06
 */
public class SHOParticle extends Particle {
  final static double k = 1.0;               // spring constant
  final static double m = 1.0;
  final static double omega0 = Math.sqrt(k); // assume unit mass
  private double y0 = 0, v0 = 0;             // initial position and velocity

  /**
   * Constructs the simple harmonic oscillator model with the given position and velocity.
   *
   * @param y double
   * @param v double
   */
  public SHOParticle(double y, double v) { // constructor
    System.out.println("A new SHO object is being created.");
    this.y = y; // sets instance value equal to passed value
    this.v = v;
    y0 = y;     // no need to use "this" because only one y0
    v0 = v;
  }

  /**
   * Steps (advances) the dynamical variables using the Euler-Cromer algorithm.
   * Algortihm corrected on /04/04/2006
   * v = v-((k/m)*y*dt)
   */
  public void step() {
    v = v-((k/m)*y*dt); // Euler-Cromer algorithm for numerical solution
    y = y+(v*dt);     // uses new v
    t = t+dt;
  }

  /**
   * Computes the position at the current time using the analytic solution.
   *
   * @return double
   */
  public double analyticPosition() {
    return y0*Math.cos(omega0*t)+v0/omega0*Math.sin(omega0*t);
  }

  /**
   * Computes the velocity at the current time using the analytic solution.
   *
   * @return double
   */
  public double analyticVelocity() {
    return -y0*omega0*Math.sin(omega0*t)+v0*Math.cos(omega0*t);
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
