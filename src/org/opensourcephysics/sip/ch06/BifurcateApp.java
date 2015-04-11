/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch06;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * BifurcateApp demonstrates chaos in the logistic equation by plotting the
 * return map for different values of r.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class BifurcateApp extends AbstractSimulation {
  double r;       // control parameter
  double dr;      // incremental change of r, suggest dr <= 0.01
  int ntransient; // number of iterations not plotted
  int nplot;      // number of iterations plotted
  PlotFrame plotFrame = new PlotFrame("r", "x", "Bifurcation diagram");

  /**
   * Constructs the Bifurcate model.
   */
  public BifurcateApp() {
    plotFrame.setMarkerSize(0, 0); // small size gives better resolution
    plotFrame.setMarkerSize(1, 0);
  }

  /**
   * Initializes the bifrucation diagram.
   */
  public void initialize() {
    plotFrame.clearData();
    r = control.getDouble("initial r");
    dr = control.getDouble("dr");
    ntransient = control.getInt("ntransient");
    nplot = control.getInt("nplot");
  }

  /**
   * Does a step by adding to the diagram using the current value of r.
   *
   * Increments the value of r at the end of the step in preparation for
   * the next set.
   */
  public void doStep() {
    if(r<1.0) {
      double x = 0.5;
      for(int i = 0;i<ntransient;i++) { // x values not plotted
        x = map(x, r);
      }
      for(int i = 0;i<nplot/2;i++) {    // plot half the points in dataset zero
        x = map(x, r);
        plotFrame.append(0, r, x);      // shows different x values for given value of r
      }
      for(int i = nplot/2+1;i<nplot;i++) { // plot remaining points in dataset one
        x = map(x, r);
        plotFrame.append(1, r, x); // dataset one has a different color
      }
      r += dr;
    }
  }

  /**
   * Resets all parameters to their defaults.
   */
  public void reset() {
    control.setValue("initial r", 0.2);
    control.setValue("dr", 0.005);
    control.setValue("ntransient", 200);
    control.setValue("nplot", 50);
  }

  /**
   * Computes the return map for the given parameters.
   *
   * @param x double
   * @param r double
   * @return double
   */
  double map(double x, double r) {
    return 4*r*x*(1-x);
  }

  /**
   * The main method starts the Java applicaiton.
   * @param args String[] command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new BifurcateApp());
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
