/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch04;
import java.awt.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

/**
 * Pendulum models the dynamics of a pendulum.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Pendulum implements Drawable, ODE {
  double omega0Squared = 3;                // g/L
  double[] state = new double[] {0, 0, 0}; // {theta, dtheta/dt, t}
  Color color = Color.RED;
  int pixRadius = 6;
  EulerRichardson odeSolver = new EulerRichardson(this);

  /**
   * Sets the ODESolver's time step.
   *
   * @param dt double
   */
  public void setStepSize(double dt) {
    odeSolver.setStepSize(dt);
  }

  /**
   * Steps (advances) the time.
   *
   * @param dt the time step.
   */
  public void step() {
    odeSolver.step(); // execute one Euler-Richardson step
  }

  /**
   * Sets the state.
   *
   * @param theta
   * @param dtheta/dt
   */
  public void setState(double theta, double thetaDot) {
    state[0] = theta;
    state[1] = thetaDot; // time rate of change of theta
  }

  /**
   * Gets the state.  Required for ODE interface.
   * @return double[] the state
   */
  public double[] getState() {
    return state;
  }

  /**
   * Gets the rate.  Required for ODE inteface
   * @param state double[] the state
   * @param rate double[]  the computed rate
   */
  public void getRate(double[] state, double[] rate) {
    rate[0] = state[1];                          // rate of change of angle
    rate[1] = -omega0Squared*Math.sin(state[0]); // rate of change of dtheta/dt
    rate[2] = 1;                                 // rate of change of time dt/dt = 1
  }

  /**
   * Draws the pendulum. Required for Drawable interface.
   *
   * @param drawingPanel
   * @param g
   */
  public void draw(DrawingPanel drawingPanel, Graphics g) {
    int xpivot = drawingPanel.xToPix(0);
    int ypivot = drawingPanel.yToPix(0);
    int xpix = drawingPanel.xToPix(Math.sin(state[0]));
    int ypix = drawingPanel.yToPix(-Math.cos(state[0]));
    g.setColor(Color.black);
    g.drawLine(xpivot, ypivot, xpix, ypix);                               // the string
    g.setColor(color);
    g.fillOval(xpix-pixRadius, ypix-pixRadius, 2*pixRadius, 2*pixRadius); // bob
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
