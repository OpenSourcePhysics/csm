/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.FFTFrame;

/**
 * Calculates the fft and displays the result.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class FFTCalculationApp extends AbstractCalculation {
  FFTFrame frame = new FFTFrame("frequency", "amplitude", "FFT Frame Test");

  /**
   * Calculates the FFT.
   */
  public void calculate() {
    double xmin = control.getDouble("xmin");
    double xmax = control.getDouble("xmax");
    int n = control.getInt("N");
    double xi = xmin, delta = (xmax-xmin)/n;
    double[] data = new double[2*n];
    int mode = control.getInt("mode");
    for(int i = 0;i<n;i++) {
      data[2*i] = Math.cos(mode*xi);
      data[2*i+1] = Math.sin(mode*xi);
      xi += delta;
    }
    frame.doFFT(data, xmin, xmax);
    frame.showDataTable(true);
  }

  /**
   * Rests the default conditions.
   */
  public void reset() {
    control.setValue("mode", 1);
    control.setValue("xmin", 0);
    control.setValue("xmax", "2*pi");
    control.setValue("N", 32);
    calculate();
  }

  /**
   * Starts the Java application by creating the CalculationControl and the Calculation.
   * @param args String[]
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new FFTCalculationApp());
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
