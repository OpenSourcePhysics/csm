/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch04;
import org.opensourcephysics.numerics.ODE;

/**
 * RC demonstrates how to implement the ODE interface for an RC circuit.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class RC implements ODE {
  double r, c;                    // resistance and capacitance
  double omega;                   // angular frequency of oscillator
  double[] state = new double[2]; // [charge, time] state array

  /**
   * Construct an RC circuit with the given values.
   */
  public RC(double _r, double _c, double _omega) {
    r = _r;
    c = _c;
    omega = _omega;
    state[0] = 0; // charge
    state[1] = 0; // time
  }

  /**
   * Get state array. Implementation of ODE interface.
   *
   * @return state array
   */
  public double[] getState() {
    return state;
  }

  /**
   * Get the source voltage at given time.
   * @param t
   * @return
   */
  public double getSourceVoltage(double t) {
    return 10*Math.sin(omega*t);
  }

  /**
   * Get the rate array. Implementation of ODE interface.
   * This method may be invoked many times with different intermediate states
   * as an ODESolver is carrying out the solution.
   *
   * @param state the state array
   * @param rate the rate array
   */
  public void getRate(double[] state, double[] rate) {
    rate[0] = (-state[0]/r/c)+(getSourceVoltage(state[1])/r); // dQ/dt
    rate[1] = 1;                                              // dt/dt = 1
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
