/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.frames.PlotFrame;
import org.opensourcephysics.numerics.Function;

/**
 * EigenstateApp tests the Eigenstate class by calculating simple harmonic oscillator eigenfunctions.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class EigenstateApp {

  /**
   * Starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    PlotFrame drawingFrame = new PlotFrame("x", "|phi|", "eigenstate");
    int numberOfPoints = 300;
    double xmin = -5, xmax = +5;
    Eigenstate eigenstate = new Eigenstate(new Potential(), numberOfPoints, xmin, xmax);
    int n = 3; // quantum number
    double[] phi = eigenstate.getEigenstate(n);
    double[] x = eigenstate.getXCoordinates();
    if(eigenstate.getErrorCode()==Eigenstate.NO_ERROR) {
      drawingFrame.setMessage("energy = "+eigenstate.energy);
    } else {
      drawingFrame.setMessage("eigenvalue did not converge");
    }
    drawingFrame.append(0, x, phi);
    drawingFrame.setVisible(true);
    drawingFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
  }
}

/**
 * Simple harmonic oscillator potential.
 */
class Potential implements Function {
  public double evaluate(double x) {
    return(x*x)/2;
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
