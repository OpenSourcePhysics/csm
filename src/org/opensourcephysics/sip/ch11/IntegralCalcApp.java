/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch11;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.numerics.*;

/**
 * IntegralCalcApp tests static methods in the Integral class.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould
 *  @version 1.0   revised 06/09/05
 */
public class IntegralCalcApp extends AbstractCalculation {
  public void reset() {
    control.setValue("a", 0);
    control.setValue("b", 1);
    control.setValue("tolerance", 1.0e-2);
    control.setValue("f(x)", "sin(2*pi*x)^2");
  }

  public void calculate() {
    Function f;
    String fx = control.getString("f(x)");
    try { // read in function to integrate
      f = new ParsedFunction(fx);
    } catch(ParserException ex) {
      control.println(ex.getMessage());
      return;
    }
    double a = control.getDouble("a");
    double b = control.getDouble("b");
    double tolerance = control.getDouble("tolerance");
    double area = Integral.ode(f, a, b, tolerance);
    control.println("ODE area = "+area);
    area = Integral.trapezoidal(f, a, b, 2, tolerance);
    control.println("Trapezoidal area = "+area);
    area = Integral.simpson(f, a, b, 2, tolerance);
    control.println("Simpson area = "+area);
    area = Integral.romberg(f, a, b, 2, tolerance);
    control.println("Romberg area ="+area);
  }

  public static void main(String[] args) {
    CalculationControl.createApp(new IntegralCalcApp());
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
