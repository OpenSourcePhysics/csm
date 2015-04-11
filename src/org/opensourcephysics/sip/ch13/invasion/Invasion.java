/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch13.invasion;
import java.awt.Color;
import org.opensourcephysics.frames.*;

/**
 * Invasion implements the invasion percolation algorithm.
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 06/21/05
 */
public class Invasion {
  public int Lx, Ly;
  public double site[][];
  public int perimeterListX[], perimeterListY[];
  public int numberOfPerimeterSites;
  public boolean ok = true;
  public LatticeFrame lattice;

  public Invasion(LatticeFrame latticeFrame) {
    lattice = latticeFrame;
    lattice.setIndexedColor(0, Color.blue);
    lattice.setIndexedColor(1, Color.black);
  }

  public void initialize() {
    Lx = 2*Ly;
    site = new double[Lx][Ly];
    perimeterListX = new int[Lx*Ly];
    perimeterListY = new int[Lx*Ly];
    for(int y = 0;y<Ly;y++) {
      site[0][y] = 1; // occupy first column
      lattice.setValue(0, y, 1);
    }
    for(int y = 0;y<Ly;y++) {
      for(int x = 1;x<Lx;x++) {
        site[x][y] = Math.random();
        lattice.setValue(x, y, 0);
      }
    }
    numberOfPerimeterSites = 0;
    for(int y = 0;y<Ly;y++) { // second column is perimeter sites
      site[1][y] += 2;        // perimeter sites have site > 2;
      numberOfPerimeterSites++;
      insert(1, y);           // inserts site in perimeter list in order
    }
    ok = true;
  }

  public void insert(int x, int y) {
    int insertionLocation = binarySearch(x, y);
    for(int i = numberOfPerimeterSites-1;i>insertionLocation;i--) {
      perimeterListX[i] = perimeterListX[i-1];
      perimeterListY[i] = perimeterListY[i-1];
    }
    perimeterListX[insertionLocation] = x;
    perimeterListY[insertionLocation] = y;
  }

  public int binarySearch(int x, int y) {
    int firstLocation = 0;
    int lastLocation = numberOfPerimeterSites-2;
    if(lastLocation<0) {
      lastLocation = 0;
    }
    int middleLocation = (firstLocation+lastLocation)/2;
    // determine which half of list new number is in
    while(lastLocation-firstLocation>1) {
      int middleX = perimeterListX[middleLocation];
      int middleY = perimeterListY[middleLocation];
      if(site[x][y]>site[middleX][middleY]) {
        lastLocation = middleLocation;
      } else {
        firstLocation = middleLocation;
      }
      middleLocation = (firstLocation+lastLocation)/2;
    }
    return lastLocation;
  }

  public int linearSearch(int x, int y) { // goes in order looking for location to insert
    if(numberOfPerimeterSites==1) {
      return 0;
    }
	for(int i = 0;i<numberOfPerimeterSites-1;i++) {
        if(site[x][y]>site[perimeterListX[i]][perimeterListY[i]]) {
          return i;
        }
      }
    return numberOfPerimeterSites-1;
  }

  public void step() {
    if(ok) {
      int nx[] = {1, -1, 0, 0};
      int ny[] = {0, 0, 1, -1};
      int x = perimeterListX[numberOfPerimeterSites-1];
      int y = perimeterListY[numberOfPerimeterSites-1];
      if(x>Lx-3) {
        ok = false;                          // if cluster gets near the end, stop simulation
      }
      numberOfPerimeterSites--;
      site[x][y] -= 1;
      lattice.setValue(x, y, 1);
      for(int i = 0;i<4;i++) {               // finds new perimeter sites
        int perimeterX = x+nx[i];
        int perimeterY = (y+ny[i])%Ly;
        if(perimeterY==-1) {
          perimeterY = Ly-1;
        }
        if(site[perimeterX][perimeterY]<1) { // new perimeter site
          site[perimeterX][perimeterY] += 2;
          numberOfPerimeterSites++;
          insert(perimeterX, perimeterY);
        }
      }
    }
  }

  public void computeDistribution(PlotFrame data) {
    int numberOfBins = 20;
    int numberOccupied = 0;
    double occupied[] = new double[numberOfBins];
    double number[] = new double[numberOfBins];
    double binSize = 1.0/numberOfBins;
    int minX = Lx/3;
    int maxX = 2*minX;
    for(int x = minX;x<=maxX;x++) {
      for(int y = 0;y<Ly;y++) {
        int bin = (int) (numberOfBins*(site[x][y]%1));
        number[bin]++;
        if((site[x][y]>1)&&(site[x][y]<2)) {
          numberOccupied++;
          occupied[bin]++;
        }
      }
    }
    data.setMessage("Number occupied = "+numberOccupied);
    for(int bin = 0;bin<numberOfBins;bin++) {
      data.append(0, (bin+0.5)*binSize, occupied[bin]/number[bin]);
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
