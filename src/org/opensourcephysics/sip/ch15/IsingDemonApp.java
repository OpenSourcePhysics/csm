/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**

 * IsingDemonApp simulates a one-dimensional IsingDemon model.

 *

 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould

 * @version 1.0  revised 07/05/05

 */
public class IsingDemonApp extends AbstractSimulation {
  LatticeFrame displayFrame = new LatticeFrame("1D Ising Model");
  IsingDemon ising1D = new IsingDemon(displayFrame);
  PlotFrame plotFrame = new PlotFrame("E", "ln P", "Boltzmann distribution");

  public void initialize() {
    ising1D.systemEnergy = control.getInt("Desired energy of system");
    ising1D.initialize(control.getInt("N"));
    resetData();
  }

  public void doStep() {
    ising1D.doOneMCStep();
  }

  public void stop() {
    plotFrame.clearData();
    double norm = 1.0/(ising1D.mcs*ising1D.N);
    for(int i = 0;i<ising1D.N;i++) {
      if(ising1D.demonEnergyDistribution[i]>0) {
        plotFrame.append(0, i, Math.log(norm*ising1D.demonEnergyDistribution[i]));
      }
    }
    plotFrame.render();
    control.println("mcs = "+ising1D.mcs);
    control.println("<Ed> = "+ising1D.demonEnergyAccumulator*norm);
    control.println("acceptance probability = "+ising1D.acceptedMoves*norm);
    control.println("<E> = "+ising1D.systemEnergyAccumulator*norm);
    control.println("Temperature = "+ising1D.temperature());
    control.println("<M> = "+ising1D.mAccumulator*norm);
    control.println("<M^2> = "+ising1D.m2Accumulator*norm);
  }

  public void reset() {
    control.setValue("N", 100);
    control.setValue("Desired energy of system", -20);
  }

  public void resetData() {
    ising1D.resetData();
    control.clearMessages();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new IsingDemonApp());
    control.addButton("resetData", "Reset Data");
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
