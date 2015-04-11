/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch07;

/**
 * Light ray in media with different index of refraction
 *
 *  @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 *  @version 1.0  revised 04/21/05
 */
public class Fermat {
  double y[];      // y coordinate of light ray, index is x coordinate
  double v[];      // light speed of ray for medium starting at index value
  int N;           // number of media
  double dn;       // change in index of refraction from one region to the next
  double dy = 0.1; // maximum change in y position
  int steps;

  /**
   * Initializes  arrays
   */
  public void initialize() {
    y = new double[N+1];
    v = new double[N];
    double indexOfRefraction = 1.0;
    for(int i = 0;i<=N;i++) {
      y[i] = i; // initial path is a straight line
    }
    for(int i = 0;i<N;i++) {
      v[i] = 1.0/indexOfRefraction;
      indexOfRefraction += dn;
    }
    steps = 0;
  }

  /**
   *  Random change in path
   */
  public void step() {
    int i = 1+(int) (Math.random()*(N-1));
    double yTrial = y[i]+2.0*dy*(Math.random()-0.5);
    double previousTime = Math.sqrt(Math.pow(y[i-1]-y[i], 2)+1)/v[i-1]; // left medium
    previousTime += Math.sqrt(Math.pow(y[i+1]-y[i], 2)+1)/v[i]; // right medium
    double trialTime = Math.sqrt(Math.pow(y[i-1]-yTrial, 2)+1)/v[i-1]; // left medium
    trialTime += Math.sqrt(Math.pow(y[i+1]-yTrial, 2)+1)/v[i]; // right medium
    if(trialTime<previousTime) {
      y[i] = yTrial;
    }
    steps++;
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
