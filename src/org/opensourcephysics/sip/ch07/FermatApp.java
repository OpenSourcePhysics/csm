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
 * Simulates use of Fermat's principle to find light path that minimizes time
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class FermatApp extends AbstractSimulation {
  Fermat medium = new Fermat();
  PlotFrame path = new PlotFrame("x", "y", "Light path");

  /**
   * Sets path frame properties
   */
  public FermatApp() {
    path.setAutoscaleX(true);
    path.setAutoscaleY(true);
    path.setConnected(true); // draw lines between points
  }

  /**
   * Gets parameters and initializes medium
   */
  public void initialize() {
    medium.dn = control.getDouble("Change in index of refraction");
    medium.N = control.getInt("Number of media segments");
    medium.initialize();
    path.clearData();
  }

  /**
   * Makes one change in path at a time
   */
  public void doStep() {
    medium.step();
    path.clearData();
    for(int i = 0;i<=medium.N;i++) {
      path.append(0, i, medium.y[i]);
    }
    path.setMessage(medium.steps+" steps");
  }

  /**
   * Resets to default values
   */
  public void reset() {
    control.setValue("Change in index of refraction", 0.5);
    control.setValue("Number of media segments", 2);
    path.clearData();
    enableStepsPerDisplay(true);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new FermatApp());
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
