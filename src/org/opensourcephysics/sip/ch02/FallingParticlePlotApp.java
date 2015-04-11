/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * FallingParticlePlotApp computes the time for a particle to fall to the ground and plots the position as a funciton of time.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  05/07/05
 */
public class FallingParticlePlotApp extends AbstractCalculation {
  PlotFrame plotFrame = new PlotFrame("t", "y", "Falling Ball");

  /**
   * Calculates the trajectory of a falling particle and plots the position as a function of time.
   */
  public void calculate() {
    plotFrame.setAutoclear(false); // data not cleared at beginning of each calculation
    // gets initial conditions
    double y0 = control.getDouble("Initial y");
    double v0 = control.getDouble("Initial v");
    // sets initial conditions
    Particle ball = new FallingParticle(y0, v0);
    // gets parameters
    ball.dt = control.getDouble("dt");
    while(ball.y>0) {
      ball.step();
      plotFrame.append(0, ball.t, ball.y);
      plotFrame.append(1, ball.t, ball.analyticPosition());
    }
  }

  /**
   * Resets the program to its initial state.
   */
  public void reset() {
    control.setValue("Initial y", 10);
    control.setValue("Initial v", 0);
    control.setValue("dt", 0.01);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // sets up calculation control structure using this class
    CalculationControl.createApp(new FallingParticlePlotApp());
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
