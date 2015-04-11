/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch19;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.DisplayFrame;
import org.opensourcephysics.numerics.*;

public class ConstraintsApp extends AbstractSimulation implements ODE {
  double dt, g, k, L0, m1, m2;                                                  // m1, mass of particle 1
  double[] state = new double[4];                                               // x1, v1, x2, v2
  ODESolver solver;
  Circle circle1 = new Circle(), circle2 = new Circle();
  Arrow arrow1 = new Arrow(-10, -10, 0, 0), arrow2 = new Arrow(-10, -10, 0, 0); // show force on each particle due to spring

  public ConstraintsApp() {
    Function surface = new Function() {
      public double evaluate(double x) {
        return y(x);
      }
    };
    DisplayFrame frame = new DisplayFrame("Two interacting constrained particles");
    frame.setPreferredMinMax(-1.6, 1.6, -1.6, 1.6);
    frame.addDrawable(new FunctionDrawer(surface));
    frame.addDrawable(circle1);
    frame.addDrawable(circle2);
    frame.addDrawable(arrow1);
    frame.addDrawable(arrow2);
  }

  public double[] getState() {
    return state;
  }

  // surface of constraint,  y(x) and its derivatives y', y''
  private double y(double x) {
    return x*x*x*x-2*x*x;
  }

  private double yp(double x) {
    return 4*x*x*x-4*x;
  }

  private double ypp(double x) {
    return 12*x*x-4;
  }

  public void getRate(double[] state, double[] rate) {
    // generalized coordinates
    double x1 = state[0];
    double vx1 = state[1];
    double x2 = state[2];
    double vx2 = state[3];
    double y1 = y(x1);
    double y2 = y(x2);
    double yp1 = yp(x1);
    double yp2 = yp(x2);   // first derivative
    double ypp1 = ypp(x1);
    double ypp2 = ypp(x2); // second derivative
    // displacements
    double Lx = x2-x1, Ly = y2-y1;
    double L = Math.sqrt(Lx*Lx+Ly*Ly); // length of spring
    // forces. L0 is equilibrium length of spring
    double keff = k*(1-L0/L); // effective spring constant
    double fx1 = keff*Lx;     // net applied force on particle 1 in x-direction
    double fy1 = keff*Ly-g*m1;
    double fx2 = -keff*Lx;    // net applied force on particle 2
    double fy2 = -keff*Ly-g*m2;
    // elements of diagonal matrix (J^T M J)^-1:
    double a11 = 1/(m1*(1+yp1*yp1));
    double a22 = 1/(m2*(1+yp2*yp2)); // other matrix elemts are zero
    // elements of vector J^T F^(a):
    double b1 = fx1+yp1*fy1;
    double b2 = fx2+yp2*fy2;
    // elements of vector J^T M J du/dt:
    double c1 = m1*yp1*ypp1*vx1*vx1;
    double c2 = m2*yp2*ypp2*vx2*vx2;
    rate[0] = vx1;
    rate[1] = a11*(b1-c1);
    rate[2] = vx2;
    rate[3] = a22*(b2-c2);
    circle1.setXY(x1, y1);
    circle2.setXY(x2, y2);
    arrow1.setXY(x1, y1);
    arrow2.setXY(x2, y2);
    arrow1.setXlength(keff*Lx/100);
    arrow1.setYlength(keff*Ly/100);
    arrow2.setXlength(-keff*Lx/100);
    arrow2.setYlength(-keff*Ly/100);
  }

  public void initialize() {
    state = new double[] { /* x1 */
      -1.5, /* v1 */ 0, /* x2 */ -1.4, /* v2 */ 0
    };
    circle1.setXY(state[0], y(state[0]));
    circle2.setXY(state[2], y(state[2]));
    solver = new RK4(this);
  }

  public void doStep() {
    solver.step();
  }

  public void startRunning() {
    solver.setStepSize(control.getDouble("dt"));
    g = control.getDouble("acceleration due to gravity g");
    k = control.getDouble("spring constant");
    L0 = control.getDouble("equilibrium spring length");
    m1 = control.getDouble("mass 1");
    m2 = control.getDouble("mass 2");
  }

  public void reset() {
    control.setAdjustableValue("dt", 0.01);
    control.setAdjustableValue("acceleration due to gravity g", 9.8);
    control.setAdjustableValue("spring constant", 1000);
    control.setAdjustableValue("equilibrium spring length", 1.0);
    control.setAdjustableValue("mass 1", 1);
    control.setAdjustableValue("mass 2", 1);
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new ConstraintsApp());
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
