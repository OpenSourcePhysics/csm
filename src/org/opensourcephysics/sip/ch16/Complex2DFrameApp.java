/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.frames.Complex2DFrame;

/**
 * Complex2DFrameApp demonstrates the use of a Complex2DFrame by displaying
 * a two-dimensional Gaussian wave function with a momentum boost.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class Complex2DFrameApp {
  public static void main(String[] args) {
    Complex2DFrame frame = new Complex2DFrame("x", "y", "Complex field");
    frame.setPreferredMinMax(-1.5, 1.5, -1.5, 1.5);
    double[][][] field = new double[2][32][32]; // components of field
    frame.setAll(field);
    for(int i = 0, nx = field[0].length;i<nx;i++) {
      double x = frame.indexToX(i);
      for(int j = 0, ny = field[0][0].length;j<ny;j++) {
        double y = frame.indexToY(j);
        double a = Math.exp(-4*(x*x+y*y));
        field[0][i][j] = a*Math.cos(5*x); // real component
        field[1][i][j] = a*Math.sin(5*x); // complex component
      }
    }
    frame.setAll(field);
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
