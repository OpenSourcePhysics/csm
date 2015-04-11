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
 * Simulates random walkers in one dimension
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class WalkerApp extends AbstractSimulation {
  Walker walker = new Walker();
  PlotFrame plotFrame = new PlotFrame("time", "<x>,<x^2>", "Averages");
  HistogramFrame distribution = new HistogramFrame("x", "H(x)", "Histogram");
  int trials; // number of trials

  /**
   *   Sets column names for data table
   */
  public WalkerApp() {
    plotFrame.setXYColumnNames(0, "t", "<x>");
    plotFrame.setXYColumnNames(1, "t", "<x^2>");
  }

  /**
   *   Gets parameters and initializes model
   */
  public void initialize() {
    walker.p = control.getDouble("Probability p of step to right");
    walker.N = control.getInt("Number of steps N");
    walker.initialize();
    trials = 0;
  }

  /**
   * Does one walker at a time
   */
  public void doStep() {
    trials++;
    walker.step();
    distribution.append(walker.position);
    distribution.setMessage("trials = "+trials);
  }

  /**
   *  Plots data when user stops the simulation.
   */
  public void stopRunning() {
    plotFrame.clearData();
    for(int t = 0;t<=walker.N;t++) {
      double xbar = walker.xAccum[t]*1.0/trials;
      double x2bar = walker.xSquaredAccum[t]*1.0/trials;
      plotFrame.append(0, 1.0*t, xbar);
      plotFrame.append(1, 1.0*t, x2bar-xbar*xbar);
    }
    plotFrame.repaint();
  }

  /**
   *  Resets to default values
   */
  public void reset() {
    control.setValue("Probability p of step to right", 0.5);
    control.setValue("Number of steps N", 100);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new WalkerApp());
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
