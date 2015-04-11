/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;

/**
 * BoxEigenstate defines the eigenfuntions and eigenvalues for a quantum mechanical particle in a box.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class BoxEigenstate {
  static double a = 1; // length of box

  private BoxEigenstate() {
    // prohibit instantiation because all methods are static
  }

  static double[] getEigenstate(int n, int numberOfPoints) {
    double[] phi = new double[numberOfPoints];
    n++; // quantum number
    double norm = Math.sqrt(2/a);
    for(int i = 0;i<numberOfPoints;i++) {
      phi[i] = norm*Math.sin((n*Math.PI*i)/(numberOfPoints-1));
    }
    return phi;
  }

  static double getEigenvalue(int n) {
    n++;
    return(n*n*Math.PI*Math.PI)/2/a/a; // hbar = 1, mass = 1
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
