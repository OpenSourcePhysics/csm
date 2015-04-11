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
 * ThreeBody models the gravitational three-body problem.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class ThreeBody implements Drawable, ODE {
  int n = 3; // number of interacting bodies
  // state= {x1, vx1, y1, vy1, x2, vx2, y2, vy2, x3, vx3, y3, vy3, t}

  double[] state = new double[4*n+1];
  double[] force = new double[2*n], zeros = new double[2*n];
  ODESolver odeSolver = new RK45MultiStep(this);
  Mass mass1 = new Mass(), mass2 = new Mass(), mass3 = new Mass();

  /**
   * Draws the three bodies.
   *
   * @param panel
   * @param g
   */
  public void draw(DrawingPanel panel, Graphics g) {
    mass1.draw(panel, g);
    mass2.draw(panel, g);
    mass3.draw(panel, g);
  }

  /**
   * Steps the time using an ode solver.
   */
  public void doStep() {
    odeSolver.step();
    mass1.setXY(state[0], state[2]);
    mass2.setXY(state[4], state[6]);
    mass3.setXY(state[8], state[10]);
  }

  /**
   * Initialize the three-body problem with the given state.
   * @param initial state
   */
  void initialize(double[] initState) {
    System.arraycopy(initState, 0, state, 0, 13); // copies initstate to state
    mass1.clear();                                // clears data from old trail
    mass2.clear();
    mass3.clear();
    mass1.setXY(state[0], state[2]);
    mass2.setXY(state[4], state[6]);
    mass3.setXY(state[8], state[10]);
  }

  /**
   * Computes the forces using pairwise interactions assuming equal mass.
   */
  void computeForce(double[] state) {
    System.arraycopy(zeros, 0, force, 0, force.length); // sets force array elements to 0
    for(int i = 0;i<n;i++) {
      for(int j = i+1;j<n;j++) {
        double dx = state[4*i]-state[4*j];
        double dy = state[4*i+2]-state[4*j+2];
        double r2 = dx*dx+dy*dy;
        double r3 = r2*Math.sqrt(r2);
        double fx = dx/r3;
        double fy = dy/r3;
        force[2*i] -= fx;
        force[2*i+1] -= fy;
        force[2*j] += fx;
        force[2*j+1] += fy;
      }
    }
  }

  /**
   * Gets the rate using the given state.
   *
   * @param state double[]
   * @param rate double[]
   */
  public void getRate(double[] state, double[] rate) {
    computeForce(state); // force array alternates fx and fy
    for(int i = 0;i<n;i++) {
      int i4 = 4*i;
      rate[i4] = state[i4+1];    // x rate is vx
      rate[i4+1] = force[2*i];   // vx rate is fx
      rate[i4+2] = state[i4+3];  // y rate is vy
      rate[i4+3] = force[2*i+1]; // vy rate is fy
    }
    rate[state.length-1] = 1; // time rate is last
  }

  /**
   * Gets the state: x1, vx1, y1, vy1, x2, vx2, y2, vy2, x3, vx3, y3, vy3, t.
   *
   * @return the state
   */
  public double[] getState() {
    return state;
  }

  class Mass extends Circle {
    Trail trail = new Trail();
    // Draws the mass.

    public void draw(DrawingPanel panel, Graphics g) {
      trail.draw(panel, g);
      super.draw(panel, g);
    }

    // Clears trail
    void clear() {
      trail.clear();
    }

    // Sets postion and adds to trail
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
