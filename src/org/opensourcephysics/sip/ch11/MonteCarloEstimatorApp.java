/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch11;
import java.util.Random;
import org.opensourcephysics.controls.*;

/**
 *  MonteCarloEstimatorApp
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class MonteCarloEstimatorApp extends AbstractSimulation {
  Random rnd = new Random();
  int nSampled; // number of points already sampled
  int nTotal;   // total number of samples
  long seed;
  double a, b;  // interval limits
  double ymax;
  int hits = 0;

  public void reset() {
    control.setValue("lower limit a", 0);
    control.setValue("upper limit b", 1.0);
    control.setValue("upper limit on y", 1.0);
    control.setValue("seed", 137933);
  }

  public double evaluate(double x) {
    return Math.sqrt(1-x*x);
  }

  public void initialize() {
    a = control.getDouble("lower limit a");
    b = control.getDouble("upper limit b");
    ymax = control.getDouble("upper limit on y");
    nSampled = 0;
    nTotal = 2;
    seed = control.getInt("seed");
    hits = 0;
    rnd.setSeed(seed);
  }

  public void doStep() {
    // nextDouble returns random double between 0 (inclusive) and 1 (exclusive)
    for(int i = nSampled;i<nTotal;i++) {
      double x = a+rnd.nextDouble()*(b-a);
      double y = rnd.nextDouble()*ymax;
      if(y<=evaluate(x)) {
        hits++;
      }
    }
    control.println("# of samples = "+nTotal+" estimated area = "+(hits*(b-a)*ymax)/nTotal);
    nSampled = nTotal; // number of points sample so far
    nTotal *= 2;       // increase number of samples by a factor of 2 for next step
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new MonteCarloEstimatorApp());
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
