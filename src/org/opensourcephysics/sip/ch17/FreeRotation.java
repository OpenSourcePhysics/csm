/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;

/**
 * SpinningTop solves the Euler equations for a spinning top.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class FreeRotation extends RigidBody {
  FreeRotationSpaceView spaceView = new FreeRotationSpaceView(this);

  /**
   * Advances the time be solving the differential equation.
   */
  public void advanceTime() {
    super.advanceTime();
    spaceView.update();
  }

  /**
   * Sets the body frame angular velocity.
   *
   * @param wx
   * @param wy
   * @param wz
   */
  void setBodyFrameOmega(double[] omega) {
    super.setBodyFrameOmega(omega);
    spaceView.update();
  }

  /**
   * Initializes the space view by setting the inertia and initializing the space view.
   *
   * @param a double
   * @param b double
   * @param c double
   */
  void setSize(double a, double b, double c) {
    setOrientation(new double[] {1, 0, 0, 0}); // put object back into standard orientation
    I1 = (b*b+c*c)/5;
    I2 = (a*a+c*c)/5;
    I3 = (b*b+a*a)/5;
    updateVectors();
    spaceView.initialize(a, b, c);
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
