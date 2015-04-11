/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch11;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.*;

/**
 * LagrangeInterpolatorApp implements a visualization of Lagrange interpolating polynomials.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class LagrangeInterpolatorApp extends AbstractCalculation {
  PlotFrame plotFrame = new PlotFrame("x", "f(x)", "Lagrange interpolation");

  /**
   * Resets the calculations's parameters and does the calculation.
   */
  public void reset() {
    control.setValue("f(x)", "sin(x)");
    control.setValue("minimum x", -2);
    control.setValue("maximum x", 2);
    control.setValue("n", 5);
    control.setValue("random y-error", 0);
    calculate();
  }

  /**
   * Calculates and displays the Lagrange interpolating polynomial.
   */
  public void calculate() {
    String fstring = control.getString("f(x)");
    double a = control.getDouble("minimum x");
    double b = control.getDouble("maximum x");
    double err = control.getDouble("random y-error");
    int n = control.getInt("n"); // number of intervals
    double[] xData = new double[n];
    double[] yData = new double[n];
    double dx = (n>1) ? (b-a)/(n-1) : 0;
    Function f;
    try {
      f = new ParsedFunction(fstring);
    } catch(ParserException ex) {
      control.println(ex.getMessage());
      return;
    }
    plotFrame.clearData();
    double[] range = Util.getRange(f, a, b, 100);
    plotFrame.setPreferredMinMax(a-(b-a)/4, b+(b-a)/4, range[0], range[1]);
    FunctionDrawer func = new FunctionDrawer(f);
    func.color = java.awt.Color.RED;
    plotFrame.addDrawable(func);
    double x = a;
    for(int i = 0;i<n;i++) {
      xData[i] = x;
      yData[i] = f.evaluate(x)*(1+err*(-0.5+Math.random()));
      plotFrame.append(0, xData[i], yData[i]);
      x += dx;
    }
    LagrangeInterpolator interpolator = new LagrangeInterpolator(xData, yData);
    plotFrame.addDrawable(new FunctionDrawer(interpolator));
    double[] coef = interpolator.getCoefficients();
    for(int i = 0;i<coef.length;i++) {
      control.println("c["+i+"]="+coef[i]);
    }
  }

  /**
   * Starts the Java applicaiton.
   * @param args String[] command line parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new LagrangeInterpolatorApp());
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
