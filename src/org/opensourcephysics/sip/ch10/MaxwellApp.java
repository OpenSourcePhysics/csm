/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.Vector2DFrame;

/**
 * MaxwellApp  creates the Maxwell model and displays time dependent solution to Maxwell's equations.
 *
 * Method plotField() revised 04/04/2006.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.1
 */
public class MaxwellApp extends AbstractSimulation {
  Vector2DFrame frame = new Vector2DFrame("x", "y", "EField in XY Plane");
  int size;
  Maxwell maxwell;
  double[][][] Exy; // x and y components of E for middle plane in z direction

  /**
   * Constructor MaxwellApp
   */
  public MaxwellApp() {
    frame.setZRange(false, 0, 1.0);
  }

  /**
   * Resets the model to a predefined state.
   */
  public void reset() {
    control.setValue("size", 31);
    control.setValue("dt", 0.5);
  }

  /**
   * Initializes the animation.
   */
  public void initialize() {
    size = control.getInt("size");
    Exy = new double[2][size][size];
    maxwell = new Maxwell(size);
    frame.setAll(Exy);
    frame.setPreferredMinMax(0, Maxwell.dl*size, Maxwell.dl*size, 0);
    plotField();
  }

  /**
   * Does a time step.
   */
  protected void doStep() {
    maxwell.doStep();
    plotField();
    frame.setMessage("t="+decimalFormat.format(maxwell.t));
  }

  /**
   * Plots the electric field using the arrays in the Maxwell object.
   */
  void plotField() {
    double[][][][] E = maxwell.E; // electric field
    int mid = size/2;
    for(int i = 0;i<size;i++) {
      for(int j = 0;j<size;j++) {
        Exy[0][i][j] = E[0][i][j][mid]; // Ex
        Exy[1][i][j] = E[1][i][j][mid]; // Ey
      }
    }
    frame.setAll(Exy);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new MaxwellApp());
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
