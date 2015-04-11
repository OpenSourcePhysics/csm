/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;

/**
 * Particle models a one-dimensional trajectory that can be computed analytically and numerically.
 *
 * This class is abstract because the dynamics of the particle's motion is not yet known.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0 05/07/05
 */
abstract public class Particle {
  double y, v, t; // instance variables
  double dt;      // time step

  /**
   * Constructs a Particle.
   */
  public Particle() { // constructor
    System.out.println("A new Particle is created.");
  }

  /**
   * Steps (advances) the dynamical variables using a numeric method.
   */
  abstract protected void step();

  /**
   * Computes the position at the current time using the analytic solution.
   *
   * @return double
   */
  abstract protected double analyticPosition();

  /**
   * Computes the velocity at the current time using the analytic solution.
   *
   * @return double
   */
  abstract protected double analyticVelocity();
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
