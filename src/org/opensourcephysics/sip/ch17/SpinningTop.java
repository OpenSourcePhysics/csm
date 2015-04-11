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
public class SpinningTop extends RigidBody {
  SpinningTopSpaceView spaceView = new SpinningTopSpaceView(this);

  void setInertia(double Is, double Iz) {
    I1 = Is;
    I2 = Is;
    I3 = Iz;
    // orient top along y axis
    setOrientation(new double[] {1/Math.sqrt(2), 1/Math.sqrt(2), 0, 0});
    spaceView.initialize();
  }

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
    spaceView.initialize();
  }

  /**
   * Computes the torque in the body frame.
   *
   * @param state
   */
  void computeBodyFrameTorque(double[] state) {
    double[] vec = new double[] {0, 0, -1}; // external force in space frame
    RigidBodyUtil.spaceToBody(state, vec);
    t1 = -vec[1]; // torque components declared in RigidBody
    t2 = vec[0];
    t3 = 0;
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
