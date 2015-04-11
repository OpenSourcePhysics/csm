/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch13;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.LatticeFrame;
import java.awt.Color;

/**
 * DLAApp displays Diffusion Limited Aggregation (DLA) in a lattice frame.
 *
 * Add code to plot the mass distribution in the stopRunning method.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/21/05
 */
public class DLAApp extends AbstractSimulation {
  LatticeFrame latticeFrame = new LatticeFrame("DLA");
  byte s[][];                   // lattice on which cluster lives
  int xOccupied[], yOccupied[]; // location of occupied sites
  int L;                        // linear dimension of lattice
  int halfL;                    // L/2
  int ringSize;                 // ring size in which walkers can move
  int numberOfParticles;        // number of particles in cluster
  int startRadius;              // radius of cluster at which walkers are started
  int maxRadius;                // maximum radius walker can go to before a new walk is started

  /**
   * Initializes the DLA lattice.
   */
  public void initialize() {
    latticeFrame.setMessage(null);
    numberOfParticles = 1;
    L = control.getInt("lattice size");
    startRadius = 3;
    halfL = L/2;
    ringSize = L/10;
    maxRadius = startRadius+ringSize;
    s = new byte[L][L];
    s[halfL][halfL] = Byte.MAX_VALUE;
    latticeFrame.setAll(s);
  }

  /**
   * Resets the DLA lattice size.
   */
  public void reset() {
    latticeFrame.setIndexedColor(0, Color.BLACK);
    control.setValue("lattice size", 300);
    setStepsPerDisplay(100);
    enableStepsPerDisplay(true);
    initialize();
  }

  /**
   * Performs an action after the animation stops.
   */
  public void stopRunning() {
    control.println("Number of particles = "+numberOfParticles);
    // add code to compute the mass distribution here
  }

  /**
   * Does a simulation step by growing the DLA.
   *
   * Attempts no more than 100 walkers.
   */
  public void doStep() {
    int x = 0, y = 0;
    if(startRadius<halfL) {
      // find random initial position of new walker
      do {
        double theta = 2*Math.PI*Math.random();
        x = halfL+(int) (startRadius*Math.cos(theta));
        y = halfL+(int) (startRadius*Math.sin(theta));
      } while(walk(x, y)); // random walk, returns true if new walk is needed
    }
    if(startRadius>=halfL) { // stop the simulation
      control.calculationDone("Done");
      latticeFrame.setMessage("Done");
    }
    latticeFrame.setMessage("n = "+numberOfParticles);
  }

  /**
   * Walk until next to perimeter site.
   *
   * @param x,y initial walker location
   */
  public boolean walk(int x, int y) {
    do {
      double rSquared = (x-halfL)*(x-halfL)+(y-halfL)*(y-halfL);
      int r = 1+(int) Math.sqrt(rSquared);
      if(r>maxRadius) {
        return true;                      // start new walker
      }
      if((r<halfL)&&(s[x+1][y]+s[x-1][y]+s[x][y+1]+s[x][y-1]>0)) {
        numberOfParticles++;
        s[x][y] = 1;
        latticeFrame.setValue(x, y, Byte.MAX_VALUE);
        if(r>=startRadius) {
          startRadius = r+2;
        }
        maxRadius = startRadius+ringSize;
        return false;                     // walk is finished
      }
	switch((int) (4*Math.random())) { // select direction randomly
	case 0 :
	  x++;
	  break;
	case 1 :
	  x--;
	  break;
	case 2 :
	  y++;
	  break;
	case 3 :
	  y--;
	}
    } while(true);                        // end do loop
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new DLAApp());
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
