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
 *  NumericalIntegrationApp implements a visualization of the integral of f(x) from x = a to x = b.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class NumericalIntegrationApp extends AbstractCalculation {
  PlotFrame plotFrame = new PlotFrame("x", "f(x)", "Numerical integration visualization");

  public void reset() {
    control.setValue("f(x)", "cos(x)");
    control.setValue("lower limit a", 0);
    control.setValue("upper limit b", Math.PI/2);
    control.setValue("number of intervals n", 4);
  }

  public void calculate() {
    String fstring = control.getString("f(x)");
    double a = control.getDouble("lower limit a");
    double b = control.getDouble("upper limit b");
    int n = control.getInt("number of intervals n");
    Function f;
    try {
      f = new ParsedFunction(fstring);
    } catch(ParserException ex) {
      control.println(ex.getMessage());
      plotFrame.clearDrawables();
      return;
    }
    plotFrame.clearDrawables();
    plotFrame.setPreferredMinMaxX(a, b); // sets the domain of x to the integration limits
    plotFrame.addDrawable(new FunctionDrawer(f));
    RectangularApproximation approximate = new RectangularApproximation(f, a, b, n);
    plotFrame.addDrawable(approximate);
    plotFrame.setMessage("area = "+decimalFormat.format(approximate.sum));
    control.println("approximate area under curve = "+approximate.sum);
  }

  public static void main(String[] args) {
    CalculationControl.createApp(new NumericalIntegrationApp());
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
