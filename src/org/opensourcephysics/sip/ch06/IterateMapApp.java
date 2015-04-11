/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch06;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.controls.*;

/**
 * IterateMapApp calculates and plots multiple trajectories of the logistic equation.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 05/17/05
 */
public class IterateMapApp extends AbstractCalculation {
  int datasetIndex = 0;
  PlotFrame plotFrame = new PlotFrame("iterations", "x", "trajectory");

  /**
   * Constructs the IterateMapApp.
   */
  public IterateMapApp() {
    plotFrame.setAutoclear(false); // keep data between calls to calculate
  }

  /**
   * Resets all parameters to their defaults.
   */
  public void reset() {
    control.setValue("r", 0.2);
    control.setValue("x", 0.6);
    control.setValue("iterations", 50);
    datasetIndex = 0;
  }

  /**
   * Calculates and plots the trajectory of the map.
   */
  public void calculate() {
    double r = control.getDouble("r");
    double x = control.getDouble("x");
    int iterations = control.getInt("iterations");
    for(int i = 0;i<=iterations;i++) {
      plotFrame.append(datasetIndex, i, x);
      x = map(r, x);
    }
    plotFrame.setMarkerSize(datasetIndex, 1);
    plotFrame.setXYColumnNames(datasetIndex, "iteration", "calc #"+datasetIndex);
    datasetIndex++;
  }

  /**
   * Computes the return map for the given parameters.
   *
   * @param x double
   * @param r double
   * @return double
   */
  double map(double r, double x) {
    return 4*r*x*(1-x); // iterate map
  }

  /**
   * The main method starts the Java applicaiton.
   * @param args String[] command line parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new IterateMapApp());
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
