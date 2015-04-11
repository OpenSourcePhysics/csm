/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;
import java.awt.*;
import org.opensourcephysics.frames.*;

/**
 * IsingDemon  implements the Ising model in one dimension using periodic boundary conditions
 * and the demon algorithm.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class IsingDemon {
  public int[] demonEnergyDistribution;
  int N;              // number of spins
  public int systemEnergy;
  public int demonEnergy = 0;
  public int mcs = 0; // number of MC steps per spin
  public double systemEnergyAccumulator = 0;
  public double demonEnergyAccumulator = 0;
  public int magnetization = 0;
  public double mAccumulator = 0, m2Accumulator = 0;
  public int acceptedMoves = 0;
  private LatticeFrame lattice;

  public IsingDemon(LatticeFrame displayFrame) {
    lattice = displayFrame;
  }

  public void initialize(int N) {
    this.N = N;
    lattice.resizeLattice(N, 1); // set lattice size
    lattice.setIndexedColor(1, Color.red);
    lattice.setIndexedColor(-1, Color.green);
    demonEnergyDistribution = new int[N];
    for(int i = 0;i<N;++i) {
      lattice.setValue(i, 0, 1); // all spins up, second argument is always 0 for 1D lattice
    }
    int tries = 0;
    int E = -N; // start system in ground state
    magnetization = N; // all spins up
    // try to 10*N times to flip spins so that system has desired energy
    while((E<systemEnergy)&&(tries<10*N)) {
      int k = (int) (N*Math.random());
      int dE = 2*lattice.getValue(k, 0)*(lattice.getValue((k+1)%N, 0)+lattice.getValue((k-1+N)%N, 0));
      if(dE>0) {
        E += dE;
        int newSpin = -lattice.getValue(k, 0);
        lattice.setValue(k, 0, newSpin);
        magnetization += 2*newSpin;
      }
      tries++;
    }
    systemEnergy = E;
    resetData();
  }

  public double temperature() {
    return 4.0/Math.log(1.0+4.0/(demonEnergyAccumulator/(mcs*N)));
  }

  public void resetData() {
    mcs = 0;
    systemEnergyAccumulator = 0;
    demonEnergyAccumulator = 0;
    mAccumulator = 0;
    m2Accumulator = 0;
    acceptedMoves = 0;
  }

  public void doOneMCStep() {
    for(int j = 0;j<N;++j) {
      int i = (int) (N*Math.random());
      int dE = 2*lattice.getValue(i, 0)*(lattice.getValue((i+1)%N, 0)+lattice.getValue((i-1+N)%N, 0));;
      if(dE<=demonEnergy) {
        int newSpin = -lattice.getValue(i, 0);
        lattice.setValue(i, 0, newSpin);
        acceptedMoves++;
        systemEnergy += dE;
        demonEnergy -= dE;
        magnetization += 2*newSpin;
      }
      systemEnergyAccumulator += systemEnergy;
      demonEnergyAccumulator += demonEnergy;
      mAccumulator += magnetization;
      m2Accumulator += magnetization*magnetization;
      demonEnergyDistribution[demonEnergy]++;
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
