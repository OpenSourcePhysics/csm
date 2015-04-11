/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

/**
 * SchroedingerApp displays a solution to the time-independent Schroedinger equation.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class SchroedingerApp extends AbstractCalculation {
  PlotFrame frame = new PlotFrame("x", "phi", "Wave function");
  Schroedinger schroedinger = new Schroedinger();

  /**
   * Constructs SchroedingerApp and sets plotting frame parameters.
   */
  public SchroedingerApp() {
    frame.setConnected(0, true);
    frame.setMarkerShape(0, Dataset.NO_MARKER);
  }

  /**
   * Calculates the wave function.
   */
  public void calculate() {
    schroedinger.xmin = control.getDouble("xmin");
    schroedinger.xmax = control.getDouble("xmax");
    schroedinger.stepHeight = control.getDouble("step height at x = 0");
    schroedinger.numberOfPoints = control.getInt("number of points");
    schroedinger.energy = control.getDouble("energy");
    schroedinger.initialize();
    schroedinger.solve();
    frame.append(0, schroedinger.x, schroedinger.phi);
  }

  /**
   * Resets the calculation parameters.
   */
  public void reset() {
    control.setValue("xmin", -5);
    control.setValue("xmax", 5);
    control.setValue("step height at x = 0", 1);
    control.setValue("number of points", 500);
    control.setValue("energy", 1);
  }

  /**
   * The main method starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new SchroedingerApp(), args);
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
