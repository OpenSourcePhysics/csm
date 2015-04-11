/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch08.hd;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.GUIUtils;

/**
 * HardDisksApp simulates a two-cimensional system of colliding hard disks.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0 revised 03/28/05
 */
public class HardDisksApp extends AbstractSimulation {
  HardDisks hd = new HardDisks();
  PlotFrame pressureData = new PlotFrame("time", "PA/NkT", "Pressure");
  DisplayFrame display = new DisplayFrame("x", "y", "Lennard-Jones Particles");
  double timeToPlot;

  /**
   * Initializes the model by reading the number of particles.
   */
  public void initialize() {
    hd.N = control.getInt("N");
    hd.Lx = control.getDouble("Lx");
    hd.Ly = control.getDouble("Ly");
    String configuration = control.getString("initial configuration");
    hd.initialize(configuration);
    display.addDrawable(hd);
    display.setPreferredMinMax(0, hd.Lx, 0, hd.Ly);
    display.setSquareAspect(true);
    control.println("Temperature = "+decimalFormat.format(hd.temperature));
    timeToPlot = 1;
  }

  /**
   * Does a simulation step and appends data to the views.
   */
  public void doStep() {
    while(hd.t<timeToPlot) { // plot at roughly equal time intervals
      hd.step();
    }
    timeToPlot++;
    pressureData.append(0, hd.t, hd.pressure());
    display.setMessage("Number of Collisions =  "+hd.numberOfCollisions);
  }

  /**
   * Resets the hard disks model to its default state.
   */
  public void reset() {
    enableStepsPerDisplay(true);
    control.setValue("N", 16);
    control.setValue("Lx", 8.0);
    control.setValue("Ly", 8.0);
    control.setValue("initial configuration", "regular");
    initialize();
  }

  /**
   * Resets the hard disks model and the data graphs.
   *
   * This method is invoked using a custom button.
   */
  public void resetData() {
    hd.resetAverages();
    GUIUtils.clearDrawingFrameData(false); // clears old data from the plot frames
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // set up animation control structure using this class
    SimulationControl control = SimulationControl.createApp(new HardDisksApp());
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
