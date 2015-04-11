/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.networks;
import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;

/**
 * PreferentialAttachmentModel implements the preferential attachment model for networks.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class PreferentialAttachment implements Drawable {
  int[] node, linkFrom, degree;
  double[] x, y;                // positions of nodes, only meaningful for display purposes
  int N;                        // maximum number of nodes
  int m = 2;                    // number of attempted links per node
  int linkNumber = 0;           // twice current number of links
  int n = 0;                    // current number of nodes
  boolean drawPositions = true; // only draw network if true
  int numberOfCompletedNetworks = 0;

  /**
   * Initializes arrays and start first network
   */
  public void initialize() {
    degree = new int[N]; // degree distribution to be averaged over many networks
    numberOfCompletedNetworks = 0; // will be drawing many networks
    startNetwork();
  }

  /**
   * Adds one link to network
   */
  public void addLink(int i, int j, int s) {
    linkFrom[i*m+s] = j;
    node[i]++;
    node[j]++;
    linkNumber += 2; // twice current number of links
  }

  /**
   * Initializes new network
   */
  public void startNetwork() {
    n = 0;
    linkFrom = new int[m*N];
    node = new int[N];
    x = new double[N];
    y = new double[N];
    linkNumber = 0;
    for(int i = 0;i<=m;i++) {
      n++;
      setPosition(i);
    }
    for(int i = 1;i<m+1;i++) {
      for(int j = 0;j<i;j++) {
        addLink(i, j, j);
      }
    }
  }

  public void setPosition(int i) {
    double r2min = 1000./N;
    boolean ok = true; // used to insure two nodes are not drawn too close to each other
    do {
      ok = true;
      x[i] = Math.random()*100;
      y[i] = Math.random()*100;
      int j = 0;
      while(j<i&&ok) {
        double dx = x[i]-x[j];
        double dy = y[i]-y[j];
        double r2 = dx*dx+dy*dy;
        if(r2<r2min) {
          ok = false;
        }
        j++;
      }
    } while(!ok);
  }

  public int findNode(int i, int s) {
    boolean ok = true;
    int j = 0;
    do {
      ok = true;
      int k = (int) (1+Math.random()*linkNumber);
      j = -1;
      int sum = 0;
      do {
        j++;
        sum += node[j];
      } while(k>sum);
      for(int r = 0;r<s;r++) {
        if(linkFrom[i*m+r]==j) {
          ok = false;
        }
      }
    } while(!ok);
    return j;
  }

  public void addNode(int i) {
    n++;
    if(drawPositions) {
      setPosition(i);
    }
    for(int s = 0;s<m;s++) {
      addLink(i, findNode(i, s), s);
    }
  }

  public void step() {
    if(n<N) {
      addNode(n);
    } else {
      numberOfCompletedNetworks++;
      for(int i = 0;i<n;i++) { // accumulate data for degree distribution
        degree[node[i]]++;
      }
      startNetwork();          // start another network
    }
  }

  public void degreeDistribution(PlotFrame plot) {
    plot.clearData();
    for(int i = 1;i<N;i++) {
      if(degree[i]>0) {
        plot.append(0, Math.log(i), Math.log(degree[i]*1.0/(N*numberOfCompletedNetworks)));
      }
    }
  }

  public void draw(DrawingPanel panel, Graphics g) {
    if(node!=null&&drawPositions) {
      int pxRadius = Math.abs(panel.xToPix(1.0)-panel.xToPix(0));
      int pyRadius = Math.abs(panel.yToPix(1.0)-panel.yToPix(0));
      g.setColor(Color.green);
      for(int i = 0;i<n;i++) {
        int xpix = panel.xToPix(x[i]);
        int ypix = panel.yToPix(y[i]);
        for(int s = 0;s<m;s++) {
          int j = linkFrom[i*m+s];
          int xpixj = panel.xToPix(x[j]);
          int ypixj = panel.yToPix(y[j]);
          g.drawLine(xpix, ypix, xpixj, ypixj);         // draw link
        }
      }
      g.setColor(Color.red);
      for(int i = 0;i<n;i++) {
        int xpix = panel.xToPix(x[i])-pxRadius;
        int ypix = panel.yToPix(y[i])-pyRadius;
        g.fillOval(xpix, ypix, 2*pxRadius, 2*pyRadius); // draw node
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
