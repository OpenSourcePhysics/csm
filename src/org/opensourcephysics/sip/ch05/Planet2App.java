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
 * Planet2App models and displays two interacting planets in the presence of a central inverse square law force.
 *
 * This application demonstrates:
 * <ol>
 *   <li>how to use the ODE interface.</li>
 *   <li>how to use the ODESolver interface.</li>
 *   <li>how to use the Animation control to run and single-step a differential equation.</li>
 * </ol>
 * Students should test other ODESolvers in the Numerics package.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Planet2App extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x (AU)", "y (AU)", "Planet Program");
  Planet2 planet2 = new Planet2();

  /**
   * Constructs the PlanetApp.
   */
  public Planet2App() {
    frame.addDrawable(planet2);
    frame.setPreferredMinMax(-10, 10, -10, 10);
    frame.setSquareAspect(true);
  }

  /**
   * Steps the time.
   */
  public void doStep() {
    for(int i = 0;i<5;i++) { // do 5 steps between screen draws
      planet2.doStep();      // advances time
    }
    frame.setMessage("t="+decimalFormat.format(planet2.state[4]));
  }

  /**
   * Initializes the animation using the values in the control.
   */
  public void initialize() {
    planet2.odeSolver.setStepSize(control.getDouble("dt"));
    double x1 = control.getDouble("x1");
    double vy1 = control.getDouble("vy1");
    double x2 = control.getDouble("x2");
    double vy2 = control.getDouble("vy2");
    // planet2.state= {x1, vx1, y1, vy1, x2, vx2, y2, vy2, t}
    planet2.initialize(new double[] {
      x1, 0, 0, vy1, x2, 0, 0, vy2, 0
    });
    frame.setMessage("t=0");
  }

  /**
   * Resets animation to a predefined state.
   */
  public void reset() {
    control.setValue("x1", 2.52);
    control.setValue("vy1", Math.sqrt(Planet.GM/2.52));
    control.setValue("x2", 5.24);
    control.setValue("vy2", Math.sqrt(Planet.GM/5.24));
    control.setValue("dt", 0.01); // initial step size
    initialize();
  }

  /**
   * Start Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new Planet2App());
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
