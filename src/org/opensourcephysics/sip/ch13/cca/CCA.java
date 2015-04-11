/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch13.cca;
import java.awt.Graphics;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.PBC;

/**
 * CCA provides simualtes cluster-cluster aggregation
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 06/21/05
 */
public class CCA implements Drawable {
  public int[][] site;               // lattice on which clusters move
  public int[] x, y;                 // location of particles
  public int[] firstParticle, nextParticle, lastParticle, mass;
  public int L;                      // linear lattice dimension
  public int numberOfParticles;      // number of particles in system
  public int numberOfClusters;       // number of clusters in system
  private int nnx[] = {1, 0, -1, 0}; // used to find neighbors of site
  private int nny[] = {0, 1, 0, -1};
  public int[] box;

  /**
   * initializes site lattice with single particle clusters
   * randomly placed on the lattice
   */
  public void initialize() {
    site = new int[L][L];
    for(int i = 0;i<L;i++) {
      for(int j = 0;j<L;j++) {
        site[i][j] = -1; // site not occupied
      }
    }
    x = new int[numberOfParticles];
    y = new int[numberOfParticles];
    firstParticle = new int[numberOfParticles];
    nextParticle = new int[numberOfParticles];
    lastParticle = new int[numberOfParticles];
    mass = new int[numberOfParticles+1];
    numberOfClusters = 0;
    for(int i = 0;i<numberOfParticles;i++) {
      do {
        x[i] = (int) (Math.random()*L);
        y[i] = (int) (Math.random()*L);
      } while(site[x[i]][y[i]]!=-1);
      site[x[i]][y[i]] = numberOfClusters;
      firstParticle[numberOfClusters] = i;
      mass[numberOfClusters] = 1;
      nextParticle[i] = -1; // no more particles in cluster
      lastParticle[numberOfClusters] = i;
      numberOfClusters++;
      checkNeighbors(x[i], y[i]);
    }
  }

  /**
   * Checks to see if there are two neighbors not in same cluster
   * @param x_i,y_i
   */
  public void checkNeighbors(int x_i, int y_i) {
    for(int j = 0;j<4;j++) {
      int px = PBC.position(x_i+nnx[j], L);
      int py = PBC.position(y_i+nny[j], L);
      if((site[px][py]!=-1)&&(site[px][py]!=site[x_i][y_i])) {
        merge(site[px][py], site[x_i][y_i]);
      }
    }
  }

  /**
   * Merges two clusters that are next to each other
   * @param largerCluster,smallerCluster
   */
  public void merge(int c1, int c2) {
    int largerClusterLabel, smallerClusterLabel;
    if(mass[c1]>mass[c2]) {
      largerClusterLabel = c1;
      smallerClusterLabel = c2;
    } else {
      largerClusterLabel = c2;
      smallerClusterLabel = c1;
    }
    // does merge by first changing links in linked list
    nextParticle[lastParticle[largerClusterLabel]] = firstParticle[smallerClusterLabel];
    lastParticle[largerClusterLabel] = lastParticle[smallerClusterLabel];
    mass[largerClusterLabel] += mass[smallerClusterLabel];
    int particle = firstParticle[smallerClusterLabel];
    do {
      site[x[particle]][y[particle]] = largerClusterLabel; // relabels sites of smaller cluster
      particle = nextParticle[particle];
    } while(particle!=-1);
    numberOfClusters--;
    // relabel last cluster to label of smaller cluster
    if(smallerClusterLabel!=numberOfClusters) {
      particle = firstParticle[numberOfClusters];
      do {
        site[x[particle]][y[particle]] = smallerClusterLabel; // relabels sites of last cluster
        particle = nextParticle[particle];
      } while(particle!=-1);
      firstParticle[smallerClusterLabel] = firstParticle[numberOfClusters];
      lastParticle[smallerClusterLabel] = lastParticle[numberOfClusters];
      mass[smallerClusterLabel] = mass[numberOfClusters];
    }
  }

  /**
   * Moves a cluster chosen at random in a random direction
   *
   */
  public void step() {
    int cluster = (int) (Math.random()*numberOfClusters);
    int direction = (int) (Math.random()*4);
    int dx = nnx[direction];
    int dy = nny[direction];
    int particle = firstParticle[cluster];
    do {
      site[x[particle]][y[particle]] = -1;
      x[particle] = PBC.position(x[particle]+dx, L);
      y[particle] = PBC.position(y[particle]+dy, L);
      particle = nextParticle[particle];
    } while(particle!=-1);
    particle = firstParticle[cluster];
    do {
      site[x[particle]][y[particle]] = cluster; // labels new sites occupied by cluster
      particle = nextParticle[particle];
    } while(particle!=-1);
    particle = firstParticle[cluster];
    do {
      checkNeighbors(x[particle], y[particle]); // checks for merger
      particle = nextParticle[particle];
    } while(particle!=-1);
  }

  public int occupiedSiteInCell(int cell, int i, int j) {
    for(int ic = 0;ic<cell;ic++) {
      for(int jc = 0;jc<cell;jc++) {
        if(site[i+ic][j+jc]>-1) {
          return 1;
        }
      }
    }
    return 0;
  }

  public void boxCount() {
    box = new int[L];
    int cell = 1;
    while(cell<L) {
      for(int i = 0;i<1+L-cell;i += cell) {
        for(int j = 0;j<1+L-cell;j += cell) {
          box[cell] += occupiedSiteInCell(cell, i, j);
        }
      }
      cell *= 2;
    }
  }

  /**
   * Draws clusters
   */
  public void draw(DrawingPanel panel, Graphics g) {
    if(site==null) {
      return;
    }
    int sizeX = Math.abs(panel.xToPix(1.0)-panel.xToPix(0));
    int sizeY = Math.abs(panel.yToPix(1.0)-panel.yToPix(0));
    for(int i = 0;i<numberOfParticles;i++) {
      int xpix = panel.xToPix(x[i])-sizeX;
      int ypix = panel.yToPix(y[i])-sizeY;
      g.fillRect(xpix+sizeX/2, ypix+sizeY/2, sizeX, sizeY);
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
