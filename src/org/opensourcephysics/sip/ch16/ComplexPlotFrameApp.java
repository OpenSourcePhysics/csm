/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.frames.ComplexPlotFrame;

/**
 * ComplexPlotFrameApp demonstrates the use of a ComplexFrame by displaying
 * a one-dimensional Gaussian wave function with a momentum boost.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class ComplexPlotFrameApp {
  public static void main(String[] args) {
    ComplexPlotFrame frame = new ComplexPlotFrame("x", "Psi(x)", "Complex function");
    int n = 128;
    double xmin = -Math.PI, xmax = Math.PI;
    double x = xmin, dx = (xmax-xmin)/n;
    double[] xdata = new double[n];
    double[] zdata = new double[2*n]; // real and imaginary values alternate
    int mode = 4;                     // test function is e^(-x*x/4)e^(i*mode*x) for x=[-pi,pi)
    for(int i = 0;i<n;i++) {
      double a = Math.exp(-x*x/4);
      zdata[2*i] = a*Math.cos(mode*x);
      zdata[2*i+1] = a*Math.sin(mode*x);
      xdata[i] = x;
      x += dx;
    }
    frame.append(xdata, zdata);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
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
