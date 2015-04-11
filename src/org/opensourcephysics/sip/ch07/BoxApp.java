/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch07;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * Simulates approach to equilibrium for particles in a partioned box.
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class BoxApp extends AbstractSimulation {
  Box box = new Box();
  PlotFrame plotFrame = new PlotFrame("time", "number on left", "Box data");
  DisplayFrame displayFrame = new DisplayFrame("Partitioned box");

  /**
   * Gets parameters and initializes model
   */
  public void initialize() {
    displayFrame.clearDrawables();
    displayFrame.addDrawable(box);
    box.N = control.getInt("Number of particles");
    box.initialize();
    plotFrame.clearData();
    displayFrame.setPreferredMinMax(0, 1, 0, 1);
  }

  /**
   * Draws particles after each move, and plots data.
   */
  public void doStep() {
    box.step();
    plotFrame.append(0, box.time, box.nleft);
  }

  /**
   *  Resets to default values
   */
  public void reset() {
    // clicking reset should erase positions of particles
    control.setValue("Number of particles", 64);
    plotFrame.clearData();
    enableStepsPerDisplay(true);
    setStepsPerDisplay(10);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new BoxApp());
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
