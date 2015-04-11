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
public class RigidBody implements ODE {
  Quaternion rotation = new Quaternion(1, 0, 0, 0);
  double[] state = new double[9];
  ODESolver solver = new RK45MultiStep(this);
  double[] omegaBody = new double[3];           // body frame omega
  double[] angularMomentumBody = new double[3]; // body frame angular momentum
  double I1 = 1, I2 = 1, I3 = 1;                // principal moments of inertia
  double wxdot = 0, wydot = 0, wzdot = 0;       // angular acceleration in the body frame
  double t1 = 0, t2 = 0, t3 = 0; // torque in the body frame

  /**
   * Sets the orientation
   * @param q double[]
   */
  void setOrientation(double[] q) {
    double norm = Math.sqrt(q[0]*q[0]+q[1]*q[1]+q[2]*q[2]+q[3]*q[3]);
    state[0] = q[0]/norm;
    state[2] = q[1]/norm;
    state[4] = q[2]/norm;
    state[6] = q[3]/norm;
    rotation.setCoordinates(state[0], state[2], state[4], state[6]);
  }

  /**
   * Gets the transformation from the default position to the current position.
   * @return Quaternion
   */
  Transformation getTransformation() {
    rotation.setCoordinates(state[0], state[2], state[4], state[6]);
    return rotation;
  }

  /**
   * Sets the body frame angular velocity.
   *
   * @param wx
   * @param wy
   * @param wz
   */
  void setBodyFrameOmega(double[] omega) {
    // use components for clarity
    double q0 = state[0], q1 = state[2], q2 = state[4], q3 = state[6];
    double wx = omega[0];
    double wy = omega[1];
    double wz = omega[2];
    state[1] = 0.5*(-q1*wx-q2*wy-q3*wz); // dq0/dt
    state[3] = 0.5*(q0*wx-q3*wy+q2*wz);  // dq1/dt
    state[5] = 0.5*(q3*wx+q0*wy-q1*wz);  // dq2/dt
    state[7] = 0.5*(-q2*wx+q1*wy+q0*wz); // dq3/dt
    updateVectors();
  }

  /**
   * Gets the space frame angular velocity components.
   *
   * @return the omega vector
   */
  public double[] getBodyFrameOmega() {
    return omegaBody;
  }

  /**
   * Gets the space frame angular momentum components.
   *
   * @return the angular momentum vector
   */
  public double[] getBodyFrameAngularMomentum() {
    return angularMomentumBody;
  }

  /**
   * Updates the body frame vectors using the current state.
   */
  void updateVectors() {
    double q0 = state[0], q1 = state[2], q2 = state[4], q3 = state[6];
    omegaBody[0] = 2*(-q1*state[1]+q0*state[3]+q3*state[5]-q2*state[7]);
    omegaBody[1] = 2*(-q2*state[1]-q3*state[3]+q0*state[5]+q1*state[7]);
    omegaBody[2] = 2*(-q3*state[1]+q2*state[3]-q1*state[5]+q0*state[7]);
    angularMomentumBody[0] = I1*omegaBody[0];
    angularMomentumBody[1] = I2*omegaBody[1];
    angularMomentumBody[2] = I3*omegaBody[2];
  }

  /**
   * Advances the time be solving the differential equation.
   */
  public void advanceTime() {
    solver.step();
    double norm = 1/Math.sqrt(state[0]*state[0]+state[2]*state[2]+state[4]*state[4]+state[6]*state[6]);
    state[0] *= norm;
    state[2] *= norm;
    state[4] *= norm;
    state[6] *= norm;
    updateVectors();
  }

  /**
   * Gets the state of the rigid body.
   * @return double[]
   */
  public double[] getState() {
    return state;
  }

  /**
   * Gets the rate of the rigid body's state using the given state.
   *
   * @param state double[]
   * @param rate double[]
   */
  public void getRate(double[] state, double[] rate) {
    computeBodyFrameAcceleration(state);
    double sum = 0;
    for(int i = 1;i<9;i += 2) { // sum the q dot values
      sum += state[i]*state[i];
    }
    sum = -2.0*sum;
    // use q components for clarity
    double q0 = state[0], q1 = state[2], q2 = state[4], q3 = state[6];
    rate[0] = state[1];
    rate[1] = 0.5*(-q1*wxdot-q2*wydot-q3*wzdot+q0*sum);
    rate[2] = state[3];
    rate[3] = 0.5*(q0*wxdot-q3*wydot+q2*wzdot+q1*sum);
    rate[4] = state[5];
    rate[5] = 0.5*(q3*wxdot+q0*wydot-q1*wzdot+q2*sum);
    rate[6] = state[7];
    rate[7] = 0.5*(-q2*wxdot+q1*wydot+q0*wzdot+q3*sum);
    rate[8] = 1.0; // time rate
  }

  /**
   * Computes the torque in the body frame.
   * Override this method for more complicated systems such as the spinning top.
   *
   * @param state the current state
   */
  void computeBodyFrameTorque(double[] state) {
    t1 = t2 = t3 = 0;
  }

  /**
   * Computes the acceleration in the body frame.
   *
   * @param state the current state of the body
   */
  void computeBodyFrameAcceleration(double[] state) {
    // use components for clarity
    double q0 = state[0], q1 = state[2], q2 = state[4], q3 = state[6];
    double wx = 2*(-q1*state[1]+q0*state[3]+q3*state[5]-q2*state[7]);
    double wy = 2*(-q2*state[1]-q3*state[3]+q0*state[5]+q1*state[7]);
    double wz = 2*(-q3*state[1]+q2*state[3]-q1*state[5]+q0*state[7]);
    computeBodyFrameTorque(state);
    wxdot = (t1-(I3-I2)*wz*wy)/I1; // Euler's equations of motion
    wydot = (t2-(I1-I3)*wx*wz)/I2;
    wzdot = (t3-(I2-I1)*wy*wx)/I3;
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
