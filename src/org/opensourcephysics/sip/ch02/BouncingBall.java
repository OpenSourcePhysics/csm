/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;
import org.opensourcephysics.display.Circle;

/**
 * BouncingBall models a falling ball as it bounces off of a floor or a wall.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0 05/07/05
 */
public class BouncingBall extends Circle { // Circle is a class that can draw itself
  final static double g = 9.8; // constant
  final static double WALL = 10;
  private double x, y, vx, vy; // initial position and velocity

  /**
   * Constructs a BouncingBall with the given initial conditions.
   *
   * @param x double
   * @param vx double
   * @param y double
   * @param vy double
   */
  public BouncingBall(double x, double vx, double y, double vy) { // constructor
    this.x = x;   // sets instance value equal to passed value
    this.vx = vx; // sets instance value equal to passed value
    this.y = y;
    this.vy = vy;
    setXY(x, y); // sets the position using setXY in Circle superclass
  }

  /**
   * Steps (advances) the time.
   * @param dt double
   */
  public void step(double dt) {
    x = x+vx*dt; // Euler algorithm for numerical solution
    y = y+vy*dt;
    vy = vy-g*dt;
    if(x>WALL) {
      vx = -Math.abs(vx); // bounce off right wall
    } else if(x<-WALL) {
      vx = Math.abs(vx);  // bounce off left wall
    }
    if(y<0) {
      vy = Math.abs(vy); // bounce off floor
    }
    setXY(x, y);
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
