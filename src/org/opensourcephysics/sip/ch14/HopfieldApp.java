/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * HopfieldApp models a neural network using an array to store memories based on user input.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class HopfieldApp extends AbstractSimulation { // Hopfield model of a neural network
  LatticeFrame lattice;
  int N;        // total number of neurons
  double[][] w; // connection array (N by N elements)
  int numberOfStoredMemories;

  public HopfieldApp() {
    lattice = new LatticeFrame("Hopfield state");
    lattice.setToggleOnClick(true, -1, 1);
    lattice.setIndexedColor(-1, java.awt.Color.blue);
    lattice.setIndexedColor(0, java.awt.Color.blue);
    lattice.setIndexedColor(1, java.awt.Color.green);
    lattice.setSize(600, 120);
  }

  public void doStep() {
    int[] S = lattice.getAll();
    for(int counter = 0;counter<N;counter++) {
      int i = (int) (N*Math.random()); // chooses random neuron index
      double sum = 0;
      for(int j = 0;j<N;j++) {
        sum += w[i][j]*S[j];
      }
      S[i] = (sum>0) ? 1 : -1;
    }
    lattice.setAll(S);
  }

  public void initialize() {
    N = control.getInt("Lattice size");
    w = new double[N][N];
    lattice.resizeLattice(N, 1);
    for(int i = 0;i<N;i++) {
      lattice.setAtIndex(i, -1);
    }
    lattice.setMessage("# memories = "+(numberOfStoredMemories = 0));
  }

  public void reset() {
    control.setValue("Lattice size", 8);
    lattice.setMessage("# memories = "+(numberOfStoredMemories = 0));
  }

  public void addMemory() {
    int[] S = lattice.getAll();
    for(int i = 0;i<N;i++) {
      for(int j = i+1;j<N;j++) {
        w[i][j] += S[i]*S[j];
        w[j][i] += S[i]*S[j];
      }
    }
    lattice.setMessage("# memories = "+(++numberOfStoredMemories));
  }

  public void randomizeState() {
    for(int i = 0;i<N;i++) {
      lattice.setAtIndex(i, Math.random()<0.5 ? -1 : 1);
    }
    lattice.repaint();
  }

  public static void main(String args[]) {
    SimulationControl control = SimulationControl.createApp(new HopfieldApp());
    control.addButton("addMemory", "Remember");
    control.addButton("randomizeState", "Randomize");
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
