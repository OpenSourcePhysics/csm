/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch07;

/**
 * Random Walk simulation in 1D
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class Walker {
  int xAccum[], xSquaredAccum[]; // accumulated data on displacement of walkers, index is time
  int N;        // maximum number of steps
  double p;     // probability of step to the right
  int position; // position of walker

  /**
   * Initializes walker array
   */
  public void initialize() {
    xAccum = new int[N+1];
    xSquaredAccum = new int[N+1];
  }

  /**
   *  Does random walk for one walker
   */
  public void step() {
    position = 0;
    for(int t = 0;t<N;t++) {
      if(Math.random()<p) {
        position++;
      } else {
        position--;
      }
      xAccum[t+1] += position; // determine displacement of walker after each step
      xSquaredAccum[t+1] += position*position;
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
