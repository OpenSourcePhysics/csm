/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import java.awt.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

/**
 * OscillatorsMode models a chain of oscillators in a single mode.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class OscillatorsMode implements Drawable, Function {
  static final double OMEGA_SQUARED = 1; // equals k/m
  FunctionDrawer functionDrawer;         // draws the initial condition
  double omega;                          // oscillation frequency of mode
  double wavenumber;                     // wavenumber = 2*pi/wavelength
  double amplitude;

  /**
   * Constructs OscillatorsMode with the given mode and number of particles.
   *
   * The particle separation is one in this model.
   *
   * @param mode int
   * @param N int
   */
  OscillatorsMode(int mode, int N) {
    amplitude = Math.sqrt(2.0/(N+1));
    omega = 2*Math.sqrt(OMEGA_SQUARED)*Math.abs(Math.sin(mode*Math.PI/N/2));
    wavenumber = Math.PI*mode/(N+1);
    functionDrawer = new FunctionDrawer(this);
    functionDrawer.initialize(0, N+1, 300, false); // draws the initial displacement
    functionDrawer.color = Color.LIGHT_GRAY;
  }

  /**
   * Evaluates the displacement for an oscillator at postion x
   *
   * @param x postion along chain
   *
   * @return the displacement
   */
  public double evaluate(double x) {
    return amplitude*Math.sin(x*wavenumber);
  }

  /**
   * Draws the normal mode's initial condtion
   *
   * @param panel DrawingPanel
   * @param g Graphics
   */
  public void draw(DrawingPanel panel, Graphics g) {
    functionDrawer.draw(panel, g);
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
