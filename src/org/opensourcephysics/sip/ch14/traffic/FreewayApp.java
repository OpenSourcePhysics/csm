/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.traffic;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * FreewayApp models traffic flow by showing  the movement of the cars and a space-time diagram.
 *
 * Time is on the vertical axis and space on the horizontal axis.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class FreewayApp extends AbstractSimulation {
  Freeway freeway = new Freeway();
  DisplayFrame display = new DisplayFrame("Freeway");
  LatticeFrame spaceTime = new LatticeFrame("space", "time", "Space Time Diagram");

  /**
   * Constructs the FreewayApp.
   */
  public FreewayApp() {
    display.addDrawable(freeway);
  }

  /**
   * Initializes the animation using the values in the control.
   */
  public void initialize() {
    freeway.numberOfCars = control.getInt("Number of cars");
    freeway.roadLength = control.getInt("Road length");
    freeway.p = control.getDouble("Slow down probability");
    freeway.maximumVelocity = control.getInt("Maximum velocity");
    display.setPreferredMinMax(0, freeway.roadLength, -3, 4);
    freeway.initialize(spaceTime);
  }

  /**
   * Does one iteration.
   */
  public void doStep() {
    freeway.step();
  }

  /**
   * Resets animation to a predefined state.
   */
  public void reset() {
    control.setValue("Number of cars", 10);
    control.setValue("Road length", 50);
    control.setValue("Slow down probability", 0.5);
    control.setValue("Maximum velocity", 2);
    control.setValue("Steps between plots", 1);
    enableStepsPerDisplay(true);
  }

  /**
   * Resets data without changing configuration
   */
  public void resetAverages() {
    freeway.flow = 0;
    freeway.steps = 0;
  }

  /**
   * Starts Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new FreewayApp());
    control.addButton("resetAverages", "resetAverages");
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
