/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch13.cluster;

/**
 * Creates percolation cluster with probability p and computes mass distribution.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 06/21/05
 */
public class SingleCluster {
  public byte site[][];
  public int[] xs, ys, pxs, pys;
  public int L;
  public double p;          // site occupation probability
  int occupiedNumber;
  int perimeterNumber;
  int nx[] = {1, -1, 0, 0}; // relative change in x to nearest neighbors
  int ny[] = {0, 0, 1, -1}; // relative change in y to nearest neighbors
  double mass[];            // mass of ring, index is distance from center of mass

  public void initialize() {
    site = new byte[L+2][L+2]; // gives status of each site
    xs = new int[L*L];         // location of occupied sites
    ys = new int[L*L];
    pxs = new int[L*L];        // location of perimeter sites
    pys = new int[L*L];
    for(int i = 0;i<L+2;i++) {
      site[0][i] = (byte) -1; // don't occupy edge sites
      site[L+1][i] = (byte) -1;
      site[i][0] = (byte) -1;
      site[i][L+1] = (byte) -1;
    }
    xs[0] = 1+(L/2);
    ys[0] = xs[0];
    site[xs[0]][ys[0]] = (byte) 1; // occupy center site
    occupiedNumber = 1;
    for(int n = 0;n<4;n++) { // perimeter sites
      pxs[n] = xs[0]+nx[n];
      pys[n] = ys[0]+ny[n];
      site[pxs[n]][pys[n]] = (byte) 2;
    }
    perimeterNumber = 4;
  }

  public void step() {
    if(perimeterNumber>0) {
      int perimeter = (int) (Math.random()*perimeterNumber);
      int x = pxs[perimeter];
      int y = pys[perimeter];
      perimeterNumber--;
      pxs[perimeter] = pxs[perimeterNumber];
      pys[perimeter] = pys[perimeterNumber];
      if(Math.random()<p) {      // occupy site
        site[x][y] = (byte) 1;
        xs[occupiedNumber] = x;
        ys[occupiedNumber] = y;
        occupiedNumber++;
        for(int n = 0;n<4;n++) { // find new perimeter sites
          int px = x+nx[n];
          int py = y+ny[n];
          if(site[px][py]==(byte) 0) {
            pxs[perimeterNumber] = px;
            pys[perimeterNumber] = py;
            site[px][py] = (byte) 2;
            perimeterNumber++;
          }
        }
      } else {
        site[x][y] = (byte) -1;
      }
    }
  }

  public void massDistribution() {
    mass = new double[L];
    double xcm = 0;
    double ycm = 0;
    for(int n = 0;n<occupiedNumber;n++) {
      xcm += xs[n];
      ycm += ys[n];
    }
    xcm /= occupiedNumber;
    ycm /= occupiedNumber;
    for(int n = 0;n<occupiedNumber;n++) {
      double dx = xs[n]-xcm;
      double dy = ys[n]-ycm;
      int r = (int) Math.sqrt(dx*dx+dy*dy);
      if((r>1)&&(r<L)) {
        mass[r]++;
      }
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
