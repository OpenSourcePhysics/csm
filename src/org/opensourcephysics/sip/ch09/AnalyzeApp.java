/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import java.awt.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.*;

/**
 * AnalyzeApp calculates the Fourier coefficients of a function.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class AnalyzeApp extends AbstractCalculation {
  PlotFrame frame = new PlotFrame("frequency", "coefficients", "Fourier analysis");

  /**
   * Constructs AnalyzeApp.
   */
  public AnalyzeApp() {
    frame.setMarkerShape(0, Dataset.POST);
    frame.setMarkerColor(0, new Color(255, 0, 0, 128)); // semitransparent red
    frame.setMarkerShape(1, Dataset.POST);
    frame.setMarkerColor(1, new Color(0, 0, 255, 128)); // semitransparent blue
    frame.setXYColumnNames(0, "frequency", "cos");
    frame.setXYColumnNames(1, "frequency", "sin");
  }

  /**
   * Creates and displays the Fourier synthesis.
   */
  public void calculate() {
    double delta = control.getDouble("delta");
    int N = control.getInt("N");
    int numberOfCoefficients = control.getInt("number of coefficients");
    String fStr = control.getString("f(t)");
    Function f = null;
    try {
      f = new ParsedFunction(fStr, "t");
    } catch(ParserException ex) {
      control.println("Error parsing function string: "+fStr);
      return;
    }
    Analyze analyze = new Analyze(f, N, delta);
    double f0 = 1.0/(N*delta);
    for(int i = 0;i<=numberOfCoefficients;i++) {
      frame.append(0, i*f0, analyze.getCosineCoefficient(i));
      frame.append(1, i*f0, analyze.getSineCoefficient(i));
    }
    // Data tables can be displayed  by the user using
    // the tools menu but this statemment does so explicitly.
    frame.showDataTable(true);
  }

  /**
   * Resets the calculation to its default.
   */
  public void reset() {
    control.setValue("f(t)", "sin(pi*t/10)");
    control.setValue("delta", 0.1);
    control.setValue("N", 200);
    control.setValue("number of coefficients", 10);
    calculate();
  }

  /**
   * Command line entry point for program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new AnalyzeApp());
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
