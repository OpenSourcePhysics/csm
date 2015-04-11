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
 * ManyDemonsApp simulates the ManyDemons model.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class ManyDemonsApp extends AbstractSimulation {
  ManyDemons manyDemons = new ManyDemons();
  DisplayFrame displayFrame = new DisplayFrame("Many Demons");
  PlotFrame plotFrame = new PlotFrame("x", "T", "Temperature vs x");;

  public ManyDemonsApp() {
    displayFrame.addDrawable(manyDemons);
  }

  public void initialize() {
    enableStepsPerDisplay(true);
    manyDemons.N = control.getInt("N");
    manyDemons.systemEnergy = control.getInt("systemEnergy");
    manyDemons.timeToAddEnergy = control.getInt("timeToAddEnergy");
    manyDemons.initialize();
    resetData();
    displayFrame.setPreferredMinMax(-5, 5+manyDemons.N, -5, 5);
  }

  public void doStep() {
    manyDemons.step();
    plotFrame.clearData();
    for(int i = 1;i<manyDemons.N-1;i++) {
      plotFrame.append(0, i, manyDemons.temperature(i));
    }
  }

  public void stop() {
    double norm = 1.0/(manyDemons.mcs*manyDemons.N);
    control.println("mcs = "+manyDemons.mcs+" Q = "+manyDemons.demonEnergyAccumulator[0]/manyDemons.mcs);
    control.println("acceptance ratio = "+manyDemons.acceptedMoves*norm+" System Energy = "+manyDemons.systemEnergyAccumulator*norm);
  }

  public void reset() {
    control.setValue("N", 52);
    control.setValue("systemEnergy", -20);
    control.setValue("timeToAddEnergy", 1);
  }

  public void resetData() {
    manyDemons.resetData();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new ManyDemonsApp());
    control.addButton("resetData", "resetData");
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
