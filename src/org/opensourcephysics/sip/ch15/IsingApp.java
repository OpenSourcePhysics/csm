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
 * IsingApp simulates a two-dimensional Ising model.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class IsingApp extends AbstractSimulation {
  Ising ising = new Ising();
  LatticeFrame displayFrame = new LatticeFrame("Ising Model");
  PlotFrame plotFrame = new PlotFrame("time", "E and M", "Ising model");;

  public IsingApp() {
    plotFrame.setXYColumnNames(0, "mcs", "M", "magnetization");
    plotFrame.setXYColumnNames(1, "mcs", "E", "energy");
  }

  public void initialize() {
    ising.temperature = control.getDouble("temperature");
    ising.initialize(control.getInt("L"), displayFrame);
    resetData();
  }

  public void doStep() {
    ising.doOneMCStep();
    plotFrame.append(0, ising.mcs, ising.magnetization*1.0/ising.N);
    plotFrame.append(1, ising.mcs, ising.energy*1.0/ising.N);
  }

  public void stop() {
    double norm = 1.0/(ising.mcs*ising.N);
    control.println("mcs = "+ising.mcs);
    control.println("acceptance probability = "+ising.acceptedMoves*norm);
    control.println("<E> = "+ising.energyAccumulator*norm);
    control.println("specific heat = "+ising.specificHeat());
    control.println("<M> = "+ising.magnetizationAccumulator*norm);
    control.println("susceptibility = "+ising.susceptibility());
  }

  public void startRunning() {
    ising.temperature = control.getDouble("temperature");
    ising.w[8] = Math.exp(-8.0/ising.temperature); // other array elements never occur for H = 0
    ising.w[4] = Math.exp(-4.0/ising.temperature);
  }

  public void reset() {
    control.setValue("L", 32);
    control.setAdjustableValue("temperature", Ising.criticalTemperature);
    enableStepsPerDisplay(true); // allow user to speed up simulation
  }

  public void resetData() {
    ising.resetData();
    plotFrame.clearData();
    plotFrame.repaint();
    control.clearMessages();
  }

  /**
* Returns an XML.ObjectLoader to save and load data for this program.
*
* LJParticle data can now be saved using the Save menu item in the control.
*
* @return the object loader
*/
  public static XML.ObjectLoader getLoader() {
    return new IsingLoader();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new IsingApp());
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
