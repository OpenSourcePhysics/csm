/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;

/**
 * A random walk quantum Monte Carlo model.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class QMWalk {
  int numberOfBins = 1000; // number of bins to accumulate data for wave function
  double[] x;        // positions of walkers
  double[] phi0;     // estimate of ground state wave function
  double[] xv;       // x values for computing phi0
  int N0;            // desired number of walkers
  int N;             // actual number of walkers
  double ds;         // step size
  double dt;         // time interval
  double vave = 0;   // mean potential
  double vref = 0;   // reference potential
  double eAccum = 0; // accumulation of energy values
  double xmin;       // minimum x
  int mcs;

  /**
   * Initializes a random walk quantum Monte Carlo model with
   * the given number of walkers and step size.
   */
  public void initialize() {
    N0 = N;
    x = new double[2*numberOfBins];
    phi0 = new double[numberOfBins];
    xv = new double[numberOfBins];
    xmin = -ds*numberOfBins/2.0; // minimum location for computing phi0
    double binEdge = xmin;
    for(int i = 0;i<numberOfBins;i++) {
      xv[i] = binEdge;
      binEdge += ds;
    }
    double initialWidth = 1; // initial width for location of walkers
    for(int i = 0;i<N;i++) {
      x[i] = (2*Math.random()-1)*initialWidth; // initial random location of walkers
      vref += potential(x[i]);
    }
    vave = 0;
    vref = 0;
    eAccum = 0;
    mcs = 0;
    dt = ds*ds;
  }

  /**
   * Walks the particles.
   */
  void walk() {
    double vsum = 0;
    for(int i = N-1;i>=0;i--) {
      if(Math.random()<0.5) { // move walker
        x[i] += ds;
      } else {
        x[i] -= ds;
      }
      double pot = potential(x[i]);
      double dv = pot-vref;
      vsum += pot;
      if(dv<0) {              // decide to add or delete walker
        if(N==0||(Math.random()<-dv*dt)&&(N<x.length)) {
          x[N] = x[i];        // new walker at the current location
          vsum += pot;        // add energy of new walker
          N++;
        }
      } else {
        if((Math.random()<dv*dt)&&(N>0)) {
          N--;
          x[i] = x[N];        // relabel last walker to deleted walker index
          vsum -= pot;        // substract energy of deleted walker
        }
      }
    }
    vave = (N==0) ? 0 // if no walkers poential = 0
                  : vsum/N;
    vref = vave-(N-N0)/N0/dt;
    mcs++;
  }

  /**
   * Does a Monte Carlo step and accumulates the data.
   */
  void doMCS() {
    walk();
    eAccum += vave;
    for(int i = 0;i<N;i++) {
      int bin = (int) Math.floor((x[i]-xmin)/ds); // calculate bin index
      if(bin>=0&&bin<numberOfBins) {
        phi0[bin]++;
      }
    }
  }

  /**
   * Resets averages to 0
   */
  void resetData() {
    for(int i = 0;i<numberOfBins;i++) {
      phi0[i] = 0;
    }
    eAccum = 0;
    mcs = 0;
  }

  /**
   * Evaluates the potential energy for a simple harmonic oscillator.
   *
   * Change this function  value to model other potentials.
   *
   * @param x
   * @return the potential
   */
  public double potential(double x) {
    return 0.5*x*x;
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
