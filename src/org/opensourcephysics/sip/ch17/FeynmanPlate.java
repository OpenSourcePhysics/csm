/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.numerics.*;

/**
 * RigidBody models rigid body dynamics by solving  Euler's equations using an ODE solver.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class FeynmanPlate {
  Quaternion toBody = new Quaternion(1, 0, 0, 0);
  // spaceview displays the plate as seen from the laboratory

  FeynmanPlateView spaceView = new FeynmanPlateView(this);
  double[] spaceL = new double[3]; // space frame angular momentum
  double I1 = 1, I2 = 1, I3 = 1;   // default moments of inertia
  double wx = 0, wy = 0, wz = 0;   // angular velocity in the body frame
  double q0, q1, q2, q3;           // quaternion components
  double dt = 0.1;

  /**
   * Sets the orientation
   * @param q double[]
   */
  void setOrientation(double[] q) {
    double norm = Math.sqrt(q[0]*q[0]+q[1]*q[1]+q[2]*q[2]+q[3]*q[3]);
    q0 = q[0]/norm;
    q1 = q[1]/norm;
    q2 = q[2]/norm;
    q3 = q[3]/norm;
    toBody.setCoordinates(q0, q1, q2, q3);
    spaceView.initialize();
  }

  /**
   * Gets the transformation to the current position.
   * @return Quaternion
   */
  Transformation getTransformation() {
    toBody.setCoordinates(q0, q1, q2, q3);
    return toBody;
  }

  /**
   * Sets the principal moments of inertia.
   *
   * @param I1 double
   * @param I2 double
   * @param I3 double
   */
  void setInertia(double I1, double I2, double I3) {
    setOrientation(new double[] {1, 0, 0, 0}); // default orientation
    this.I1 = I1;
    this.I2 = I2;
    this.I3 = I3;
    spaceView.initialize();
  }

  /**
   * Computes the omega components in the body frame.
   */
  void computeOmegaBody() {
    double[] bodyL = spaceL.clone();
    toBody.inverse(bodyL);
    wx = bodyL[0]/I1;
    wy = bodyL[1]/I2;
    wz = bodyL[2]/I3;
  }

  /**
   * Advances the time be solving the differential equation.
   */
  public void advanceTime() {
    computeOmegaBody();
    // compute quaternion rate of change
    double q0dot = 0.5*(-q1*wx-q2*wy-q3*wz); // dq0/dt
    double q1dot = 0.5*(q0*wx-q3*wy+q2*wz);  // dq1/dt
    double q2dot = 0.5*(q3*wx+q0*wy-q1*wz);  // dq2/dt
    double q3dot = 0.5*(-q2*wx+q1*wy+q0*wz); // dq3/dt
    // update quaternion
    q0 += q0dot*dt;
    q1 += q1dot*dt;
    q2 += q2dot*dt;
    q3 += q3dot*dt;
    double norm = 1/Math.sqrt(q0*q0+q1*q1+q2*q2+q3*q3); // normalize to eliminate drift
    q0 *= norm;
    q1 *= norm;
    q2 *= norm;
    q3 *= norm;
    toBody.setCoordinates(q0, q1, q2, q3);
    spaceView.update();
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
