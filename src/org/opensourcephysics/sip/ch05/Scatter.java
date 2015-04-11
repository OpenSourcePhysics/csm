/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */
package org.opensourcephysics.sip.ch05;
import java.awt.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.*;

/**
 * Scatter models particle scattering using a central force law.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Scatter implements ODE {
  double[] state = new double[5];
  RK4 odeSolver = new RK4(this);

  /**
   * Constructs Scatter and sets the ODE solver step size.
   */
  public Scatter() {
    odeSolver.setStepSize(0.05);
  }

  /**
   * Calculates a trajectory and adds a trail to the plot frame to show the path.
   *
   * @param drawingPanel
   * @param b  the impact parameter
   * @param vx the initial velocity
   *
   * @return true if trajectory valid; false otherwise
   */
  boolean calculateTrajectory(PlotFrame frame, double b, double vx) {
    state[0] = -5.0; // x
    state[1] = vx;   // vx
    state[2] = b;    // y
    state[3] = 0;    // vy
    state[4] = 0;    // time
    Trail trail = new Trail();
    trail.color = Color.red;
    frame.addDrawable(trail);
    double r2 = (state[0]*state[0])+(state[2]*state[2]);
    double count = 0;
    while((count<=1000)&&((2*r2)>((state[0]*state[0])+(state[2]*state[2])))) {
      trail.addPoint(state[0], state[2]);
      odeSolver.step();
      count++;
    }
    return count<1000;
  }

  /**
   * Gets the magnituce of the central force.
   *
   * @param r the distance from the center
   * @return the force
   */
  private double force(double r) {
    // Coulomb force law
    return(r==0) ? 0 : (1/r/r); // returns 0 if r = 0
  }

  /**
   * Gets the rate array using the given state, {x,vx,y,vy,t}.
   *
   * @param state the state
   * @param rate  the rate
   */
  public void getRate(double[] state, double[] rate) {
    double r = Math.sqrt((state[0]*state[0])+(state[2]*state[2]));
    double f = force(r);
    rate[0] = state[1];
    rate[1] = (f*state[0])/r;
    rate[2] = state[3];
    rate[3] = (f*state[2])/r;
    rate[4] = 1;
  }

  /**
   * Gets the state array, {x,vx,y,vy,t}.
   * @return
   */
  public double[] getState() {
    return state;
  }

  /**
   * Gets the scattering angle.
   *
   * @return the angle in radians
   */
  double getAngle() {
    return Math.atan2(state[3], state[1]);// /Math.PI;   xx
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
