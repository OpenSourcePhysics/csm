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
 * PolynomialApp test the Polynomial class.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class PolynomialApp extends AbstractCalculation {
  PlotFrame plotFrame = new PlotFrame("x", "f(x)", "Polynomial visualization");
  double xmin, xmax;
  Polynomial p;

  /**
   * Resets the default polynomial.
   */
  public void reset() {
    control.setValue("coefficients", "-2,0,1");
    control.setValue("minimum x", -10);
    control.setValue("maximum x", 10);
  }

  /**
   * Calculates and displays the polynomial.
   */
  public void calculate() {
    xmin = control.getDouble("minimum x");
    xmax = control.getDouble("maximum x");
    String[] coefficients = control.getString("coefficients").split(",");
    p = new Polynomial(coefficients);
    plotAndCalculateRoots();
  }

  void plotAndCalculateRoots() {
    plotFrame.clearDrawables();
    plotFrame.addDrawable(new FunctionDrawer(p));
    double[] range = Util.getRange(p, xmin, xmax, 100); // finds ymin and ymax within (xmin, xmax) domain.
    plotFrame.setPreferredMinMax(xmin, xmax, range[0], range[1]);
    plotFrame.repaint();
    double[] roots = p.rootsReal();
    control.clearMessages();
    control.println("polynomial = "+p);
    for(int i = 0, n = roots.length;i<n;i++) {
      control.println("root = "+roots[i]); // print each root
    }
  }

  public void derivative() {
    p = p.derivative();
    plotAndCalculateRoots();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    CalculationControl control = CalculationControl.createApp(new PolynomialApp());
    control.addButton("derivative", "Derivative", "The derivative of the polynomial.");
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
