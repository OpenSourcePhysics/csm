/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch05;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * PlanetApp models an orbiting planet.
 *
 * This program demonstrates:
 * how to use the Simulation control to run and single step a time dependent model.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class PlanetApp extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x (AU)", "y (AU)", "Planet Simulation");
  Planet planet = new Planet();

  /**
   * Constructs the PlanetApp.
   */
  public PlanetApp() {
    frame.addDrawable(planet);
    frame.setPreferredMinMax(-5, 5, -5, 5);
    frame.setSquareAspect(true);
  }

  /**
   * Steps the time.
   */
  public void doStep() {
    for(int i = 0;i<5;i++) { // do 5 steps between screen draws
      planet.doStep();       // advances time
    }
    frame.setMessage("t = "+decimalFormat.format(planet.state[4]));
  }

  /**
   * Initializes the animation using the values in the control.
   */
  public void initialize() {
    planet.odeSolver.setStepSize(control.getDouble("dt"));
    double x = control.getDouble("x");
    double vx = control.getDouble("vx");
    double y = control.getDouble("y");
    double vy = control.getDouble("vy");
    // create an array on the fly as the argument to another method
    planet.initialize(new double[] {x, vx, y, vy, 0});
    frame.setMessage("t = 0");
  }

  /**
   * Resets animation to a predefined state.
   */
  public void reset() {
    control.setValue("x", 1);
    control.setValue("vx", 0);
    control.setValue("y", 0);
    control.setValue("vy", 6.28);
    control.setValue("dt", 0.01);
    initialize();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new PlanetApp());
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
