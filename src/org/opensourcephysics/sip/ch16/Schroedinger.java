/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.numerics.*;

/**
 * Schroedinger solves the time independent Schroedinger equation
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class Schroedinger implements ODE {
  double energy = 0;
  double[] phi;
  double[] x;
  double xmin, xmax;              // range of values of x
  double[] state = new double[3]; // state = phi, dphi/dx, x
  ODESolver solver = new RK45MultiStep(this);
  double stepHeight = 0;
  int numberOfPoints;

  /**
   * Constructs a simple quantum mechanical model.
   * The potential enegy is coded into the evaluate method.
   *
   * @param numberOfPoints  number of grid points
   * @param a      half interval
   */
  public void initialize() {
    phi = new double[numberOfPoints];
    x = new double[numberOfPoints];
    double dx = (xmax-xmin)/(numberOfPoints-1);
    solver.setStepSize(dx);
  }

  /**
   * Solves the Schroedinger equation and stores the result in the phi array.
   *
   * @param energy
   * @return
   */
  void solve() {
    for(int i = 0;i<numberOfPoints;i++) { // zeros wavefunction
      phi[i] = 0;
    }
    state[0] = 0;    // initial phi
    state[1] = 1.0;  // nonzero initial dphi/dx
    state[2] = xmin; // initial value of x
    for(int i = 0;i<numberOfPoints;i++) {
      phi[i] = state[0];             // stores wavefunction
      x[i] = state[2];
      solver.step();                 // steps Schroedinger equation
      if(Math.abs(state[0])>1.0e9) { // checks for diverging solution
        break;                       // leave the loop
      }
    }
  }

  /**
   * Gets the state.
   * The state for the ode solver: phi, dphi/dx, x.
   *
   * @return the state
   */
  public double[] getState() {
    return state;
  }

  /**
   * Gets the rate using the given state.
   *
   * @param state
   * @param rate
   */
  public void getRate(double[] state, double[] rate) {
    rate[0] = state[1];
    rate[1] = 2.0*(-energy+evaluatePotential(state[2]))*state[0];
    rate[2] = 1.0;
  }

  /**
   * Evaluates the potential using a step at x=0.
   * Change this function to model other qm systems.
   *
   * @param x
   * @return
   */
  public double evaluatePotential(double x) { // potential is nonzero for x > 0
    if(x<0) {
      return 0;
    }
	return stepHeight;
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
