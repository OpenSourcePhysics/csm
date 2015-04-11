/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;

/**
 * FallingParticleApp computes the time for a particle to fall 10 meters and displays the variables.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  05/07/05
 */
public class FallingParticleApp { // beginning of class definition

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // beginning of method definition
    Particle ball = new FallingParticle(10, 0); // declaration and instantiation
    ball.t = 0;
    ball.dt = 0.01;
    while(ball.y>0) {
      ball.step();
    }
    System.out.println("Results");
    System.out.println("final time = "+ball.t);
    System.out.println("y = "+ball.y+" v = "+ball.v);            // numerical result
    System.out.println("y analytic = "+ball.analyticPosition()); // analytic result
  } // end of method definition
} // end of class definition

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
