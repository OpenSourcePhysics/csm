/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch06;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.Display3DFrame;

/**
 * LorenzApp models the Lorenz attractor by extending AbstractAnimation
 * and implementing the doStep method.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/06/05
 */
public class LorenzApp extends AbstractSimulation {
  Display3DFrame frame = new Display3DFrame("Lorenz Attractor");
  Lorenz lorenz = new Lorenz();

  /**
   * Constructs Ball3dApp and in initializes the drawing and the plot.
   */
  public LorenzApp() {
    frame.setPreferredMinMax(-15.0, 15.0, -15.0, 15.0, 0.0, 50.0);
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_AXES);
    frame.addElement(lorenz);
  }

  /**
   * Initializes the animation.
   */
  public void initialize() {
    double x = control.getDouble("initial x");
    double y = control.getDouble("initial y");
    double z = control.getDouble("initial z");
    double dt = control.getDouble("dt");
    lorenz.initialize(x, y, z);
    lorenz.ode_solver.initialize(dt);
    frame.setVisible(true);
  }

  /**
   * Resets the animation.
   */
  public void reset() {
    control.setValue("initial x", 2);
    control.setValue("initial y", 5);
    control.setValue("initial z", 20);
    control.setValue("dt", 0.01);
    initialize();
  }

  /**
   * Does an animation step.
   */
  protected void doStep() {
    lorenz.doStep();
    frame.setMessage("t="+decimalFormat.format(lorenz.state[3]));
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new LorenzApp());
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
