/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch13.cca;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * CCAApp models Cluster-Cluster Aggregation
 *
 * @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 06/21/05
 */
public class CCAApp extends AbstractSimulation {
  CCA model = new CCA();
  DisplayFrame displayFrame = new DisplayFrame("x", "y", "Cluster-Cluster Aggregation");
  PlotFrame plotFrame = new PlotFrame("ln r", "ln M(r)", "Mass Distribution");

  /**
   * initialize animation
   *
   */
  public void initialize() {
    displayFrame.addDrawable(model);
    model.L = control.getInt("L");
    displayFrame.setPreferredMinMax(0, model.L, 0, model.L);
    model.numberOfParticles = control.getInt("N");
    model.initialize();
  }

  /**
   * Does a simulation step.
   */
  public void doStep() {
    if(model.numberOfClusters>1) {
      model.step();
    }
    displayFrame.setMessage("Number of clusters = "+model.numberOfClusters);
  }

  /**
   * Computes the distribution.
   */
  public void distribution() {
    plotFrame.clearData();
    model.boxCount();
    for(int cell = 1;cell<model.L;cell++) {
      if(model.box[cell]>0) {
        // use logarithmic scale
        plotFrame.append(0, Math.log(cell), Math.log(model.box[cell]));
      }
    }
    plotFrame.render();
  }

  /**
   * Resets the simulation.
   *
   */
  public void reset() {
    control.setValue("L", 50);
    control.setValue("N", 500);
  }

  /**
   * start application
   * @param args
   */
  public static void main(String[] args) {
    // set up animation control structure using this class
    SimulationControl control = SimulationControl.createApp(new CCAApp());
    control.addButton("distribution", "Distribution");
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
