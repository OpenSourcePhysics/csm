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
 * PolynomialFitApp implements a visualization of a polynomial lest squares fit to a function.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class PolynomialFitApp extends AbstractCalculation {
  PlotFrame frame = new PlotFrame("x", "f(x)", "Polynomial Best Fit");

  /**
   * Constructs a PolynomialFitApp.
   */
  public PolynomialFitApp() {
    frame.setConnected(false);
  }

  /**
   * Resets the calculations's parameters and does the calculation.
   */
  public void reset() {
    control.setValue("f(x)", "20-10*x-4.9*x*x");
    control.setValue("sample start", -2);
    control.setValue("sample stop", 2);
    control.setValue("number of samples", 16);
    control.setValue("degree of polynomial", 3);
    control.setValue("random y-error", 0);
  }

  /**
   * Calculates and displays the best polynomial fit.
   */
  public void calculate() {
    String fstring = control.getString("f(x)");
    double a = control.getDouble("sample start");
    double b = control.getDouble("sample stop");
    int n = control.getInt("number of samples"); // number of samples
    int degree = control.getInt("degree of polynomial");
    double err = control.getDouble("random y-error");
    double dx = (n>1) ? (b-a)/(n-1) : 0;
    Function f;
    try {
      f = new ParsedFunction(fstring);
    } catch(ParserException ex) {
      control.println(ex.getMessage());
      return;
    }
    double[] range = Util.getRange(f, a, b, 100);
    frame.clearDrawables();
    frame.setPreferredMinMax(a-(b-a)/4, b+(b-a)/4, range[0], range[1]);
    FunctionDrawer func = new FunctionDrawer(f);
    func.color = java.awt.Color.RED;
    frame.addDrawable(func);
    double x = a;
    double[] xpts = new double[n], ypts = new double[n];
    for(int i = 0;i<n;i++) {
      xpts[i] = x;
      ypts[i] = f.evaluate(x)*(1+err*(-0.5+Math.random()));
      x += dx;
    }
    frame.append(0, xpts, ypts);
    Polynomial interpolator = new PolynomialLeastSquareFit(xpts, ypts, degree);
    frame.addDrawable(new FunctionDrawer(interpolator));
    control.println("p(x)="+interpolator);
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
    CalculationControl.createApp(new PolynomialFitApp());
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
