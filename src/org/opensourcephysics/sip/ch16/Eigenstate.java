/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.numerics.*;

/**
 * Eigenstate computes energy eigenstates for an arbitrary potential.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class Eigenstate implements ODE {
  public static final int NO_ERROR = 0;
  public static final int DID_NOT_CONVERGE = 1;
  public static final int INSUFFICIENT_NUMBER_OF_POINTS = 2;
  int errCode = 0;
  double tolerance = 1.0e-2;
  double energy, potMin;
  double[] phi;                   // wave function
  double[] x;                     // positions
  double maxAmp;                  // maximum amplitude of phi
  double xmin, xmax;              // min and max x values
  int istart, istop;
  double dx;
  double[] state = new double[3]; // state = phi, d phi/dx, x
  ODESolver solver = new RK45MultiStep(this);
  Function pot;

  /**
   * Constructs an Eigenstate using the given potential function and the given x-domain.
   */
  public Eigenstate(Function potential, int numberOfPoints, double xmin, double xmax) {
    pot = potential;
    phi = new double[numberOfPoints];
    x = new double[numberOfPoints];
    dx = (xmax-xmin)/(numberOfPoints-1);
    solver.setStepSize(dx);
    this.xmin = xmin;
    this.xmax = xmax;
    istart = 0;
    istop = numberOfPoints;
    initialize();
  }

  private void initialize() {
    double position = xmin;
    potMin = pot.evaluate(position);
    for(int i = 0, n = phi.length;i<n;i++) {
      x[i] = position;
      double V = pot.evaluate(position);
      if(potMin>V) {
        potMin = V;
      }
      position += dx;
    }
  }

  /**
   * Gets the energy eigenfuntion for the given quantum number.
   *
   * @param qnumber  quantum number
   *
   * @return double[] the eigenstate
   */
  public double[] getEigenstate(int qnumber) {
    double enmin = potMin; // energy must be larger than the minimum potential
    double enmax = enmin+1;
    int crossings = 0;
    int maxCrossings = x.length/4;
    int counter = 0;
    while(counter<12&&crossings<=qnumber) {
      crossings = solve(enmax);
      enmax += enmax-enmin; // increase enmax until the number of crossings is larger than qnumber
      if(crossings>maxCrossings) {
        errCode = INSUFFICIENT_NUMBER_OF_POINTS;
        return new double[phi.length]; // return array of zeros
      }
      counter++;
    }
    counter = 0; // reset the counter
    do {
      double en = (enmax+enmin)/2; // estimate the energy
      int crossing = solve(en);
      if((crossing==qnumber)&&(Math.abs(phi[phi.length-1])<=tolerance*maxAmp)) {
        errCode = NO_ERROR;
        return phi.clone();
      }
      if((crossing>qnumber)||((crossing==qnumber)&&(parity(crossing)*phi[phi.length-1]>0))) {
        enmax = en;
      } else {
        enmin = en;
      }
      counter++;
    } while((counter<32)&&(enmax-enmin)>1.0e-8);
    errCode = DID_NOT_CONVERGE; // did not converge to eigenfunction
    return new double[phi.length]; // return array of zeros
  }

  /**
   * Gets the x coordinates during the eigenstate computation.
   * @return double[]
   */
  public double[] getXCoordinates() {
    return x.clone();
  }

  /**
   * Gets the error code for the last eigenstate computation.
   * @return int
   */
  public int getErrorCode() {
    return errCode;
  }

  /**
   * Estimatates the region where the wave function is non-zero.
   * The region is bounded by the istart and istop indices.
   *
   * @param en double the energy
   */
  private void estimateStartStopIndex(double en) {
    double x = xmin;
    // find the first point where E>V
    for(int i = 0, n = phi.length;i<n;i++) {
      if((pot.evaluate(x)-en)<0) {
        istart = i;
        break;
      }
      x += dx;
    }
    x = xmax;
    // find the last point where E>V
    for(int i = phi.length;i>istart;i--) {
      if((pot.evaluate(x)-en)<0) {
        istop = i;
        break;
      }
      x -= dx;
    }
    double a = 1;
    x = xmin+istart*dx;
    // assume exponential decay on left hand side
    while(a>tolerance&&istart>0) {
      istart--;
      x -= dx;
      double k = Math.sqrt(2*(pot.evaluate(x)-en));
      a *= Math.exp(-k*dx);
    }
    a = 1;
    x = xmin+istop*dx;
    // assume exponential decay on right hand side
    while(a>tolerance&&istop<phi.length) {
      istop++;
      x += dx;
      double k = Math.sqrt(2*(pot.evaluate(x)-en));
      a *= Math.exp(-k*dx);
    } // omitted statement that was commented out
  }

  /**
   * Solves the Schroedinger ODE with the given energy.
   * @param en double the energy
   * @return int error code
   */
  private int solve(double en) {
    estimateStartStopIndex(en);
    if(istop-istart<3) {
      return 0;
    }
    energy = en;
    state[0] = 0;              // initial phi
    state[1] = 1.0;            // nonzero initial d phi/dx
    state[2] = xmin+istart*dx; // initial x
    int crossing = 0; // count  number of zero crossings
    boolean slopeChanged = false;
    maxAmp = 0;
    double norm = 0;
    double[] lastState = new double[3];
    for(int i = 0;i<istart;i++) {
      phi[i] = 0; // wavefuntion = 0 to start
      x[i] = xmin+i*dx;
    }
    for(int i = istart;i<istop;i++) {
      phi[i] = state[0];                           // store wavefunction
      x[i] = state[2];
      norm += state[0]*state[0];
      System.arraycopy(state, 0, lastState, 0, 3); // save current state
      solver.step();
      if(maxAmp<Math.abs(state[0])) {
        maxAmp = Math.abs(state[0]);
      }
      if(((state[1]<=0)&&(lastState[1]>0))||((state[1]>=0)&&(lastState[1]<0))) {
        slopeChanged = true;
      }
      if((state[0]<=0)&&(lastState[0]>0)) {        // positive to negative transition
        crossing++;
        slopeChanged = false;
      } else if((state[0]>=0)&&(lastState[0]<0)) { // negative to positive transition
        crossing++;
        slopeChanged = false;
      }
      if(Math.abs(state[0])>1.0e9) {   // break if solution  diverges
        for(int j = i+1;j<istop;j++) { // fill remaining values
          phi[j] = state[0];
          x[i] += solver.getStepSize();
        }
        break;
      }
    }
    norm *= dx;
    for(int i = istop, n = phi.length;i<n;i++) {
      phi[i] = phi[istop-1];
      x[i] = xmin+i*dx;
    }
    // check if last value is close enough to a crossing
    if(slopeChanged&&(Math.abs(phi[phi.length-1])<=tolerance*maxAmp)) {
      crossing++;
    }
    rescale(norm);
    return crossing;
  }

  private int parity(int n) {
    if(n%2==0) {
      return 1;
    }
	return -1;
  }

  private void rescale(double scale) {
    if(scale==0) {
      return;
    }
    for(int i = 0, n = phi.length;i<n;i++) {
      phi[i] /= scale;
      state[0] /= scale;
      state[1] /= scale;
    }
    maxAmp /= scale;
  }

  /**
   * Gets the state.
   * The state for the ode solver is phi, d phi/dx, x.
   * @return
   */
  public double[] getState() {
    return state;
  }

  public void getRate(double[] state, double[] rate) {
    rate[0] = state[1];
    rate[1] = 2*(-energy+pot.evaluate(state[2]))*state[0];
    rate[2] = 1;
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
