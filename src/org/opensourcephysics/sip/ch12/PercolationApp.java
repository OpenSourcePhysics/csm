/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch12;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.LatticeFrame;

/**
 * PercolationApp displays site percolation clusters.
 *
 * Click on a cluster to select and change its color.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould, Kipton Barros
 * @version 1.1  revised 05/18/05
 */
public class PercolationApp extends AbstractCalculation implements InteractiveMouseHandler {
  LatticeFrame lattice = new LatticeFrame("Percolation");
  Random random = new Random();
  int L;
  int clusterNumber; // used to color clusters

  /**
   * Creates the PercolationApp and sets the colors for lattice.
   */
  public PercolationApp() {
    lattice.setInteractiveMouseHandler(this);
    // unoccupied sites are black and have value -2
    lattice.setIndexedColor(-2, Color.BLACK);
    // occupied sites that are not part of an identified cluster are red and have value -1
    lattice.setIndexedColor(-1, Color.RED);
    // following colors used to identify clusters when user clicks on an occupied site
    lattice.setIndexedColor(0, Color.GREEN);
    lattice.setIndexedColor(1, Color.YELLOW);
    lattice.setIndexedColor(2, Color.BLUE);
    lattice.setIndexedColor(3, Color.CYAN);
    lattice.setIndexedColor(4, Color.MAGENTA);
    lattice.setIndexedColor(5, Color.PINK);
    lattice.setIndexedColor(6, Color.LIGHT_GRAY); // recycles cluster colors starting from green
  }

  /**
   * @param panel InteractivePanel
   * @param evt MouseEvent
   */
  // uses mouse click events to identify an occupied site and identify its cluster
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt) {
    panel.handleMouseAction(panel, evt);
    if(panel.getMouseAction()==InteractivePanel.MOUSE_PRESSED) {
      int site = lattice.indexFromPoint(panel.getMouseX(), panel.getMouseY());
      // test if a valid site was clicked (index non-negative),
      // and if site is occupied, but not yet cluster colored (value -1).
      if(site>=0&&lattice.getAtIndex(site)==-1) {
        colorCluster(site);                  // color cluster to which site belongs
        clusterNumber = (clusterNumber+1)%7; // cycle through 7 cluster colors
        lattice.repaint();                   // display lattice with colored cluster
      }
    }
  }

  /**
  * Occupy all sites with probability p
  */
  // Occupies all sites with probability p
  public void calculate() {
    L = control.getInt("Lattice size");
    lattice.resizeLattice(L, L); // resize lattice
    // same seed will generate same set of random numbers
    random.setSeed(control.getInt("Random seed"));
    double p = control.getDouble("Site occupation probability");
    // occupy lattice sites with probability p
    for(int i = 0;i<L*L;i++) {
      lattice.setAtIndex(i, random.nextDouble()<p ? -1 : -2);
    }
    // first cluster will have color green (value 0)
    clusterNumber = 0;
  }

  // returns jth neighbor of site s, where j can be 0 (left), 1 (right),
  // 2 (down), or 3 (above).  If no neighbor exists because of boundary,
  // return -1. Change this method for periodic boundary conditions.
  int getNeighbor(int s, int j) {
    switch(j) {
    case 0 :                           // left
      if(s%L==0) {
        return -1;
      }
			return s-1;
    case 1 :                           // right
      if(s%L==L-1) {
        return -1;
      }
			return s+1;
    case 2 :                           // down
      if(s/L==0) {
        return -1;
      }
			return s-L;
    case 3 :                           // above
      if(s/L==L-1) {
        return -1;
      }
			return s+L;
    default :
      return -1;
    }
  }

  void colorCluster(int initialSite) { // color all sites in cluster
    int[] sitesToTest = new int[L*L]; // cluster sites whose neighbors have not yet been examined
    int numSitesToTest = 0; // number of sites in sitesToTest[]
    lattice.setAtIndex(initialSite, clusterNumber); // color initialSite according to clusterNumber
    sitesToTest[numSitesToTest++] = initialSite; // add initialSite to sitesToTest[]
    while(numSitesToTest>0) {                   // grow cluster until numSitesToTest = 0
      int site = sitesToTest[--numSitesToTest]; // get next site to test and remove it from list
      for(int j = 0;j<4;j++) {                             // visit four possible neighbors
        int neighborSite = getNeighbor(site, j);
        // test if neighborSite is occupied, and not yet added to cluster
        if(neighborSite>=0&&lattice.getAtIndex(neighborSite)==-1) {
          lattice.setAtIndex(neighborSite, clusterNumber); // color neighborSite according to clusterNumber
          sitesToTest[numSitesToTest++] = neighborSite; // add neighborSite to sitesToTest[]
        }
      }
    }
  }

  public void reset() {
    control.setValue("Lattice size", 32);
    control.setValue("Site occupation probability", 0.5927);
    control.setValue("Random seed", 100);
    calculate();
  }

  public static void main(String args[]) {
    CalculationControl.createApp(new PercolationApp());
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
