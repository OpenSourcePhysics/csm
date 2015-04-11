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
 *  MonteCarloSampleMean
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class MonteCarloSampleMean extends AbstractCalculation {
  Random rnd = new Random();
  int n;       // number of trials
  long seed;
  double a, b; // interval limits
  int nmax;
  double sum;

  public void reset() {
    control.setValue("lower limit a", 0);
    control.setValue("upper limit b", 1.0);
    control.setValue("seed", 1379);
    control.setValue("n", 10000);
  }

  public double evaluate(double x) {
    return Math.sqrt(1-x*x);
  }

  public void calculate() {
    a = control.getDouble("lower limit a");
    b = control.getDouble("upper limit b");
    nmax = control.getInt("n");
    nmax = nmax-1;
    n = 0;
    seed = control.getInt("seed");
    sum = 0;
    rnd.setSeed(seed);
    while(n<=99999) {
      double x = rnd.nextDouble()*(b-a);
      sum = sum+evaluate(x);
      n++;
    }
    control.println("n = "+n+" estimated area = "+4.0*(b-a)*sum/n);
  }

  public static void main(String[] args) {
    CalculationControl.createApp(new MonteCarloSampleMean());
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
