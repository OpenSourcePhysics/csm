/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch07;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.PlotFrame;

/**
 * Simulates nuclear decay
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class NuclearDecayApp extends AbstractSimulation {
  Nuclei nuclei = new Nuclei();
  PlotFrame plotFrame = new PlotFrame("Time", "Number of unstable nuclei", "Nuclear decay data");
  int trials; // number of trials

  /**
   *   Gets parameters and initializes model
   */
  public void initialize() {
    nuclei.n0 = control.getInt("Initial number of unstable nuclei");
    nuclei.p = control.getDouble("Decay probability");
    nuclei.tmax = control.getInt("Maximum time to collect data");
    nuclei.initialize();
    plotFrame.clearData();
    trials = 0;
  }

  /**
   *   Does nuclear decay trials
   */
  public void doStep() {
    trials++;
    nuclei.step();
    plotFrame.setMessage("trials = "+trials);
  }

  /**
   *  Plots data when user stops thread
   */
  public void stop() {
    plotFrame.clearData();
    for(int t = 0;t<=nuclei.tmax;t++) {
      plotFrame.append(0, 1.0*t, nuclei.n[t]*1.0/trials);
    }
    plotFrame.render();
  }

  /**
   *  Resets to default values
   */
  public void reset() {
    control.setValue("Initial number of unstable nuclei", 10000);
    control.setValue("Decay probability", 0.001);
    control.setValue("Maximum time to collect data", 100);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new NuclearDecayApp());
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
