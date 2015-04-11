/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;

/**
 * IdealDemon modles the demon algorithm for the one-dimensional ideal gas.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class IdealDemon {
  public double v[];
  public int N;
  public double systemEnergy;
  public double demonEnergy;
  public int mcs = 0; // number of MC moves per particle
  public double systemEnergyAccumulator = 0;
  public double demonEnergyAccumulator = 0;
  public int acceptedMoves = 0;
  public double delta;

  public void initialize() {
    v = new double[N]; // array to hold particle velocities
    double v0 = Math.sqrt(2.0*systemEnergy/N);
    for(int i = 0;i<N;++i) {
      v[i] = v0; // give all particles the same initial velocity
    }
    demonEnergy = 0;
    resetData();
  }

  public void resetData() {
    mcs = 0;
    systemEnergyAccumulator = 0;
    demonEnergyAccumulator = 0;
    acceptedMoves = 0;
  }

  public void doOneMCStep() {
    for(int j = 0;j<N;++j) {
      int particleIndex = (int) (Math.random()*N); // choose particle at random
      double dv = (2.0*Math.random()-1.0)*delta;   // random change in velocity
      double trialVelocity = v[particleIndex]+dv;
      double dE = 0.5*(trialVelocity*trialVelocity-v[particleIndex]*v[particleIndex]);
      if(dE<=demonEnergy) {
        v[particleIndex] = trialVelocity;
        acceptedMoves++;
        systemEnergy += dE;
        demonEnergy -= dE;
      }
      systemEnergyAccumulator += systemEnergy;
      demonEnergyAccumulator += demonEnergy;
    }
    mcs++;
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
