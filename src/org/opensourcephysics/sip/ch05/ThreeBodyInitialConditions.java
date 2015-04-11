/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch05;

/**
 * ThreeBodyInitialConditions stores interesting initial conditions for the three-body problem.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class ThreeBodyInitialConditions {
  static final double sn = Math.sin(Math.PI/3);
  static final double half = Math.cos(Math.PI/3);
  static final double x1 = 0.97000436;
  static final double v1 = 0.93240737/2;
  static final double y1 = 0.24308753;
  static final double v2 = 0.86473146/2;
  static final double v = 0.8; // inital speed
  // state = {x1, vx1, y1, vy1, x2, vx2, y2, vy2, x3, vx3, y3, vy3, t}
  // EULER places masses on a line

  static final double[] EULER = {
    0, 0, 0, 0, 1, 0, 0, v, -1, 0, 0, -v, 0
  };
  // LAGRANGE places masses on an equilateral triangle

  static final double[] LAGRANGE = {
    1, 0, 0, v, -half, -v*sn, sn, -v*half, -half, v*sn, -sn, -v*half, 0
  };
  static final double[] MONTGOMERY = {
    x1, v1, -y1, v2, -x1, v1, y1, v2, 0, -2*v1, 0, -2*v2, 0
  };
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
