/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02; // location of file

/**
 * FirstFallingBallApp computes the time for a ball to fall 10 meters and displays the variables.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class FirstFallingBallApp { // beginning of class definition

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // beginning of method definition
    // braces { } used to group statements.
    // indent statements within a block so that they can be easily identified
    // following statements form the body of main method
    double y0 = 10;   // example of declaration and assignment statement
    double v0 = 0;    // initial velocity
    double t = 0;     // time
    double dt = 0.01; // time step
    double y = y0;
    double v = v0;
    double g = 9.8;   // gravitational field
    for(int n = 0;n<100;n++) { // beginning of loop, n++ equivalent to n = n + 1
      // repeat following three statements 100 times
      y = y+v*dt; // indent statements in loop for clarity
      v = v-g*dt; // use Euler algorithm
      t = t+dt;
    }             // end of for loop
    System.out.println("Results");
    System.out.println("final time = "+t);
    // display numerical result
    System.out.println("y = "+y+" v = "+v);
    // display analytic result
    double yAnalytic = y0+v0*t-0.5*g*t*t;
    double vAnalytic = v0-g*t;
    System.out.println("analytic y = "+yAnalytic+" v = "+vAnalytic);
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
