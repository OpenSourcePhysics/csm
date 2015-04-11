/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.HistogramFrame;

/**
 * Tests Metropolis algorithm for one particle and plots the velocity distribution.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class BoltzmannApp extends AbstractSimulation {
  double beta; // inverse temperature
  int mcs;
  int accepted;
  double velocity;
  HistogramFrame velocityDistribution = new HistogramFrame("v", "P(v)", "Velocity distribution");

  /**
   *   Gets parameters and initializes model
   */
  public void initialize() {
    velocityDistribution.clearData();
    beta = 1.0/control.getDouble("Temperature");
    velocity = control.getDouble("Initial velocity");
    accepted = 0;
    mcs = 0;
  }

  /**
   *   Does one Monte Carlo step and plots distributions
   */
  public void doStep() {
    double delta = control.getDouble("Maximum velocity change");
    mcs++;
    double ke = 0.5*velocity*velocity;
    double vTrial = velocity+delta*(2.0*Math.random()-1.0);
    double keTrial = 0.5*vTrial*vTrial;
    double dE = keTrial-ke;
    if((dE<0)||(Math.exp(-beta*dE)>Math.random())) {
      accepted++;
      ke = keTrial;
      velocity = vTrial;
    }
    velocityDistribution.append(velocity);
    control.clearMessages();
    control.println("mcs = "+mcs);
    control.println("acceptance probability = "+(double) (accepted)/mcs);
  }

  /**
   *  Resets to default values
   */
  public void reset() {
    control.setValue("Maximum velocity change", 10.0);
    control.setValue("Temperature", 10.0);
    control.setValue("Initial velocity", 0.0);
    enableStepsPerDisplay(true);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new BoltzmannApp());
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
