/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch05;
import java.awt.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

/**
 * Planet models and displays the motion of a planet using an inverse square force law.
 *
 * Students should test other ODESolvers in the Numerics package.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Planet implements Drawable, ODE {
  // GM in units of (AU)^3/(yr)^2
  final static double GM = 4*Math.PI*Math.PI;
  Circle circle = new Circle();
  Trail trail = new Trail();
  double[] state = new double[5];    // {x,vx,y,vy,t}
  Euler odeSolver = new Euler(this); // creates numerical method

  /**
   * Steps the differential equation and appends data to curve.
   */
  public void doStep() {
    odeSolver.step();                   // advances time
    trail.addPoint(state[0], state[2]); // x,y
  }

  /**
   * Initializes the planet's position, velocity, and time.
   *
   * @param initState the initial state
   */
  void initialize(double[] initState) {
    System.arraycopy(initState, 0, state, 0, initState.length);
    // reinitializes the solver in case the solver accesses data from previous steps
    odeSolver.initialize(odeSolver.getStepSize());
    trail.clear();
  }

  /**
   * Gets the rate using the given state.
   * Values in the rate array are overwritten.
   *
   * @param state the state
   * @param rate  the resulting rate
   */
  public void getRate(double[] state, double[] rate) {
    // state[]: x, vx, y, vy, t
    double r2 = (state[0]*state[0])+(state[2]*state[2]); // r squared
    double r3 = r2*Math.sqrt(r2);                        // r cubed
    rate[0] = state[1];          // x rate
    rate[1] = (-GM*state[0])/r3; // vx rate
    rate[2] = state[3];          // y rate
    rate[3] = (-GM*state[2])/r3; // vy rate
    rate[4] = 1;                 // time rate
  }

  /**
   * Gets the state
   * @return the state
   */
  public double[] getState() {
    return state;
  }

  /**
   * Draws the planet and its path.
   *
   * @param panel the drawing panel
   * @param g the graphics context
   */
  public void draw(DrawingPanel panel, Graphics g) {
    circle.setXY(state[0], state[2]);
    circle.draw(panel, g);
    trail.draw(panel, g);
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
