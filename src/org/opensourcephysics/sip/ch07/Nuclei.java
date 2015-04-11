/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch07;

/**
 * Nuclei simulates decay of unstable nuclei
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class Nuclei {
  int n[];  // accumulated data on number of unstable nuclei, index is time
  int tmax; // maximum time to record data
  int n0;   // initial number of unstable nuclei
  double p; // decay probability

  /**
   * Initializes unstable nuclei array
   */
  public void initialize() {
    n = new int[tmax+1];
  }

  /**
   *  Nuclei decay
   */
  public void step() {
    n[0] += n0;
    int nUnstable = n0;
    for(int t = 0;t<tmax;t++) {
      for(int i = 0;i<nUnstable;i++) {
        if(Math.random()<p) {
          nUnstable--;
        }
      }
      n[t+1] += nUnstable;
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
