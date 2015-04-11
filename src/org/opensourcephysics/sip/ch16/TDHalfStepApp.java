/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.ComplexPlotFrame;

/**
 * TDHalfStepApp solves the time-independent Schroedinger equation using the half-step algorithm.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class TDHalfStepApp extends AbstractSimulation {
  ComplexPlotFrame psiFrame = new ComplexPlotFrame("x", "|Psi|", "Wave function");
  TDHalfStep wavefunction;
  double time;

  /**
   * Constructors TDHalfStepApp
   */
  public TDHalfStepApp() {
    psiFrame.limitAutoscaleY(-1, 1); // do not autoscale within this y-range.
  }

  /**
   * Initializes the wave function.
   */
  public void initialize() {
    time = 0;
    psiFrame.setMessage("t="+0);
    double xmin = control.getDouble("xmin");
    double xmax = control.getDouble("xmax");
    int numberOfPoints = control.getInt("number of points");
    double width = control.getDouble("packet width");
    double x0 = control.getDouble("packet offset");
    double momentum = control.getDouble("packet momentum");
    GaussianPacket packet = new GaussianPacket(width, x0, momentum);
    wavefunction = new TDHalfStep(packet, numberOfPoints, xmin, xmax);
    psiFrame.clearData(); // removes old data
    psiFrame.append(wavefunction.x, wavefunction.realPsi, wavefunction.imagPsi);
  }

  /**
   * Does a simulation step.
   */
  public void doStep() {
    time += wavefunction.step();
    psiFrame.clearData();
    psiFrame.append(wavefunction.x, wavefunction.realPsi, wavefunction.imagPsi);
    psiFrame.setMessage("t="+decimalFormat.format(time));
  }

  /**
   * Resets the simulation.
   */
  public void reset() {
    control.setValue("xmin", -20);
    control.setValue("xmax", 20);
    control.setValue("number of points", 500);
    control.setValue("packet width", 1);
    control.setValue("packet offset", -15);
    control.setValue("packet momentum", 2);
    setStepsPerDisplay(10); // multiple computations per animation step
    enableStepsPerDisplay(true);
    initialize();
  }

  /**
   * Starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new TDHalfStepApp());
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
