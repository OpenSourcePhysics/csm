/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch06;
import org.opensourcephysics.numerics.ODE;

/**
 * DampedDrivenPendulum models a damped driven pendulum.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class DampedDrivenPendulum implements ODE {
  double state[] = new double[3]; // [theta, angular velocity,time]
  double gamma;                   // damping constant
  double A;                       // amplitude of external force

  /**
   * Initializes the state by copying the given array into the state array.
   * The state array variables are: [theta, angular velocity,time].
   *
   * @param newState double[]
   */
  void initializeState(double[] newState) {
    System.arraycopy(newState, 0, state, 0, 3);
  }

  /**
   * Gets the rate using the given state.
   * @param state double[]
   * @param rate double[]
   */
  public void getRate(double state[], double rate[]) {
    rate[0] = state[1]; // state[1] = angular velocity
    rate[1] = -gamma*state[1]-(1.0+2.0*A*Math.cos(2*state[2]))*Math.sin(state[0]);
    rate[2] = 1; // rate of change of time
  }

  /**
   * Gets the state.
   *
   * @return double[]
   */
  public double[] getState() {
    return state;
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
