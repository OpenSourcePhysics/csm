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
 * Planet2 models two interacting planets in the presence of a central inverse
 * square law force.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Planet2 implements Drawable, ODE {
  // GM in units of (AU)^3/(yr)^2
  final static double GM = 4*Math.PI*Math.PI;
  final static double GM1 = 0.04*GM;
  final static double GM2 = 0.001*GM;
  double[] state = new double[9];
  ODESolver odeSolver = new RK45MultiStep(this);
  Mass mass1 = new Mass(), mass2 = new Mass();

  /**
   * Steps the time using an ode solver.
   */
  public void doStep() {
    odeSolver.step();
    mass1.setXY(state[0], state[2]);
    mass2.setXY(state[4], state[6]);
  }

  /**
   * Draws the three bodies.
   *
   * @param panel
   * @param g
   */
  public void draw(DrawingPanel panel, Graphics g) {
    mass1.draw(panel, g);
    mass2.draw(panel, g);
  }

  /**
   * Initializes the positions and velocities with the given state.
   * @param initState
   */
  void initialize(double[] initState) {
    System.arraycopy(initState, 0, state, 0, initState.length);
    mass1.clear(); // clears data from the old trail
    mass2.clear();
    mass1.setXY(state[0], state[2]);
    mass2.setXY(state[4], state[6]);
  }

  /**
   * Gets the rate using the given state.
   *
   * Values in the rate array are overwritten.
   *
   * @param state  the state
   * @param rate   the resulting rate
   */
  public void getRate(double[] state, double[] rate) {
    // state[]: x1, vx1, y1, vy1, x2, vx2, y2, vy2, t
    double r1Squared = (state[0]*state[0])+(state[2]*state[2]); // r1 squared
    double r1Cubed = r1Squared*Math.sqrt(r1Squared);            // r1 cubed
    double r2Squared = (state[4]*state[4])+(state[6]*state[6]); // r2 squared
    double r2Cubed = r2Squared*Math.sqrt(r2Squared);            // r2 cubed
    double dx = state[4]-state[0];                              // x12 separation
    double dy = state[6]-state[2];                              // y12 separation
    double dr2 = (dx*dx)+(dy*dy);                               // r12 squared
    double dr3 = Math.sqrt(dr2)*dr2;                            // r12 cubed
    rate[0] = state[1];                                // x1 rate
    rate[2] = state[3];                                // y1 rate
    rate[4] = state[5];                                // x2 rate
    rate[6] = state[7];                                // y2 rate
    rate[1] = ((-GM*state[0])/r1Cubed)+((GM1*dx)/dr3); // vx1 rate
    rate[3] = ((-GM*state[2])/r1Cubed)+((GM1*dy)/dr3); // vy1 rate
    rate[5] = ((-GM*state[4])/r2Cubed)-((GM2*dx)/dr3); // vx2 rate
    rate[7] = ((-GM*state[6])/r2Cubed)-((GM2*dy)/dr3); // vy2 rate
    rate[8] = 1;                                       // time rate
  }

  /**
   * Gets the state: x1, vx1, y1, vy1, x2, vx2, y2, vy2, t.
   *
   * @return the state
   */
  public double[] getState() {
    return state;
  }

  /**
   * Class Mass
   */
  class Mass extends Circle {
    Trail trail = new Trail();

    /**
     * Draws the mass
     *
     * @param panel
     * @param g
     */
    public void draw(DrawingPanel panel, Graphics g) {
      trail.draw(panel, g);
      super.draw(panel, g);
    }

    /**
     * Clears the trail.
     *
     */
    void clear() {
      trail.clear();
    }

    /**
     * Sets the postion and adds to the trail.
     *
     * @param x
     * @param y
     */
    public void setXY(double x, double y) {
      super.setXY(x, y);
      trail.addPoint(x, y);
    }
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
