/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch04;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * PendulumApp solves and displays the time evolution of a pendulum by stepping a pendulum model.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class PendulumApp extends AbstractSimulation {
  PlotFrame plotFrame = new PlotFrame("Time", "Theta", "Theta versus time");
  Pendulum pendulum = new Pendulum();
  DisplayFrame displayFrame = new DisplayFrame("Pendulum");

  /**
   * Constructs the PendulumApp and intializes the display.
   */
  public PendulumApp() {
    displayFrame.addDrawable(pendulum);
    displayFrame.setPreferredMinMax(-1.2, 1.2, -1.2, 1.2);
  }

  /**
   * Initializes the simulation.
   */
  public void initialize() {
    double dt = control.getDouble("dt");
    double theta = control.getDouble("initial theta");
    double thetaDot = control.getDouble("initial dtheta/dt");
    pendulum.setState(theta, thetaDot);
    pendulum.setStepSize(dt);
  }

  /**
   * Does a time step.
   */
  public void doStep() {
    plotFrame.append(0, pendulum.state[2], pendulum.state[0]); // angle vs time data added
    pendulum.step(); // advances the state by one time step
  }

  /**
   * Resets the simulation.
   */
  public void reset() {
    pendulum.state[2] = 0; // set time = 0
    control.setValue("initial theta", 0.2);
    control.setValue("initial dtheta/dt", 0);
    control.setValue("dt", 0.1);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // creates a simulation control structure using this class
    SimulationControl.createApp(new PendulumApp());
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
