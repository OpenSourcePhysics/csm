/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.networks;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * NetworksApp simulates and displays a network model such as PreferentialAttachment.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class NetworksApp extends AbstractSimulation {
  PreferentialAttachment network = new PreferentialAttachment();
  PlotFrame plot = new PlotFrame("ln s", "ln N(s)", "Degree Distribution");
  DisplayFrame display = new DisplayFrame("", "", "Network");

  public NetworksApp() {
    display.addDrawable(network);
    display.setPreferredMinMax(0, 100, 0, 100);
  }

  public void initialize() {
    network.N = control.getInt("Number of nodes");
    network.m = control.getInt("links per node");
    network.drawPositions = control.getBoolean("draw?");
    network.initialize();
  }

  public void reset() {
    control.setValue("Number of nodes", 100);
    control.setValue("links per node", 2);
    control.setAdjustableValue("draw?", true);
  }

  public void startRunning() {
    network.drawPositions = control.getBoolean("draw?");
  }

  public void doStep() {
    network.step();
    display.setMessage(network.numberOfCompletedNetworks+" completed networks "+network.n+" nodes in new network");
  }

  public void stop() {
    network.degreeDistribution(plot);
    plot.render();
  }

  public static void main(String args[]) {
    SimulationControl.createApp(new NetworksApp());
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
