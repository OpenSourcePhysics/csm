/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * OscillatorsApp displays a system of coupled oscillators in a drawing panel.
 *
 * The separation between oscillators is one in the current model.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class OscillatorsApp extends AbstractSimulation {
  DisplayFrame displayFrame = new DisplayFrame("Position", "Displacement", "Oscillators");
  Oscillators oscillators;
  double dt;

  /**
   * Initializes the simulation by creating a system of oscillators.
   */
  public void initialize() {
    dt = control.getDouble("dt"); // time step
    int mode = control.getInt("mode");
    int N = control.getInt("number of particles");
    oscillators = new Oscillators(mode, N);
    displayFrame.setPreferredMinMax(0, N+1, -1.5, 1.5);
    displayFrame.clearDrawables(); // remove old oscillators
    displayFrame.setSquareAspect(false);
    displayFrame.addDrawable(oscillators);
  }

  /**
   * Does a time step
   */
  public void doStep() {
    oscillators.step(dt); // advance the state by dt
    displayFrame.setMessage("t = "+decimalFormat.format(oscillators.time));
  }

  /**
   * Resets the oscillator program to its default values.
   */
  public void reset() {
    control.setValue("number of particles", 16);
    control.setValue("mode", 1);
    control.setValue("dt", 0.5);
    initialize();
  }

  /**
   * Creates the oscillator program from the command line
   *
   * @param args
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new OscillatorsApp());
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
