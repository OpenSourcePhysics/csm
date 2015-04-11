/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch04;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.*;

/**
 * RCApp solves and plots the voltage across a capacitor in an RC circuit driven
 * by a sinusoidal driving voltage.
 *
 * This application demonstrates:
 * <ol>
 *   <li>how to use the ODE interface.</li>
 *   <li>how to use the ODESolver interface.</li>
 *   <li>how to use the Animation control to run and single-step a differential equation.</li>
 *   <li>how to display the ODE solution in a graph.</li>
 * </ol>
 * Students should test other ODESolvers in the Numerics package.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class RCApp extends AbstractSimulation {
  PlotFrame plottingFrame = new PlotFrame("Time(s)", "Charge", "RC Circuit");
  RC rc;
  Euler odeSolver;

  /**
   * Constructs the RCApp
   */
  public RCApp() {
    rc = new RC(0, 0, 0);
    odeSolver = new Euler(rc); // create numerical method
  }

  /*
   * Initializes the animation using the values in the control
   */
  public void initialize() {
    rc.r = control.getDouble("r");
    rc.c = control.getDouble("c");
    rc.omega = control.getDouble("omega");
    odeSolver.setStepSize(control.getDouble("dt"));
    plottingFrame.append(0, rc.state[1], rc.getSourceVoltage(rc.state[1])); // voltage data
    plottingFrame.append(1, rc.state[1], rc.state[0]); // charge data
    plottingFrame.setMessage("time ="+decimalFormat.format(rc.state[1]));
    plottingFrame.setMessage("charge="+decimalFormat.format(rc.state[0]), 0);
  }

  /**
   * Does an animation step
   */
  public void doStep() {
    odeSolver.step();                                                       // advance state by current step size
    plottingFrame.append(0, rc.state[1], rc.getSourceVoltage(rc.state[1])); // voltage data
    plottingFrame.append(1, rc.state[1], rc.state[0]); // charge data
    plottingFrame.setMessage("time ="+decimalFormat.format(rc.state[1]));
    plottingFrame.setMessage("charge="+decimalFormat.format(rc.state[0]), 0);
  }

  /**
   * Resets animation to a predefined state
   */
  public void reset() {
    control.setValue("r", 1.0);     // initial decay constant
    control.setValue("c", 1.0);     // initial decay constant
    control.setValue("omega", 1.0); // initial decay constant
    control.setValue("dt", 0.1);    // initial step size
    odeSolver.setStepSize(0.1);     // initial step size
    rc.state[1] = 0; // initial time
    rc.state[0] = 0; // initial charge
    initialize();
  }

  /**
   * Start Java application
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new RCApp());
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
