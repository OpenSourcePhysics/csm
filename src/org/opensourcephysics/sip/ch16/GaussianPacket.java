/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;

/**
 * A Gaussian wave function with a momentum boost.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class GaussianPacket {
  double w, x0, p0;
  double w42;
  double norm;

  /**
   * Constructs the Gaussian wave function with the given center and momentum boost.\
   * @param width double
   * @param center double
   * @param momentum double
   */
  public GaussianPacket(double width, double center, double momentum) {
    w = width;
    w42 = 4*w*w;
    x0 = center;
    p0 = momentum;
    norm = Math.pow(2*Math.PI*w*w, -0.25);
  }

  /**
   * Gets the real part of the Gaussian wave function at the given x.
   * @param x double
   * @return double
   */
  public double getReal(double x) {
    return norm*Math.exp(-(x-x0)*(x-x0)/w42)*Math.cos(p0*(x-x0));
  }

  /**
   * Gets the imaginary part of the Gaussian wave function at the given x.
   * @param x double
   * @return double
   */
  public double getImaginary(double x) {
    return norm*Math.exp(-(x-x0)*(x-x0)/w42)*Math.sin(p0*(x-x0));
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
