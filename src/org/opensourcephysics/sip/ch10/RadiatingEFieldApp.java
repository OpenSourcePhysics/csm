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
 * RadiatingEFieldApp models the radiating electric field from an accelerating point
 * charge using Lienard-Wiechert potentials.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class RadiatingEFieldApp extends AbstractSimulation {
  Vector2DFrame frame = new Vector2DFrame("x", "y", "Electric field");
  RadiatingCharge charge = new RadiatingCharge();
  int gridSize;     // linear dimension of grid used to compute fields
  double[][][] Exy; // x and y components of electric field
  double xmin = -20, xmax = 20, ymin = -20, ymax = 20;

  /**
   * The RadiationApp constructor.
   */
  public RadiatingEFieldApp() {
    frame.setPreferredMinMax(xmin, xmax, ymin, ymax);
    frame.setZRange(false, 0, 0.2);
    frame.addDrawable(charge);
  }

  /**
   * Initializes the animation.
   */
  public void initialize() {
    gridSize = control.getInt("size");
    Exy = new double[2][gridSize][gridSize];
    charge.vmax = control.getDouble("vmax"); // maximum speed of charge
    charge.dt = control.getDouble("dt");
    frame.setAll(Exy);
    initArrays();
  }

  /**
   * Initializes the arrays.
   */
  private void initArrays() {
    charge.resetPath();
    calculateFields();
  }

  /**
   * Calculates the fields.
   */
  private void calculateFields() {
    double[] fields = new double[3]; // Ex, Ey, Bz
    for(int i = 0;i<gridSize;i++) {
      for(int j = 0;j<gridSize;j++) {
        double x = frame.indexToX(i); // x location where we calculate the field
        double y = frame.indexToY(j); // y location where we calculate the field
        charge.calculateRetardedField(x, y, fields); // return the retarded time
        Exy[0][i][j] = fields[0]; // Ex
        Exy[1][i][j] = fields[1]; // Ey
      }
    }
    frame.setAll(Exy);
  }

  /**
   * Resets the model to a predefined state.
   */
  public void reset() {
    control.setValue("size", 31);
    control.setValue("dt", 0.5);
    control.setValue("vmax", 0.9);
    initialize(); // initialize the model
  }

  /**
   * doStep
   */
  protected void doStep() {
    charge.step();
    calculateFields();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new RadiatingEFieldApp());
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
