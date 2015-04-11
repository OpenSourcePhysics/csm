/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.latticegas;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * LatticeGasApp simulates and displays the LatticeGas model of fluid flow.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class LatticeGasApp extends AbstractSimulation {
  LatticeGas model = new LatticeGas();
  DisplayFrame display = new DisplayFrame("Lattice gas");

  public LatticeGasApp() {
    display.addDrawable(model);
    display.setSize(800, (int) (400*Math.sqrt(3)/2));
  }

  public void initialize() {
    int lx = control.getInt("lx");
    int ly = control.getInt("ly");
    double density = control.getDouble("Particle density");
    model.initialize(lx, ly, density);
    model.flowSpeed = control.getDouble("Flow speed");
    model.spatialAveragingLength = control.getInt("Spatial averaging length");
    model.arrowSize = control.getInt("Arrow size");
    display.setPreferredMinMax(-1, lx, -Math.sqrt(3)/2, ly*Math.sqrt(3)/2);
  }

  public void doStep() {
    model.flowSpeed = control.getDouble("Flow speed");
    model.spatialAveragingLength = control.getInt("Spatial averaging length");
    model.arrowSize = control.getDouble("Arrow size");
    model.step();
  }

  public void reset() {
    control.setValue("lx", 1000);
    control.setValue("ly", 500);
    control.setValue("Particle density", 0.2);
    control.setAdjustableValue("Flow speed", 0.2);
    control.setAdjustableValue("Spatial averaging length", 20);
    control.setAdjustableValue("Arrow size", 2);
    enableStepsPerDisplay(true);
    control.setAdjustableValue("steps per display", 100);
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new LatticeGasApp());
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
