/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch06;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.numerics.*;

/**
 * FixedPointApp computes fixed points of the logistic map using the bisection root finding method.
 * This class does not use recursion.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class FixedPointApp extends AbstractCalculation {

  /**
   * Resets the calculation's default values.
   */
  public void reset() {
    control.setValue("r", 0.8);             // control parameter r
    control.setValue("period", 2);          // period
    control.setValue("epsilon", 0.0000001); // desired precision
    control.setValue("xleft", 0.01);        // guess for xleft
    control.setValue("xright", 0.99);       // guess for xright
  }

  /**
   * Calculates the value of x for the trajectory that has the desired period.
   */
  public void calculate() {
    double r = control.getDouble("r");
    int period = control.getInt("period");
    double epsilon = control.getDouble("epsilon");
    double xleft = control.getDouble("xleft");
    double xright = control.getDouble("xright");
    Function f = new LogisticIteration(r, period);
    double x = Root.bisection(f, xleft, xright, epsilon);
    if(Double.isNaN(x)) {
      control.println("range does not enclose a root");
    } else {
      control.println("explicit search for period "+period+" behavior");
      control.println(0+"\t"+x);         // result
      for(int i = 1;i<=2*period+1;i++) { // show the iteration
        x = 4*r*x*(1-x);
        control.println(i+"\t"+x);
      }
    }
  }

  /**
   * LogisticIteration iterates the logistic function and compares the result to the starting value.
   *
   * This inner class is used with a root finding algorithm.
   */
  class LogisticIteration implements Function {
    double r;
    int iterations;

    /**
     * Constructs the LogisticIterate function with the given values.
     * @param _r double
     * @param _iterations int
     */
    LogisticIteration(double _r, int _iterations) {
      r = _r;
      iterations = _iterations;
    }

    /**
     * Evaluates the function by iterating the logistic function.
     * @param x double
     * @return double
     */
    public double evaluate(double x) {
      double y = x;
      for(int i = 0;i<iterations;i++) {
        y = 4*r*y*(1-y);
      }
      return y-x;
    }
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new FixedPointApp());
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
