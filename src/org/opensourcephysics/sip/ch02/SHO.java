/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;

/**
 * SHO models a simple harmonic oscillator.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  05/07/05
 */
public class SHO {
  double x, v, t;               // the dynamical variables
  double dt;
  double k = 1.0;               // spring constant
  double omega0 = Math.sqrt(k); // assume unit mass

  /**
   * Creates a simple harmonic oscillator at x = 0 with v = 0.
   */
  public SHO() { // constructor
    System.out.println("A new harmonic oscillator object is created.");
  }

  /**
   * Steps (advances) the dynamical variables using the Euler algorithm.
   */
  public void step() {
    // modified Euler algorithm
    v = v-k*x*dt;
    x = x+v*dt; // note that updated v is used
    t = t+dt;
  }

  /**
   * Computes the position using the given initial position and initial velocity.
   *
   * @return double
   */
  public double analyticPosition(double y0, double v0) {
    return y0*Math.cos(omega0*t)+v0/omega0*Math.sin(omega0*t);
  }

  /**
   * Computes the velocity using the given initial position and initial velocity.
   *
   * @return double
   */
  public double analyticVelocity(double y0, double v0) {
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
