/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import java.awt.Graphics;
import org.opensourcephysics.display.*;

/**
 * Oscillators models the analytic soution of a chain of oscillators
 * with fixed end points.
 *
 * Students should implement the ODE interface to
 * complete the exercise in "An Introduction to Computer Simulation Methods."
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Oscillators implements Drawable {
  OscillatorsMode normalMode;
  Circle circle = new Circle();
  double[] x; // drawing positions
  double[] u; // displacement
  double time = 0;

  /**
   * Constructs a chain of coupled oscillators in the given mode and number of oscillators.
   *
   * @param mode int
   * @param N int
   */
  public Oscillators(int mode, int N) {
    u = new double[N+2]; // includes the two ends of the chain
    x = new double[N+2]; // includes the two ends of the chain
    normalMode = new OscillatorsMode(mode, N);
    double xi = 0;
    for(int i = 0;i<N+2;i++) {
      x[i] = xi;
      u[i] = normalMode.evaluate(xi); // initial displacement
      xi++;                           // increment x[i] by lattice spacing of one
    }
  }

  /**
   * Steps the time using the given time step.
   *
   * @param dt
   */
  public void step(double dt) {
    time += dt;
  }

  /**
   * Draws the oscillators
   *
   * @param drawingPanel
   * @param g
   */
  public void draw(DrawingPanel drawingPanel, Graphics g) {
    normalMode.draw(drawingPanel, g); // draw initial condition
    double phase = Math.cos(time*normalMode.omega);
    for(int i = 0, n = x.length;i<n;i++) {
      circle.setXY(x[i], u[i]*phase);
      circle.draw(drawingPanel, g);
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
