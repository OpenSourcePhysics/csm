/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.PlotFrame;

/**
 * QMWalkApp computes and displays the ground state of a quantum mechanical
 * system using the random walk Monte Carlo algorithm.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class QMWalkApp extends AbstractSimulation {
  PlotFrame phiFrame = new PlotFrame("x", "Phi_0", "Phi_0(x)");
  QMWalk qmwalk = new QMWalk();

  /**
   * Calculates the ground state wave function and displays the result.
   */
  public void initialize() {
    qmwalk.N = control.getInt("initial number of walkers");
    qmwalk.ds = control.getDouble("step size ds");
    qmwalk.numberOfBins = control.getInt("number of bins for wavefunction");
    qmwalk.initialize();
  }

  /**
   * One Monte Carlo step
   */
  public void doStep() {
    qmwalk.doMCS();
    phiFrame.clearData();
    phiFrame.append(0, qmwalk.xv, qmwalk.phi0);
    phiFrame.setMessage("E = "+decimalFormat.format(qmwalk.eAccum/qmwalk.mcs)+" N = "+qmwalk.N);
  }

  /**
   * Resets the calculation.
   */
  public void reset() {
    control.setValue("initial number of walkers", 50);
    control.setValue("step size ds", 0.1);
    control.setValue("number of bins for wavefunction", 100);
    enableStepsPerDisplay(true);
  }

  /**
   * Resets the accumulated data.
   */
  public void resetData() {
    qmwalk.resetData();
    phiFrame.clearData();
    phiFrame.repaint();
  }

  /**
   * Comand line entry point.
   * @param args
   */
  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new QMWalkApp());
    control.addButton("resetData", "Reset Data");
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
