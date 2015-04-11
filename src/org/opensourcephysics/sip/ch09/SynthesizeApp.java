/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.FunctionDrawer;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * SynthesizeApp displays a Fourier series using a linear superposition of sinusoidal functions.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class SynthesizeApp extends AbstractCalculation {
  DisplayFrame frame = new DisplayFrame("x", "f(x)", "Fourier Synthesis");

  /**
   * Reads the Fourier coefficients from the control and displays the Fourier series.
   */
  public void calculate() {
    double xmin = control.getDouble("xmin");
    double xmax = control.getDouble("xmax");
    int N = control.getInt("N");
    double period = control.getDouble("period");
    double[] sinCoefficients = (double[]) control.getObject("sin coefficients");
    double[] cosCoefficients = (double[]) control.getObject("cos coefficients");
    FunctionDrawer functionDrawer = new FunctionDrawer(new Synthesize(period, 0, cosCoefficients, sinCoefficients));
    functionDrawer.initialize(xmin, xmax, N, false);
    frame.clearDrawables();            // remove old function drawer
    frame.addDrawable(functionDrawer); // add new function drawer
  }

  /**
   * Resets the calculation to its default.
   */
  public void reset() {
    control.setValue("xmin", -1);
    control.setValue("xmax", 1);
    control.setValue("N", 300);
    control.setValue("period", 1);
    control.setValue("sin coefficients", new double[] {
      1.0, 0, 1.0/3.0, 0, 1.0/5.0, 0, 0
    });
    control.setValue("cos coefficients", new double[] {
      0, 0, 0, 0, 0, 0, 0
    });
    calculate();
  }

  /**
   * Command line entry point for program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new SynthesizeApp());
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
