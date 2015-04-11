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
 * BouncingBall models a collection of bouncing balls.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0 05/07/05
 */
public class BouncingBallApp extends AbstractSimulation {
  // declares and instantiates a window to draw balls
  DisplayFrame frame = new DisplayFrame("x", "y", "Bouncing Balls");
  BouncingBall[] ball; // declares an array of BouncingBall objects
  double time, dt;

  /**
   * Initializes the simulation by creating the BouncingBall objects and adding them
   * to the frame.
   */
  public void initialize() {
    // sets boundaries of window in world coordinates
    frame.setPreferredMinMax(-10.0, 10.0, 0, 10);
    time = 0;
    frame.clearDrawables(); // removes old particles
    int n = control.getInt("number of balls");
    int v = control.getInt("speed");
    ball = new BouncingBall[n]; // instantiates array of n BouncingBall objects
    for(int i = 0;i<n;i++) {
      double theta = Math.PI*Math.random(); // random angle
      // instantiates the ith BouncingBall object
      ball[i] = new BouncingBall(0, v*Math.cos(theta), 0, v*Math.sin(theta));
      frame.addDrawable(ball[i]);           // // adds ball to frame so that it will be displayed
    }
    // decimalFormat instantiated in superclass and used to format numbers conveniently
    frame.setMessage("t = "+decimalFormat.format(time)); // appears in lower right hand corner
  }

  /**
   * Does a simulation step by stepping (advancing) each ball.
   */
  public void doStep() { // invoked every 1/10 second by timer in AbstractSimulation superclass
    for(int i = 0;i<ball.length;i++) {
      ball[i].step(dt);
    }
    time += dt;
    frame.setMessage("t="+decimalFormat.format(time));
  }

  /**
   * Checks the time step parameter whenever the start or step button is pressed.
   */
  public void startRunning() { // invoked when start or step button is pressed
    dt = control.getDouble("dt");
  } // gets time step

  /**
   * Resets the simulation parameters to their intial state.
   */
  public void reset() { // invoked when reset button is pressed
    control.setAdjustableValue("dt", 0.1); // allows dt to be changed after initializaton
    control.setValue("number of balls", 40);
    control.setValue("speed", 10);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // sets up animation control structure using this class
    SimulationControl.createApp(new BouncingBallApp());
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
