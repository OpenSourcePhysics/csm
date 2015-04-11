/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import java.text.DecimalFormat;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.numerics.FFT;

/**
 * FFTApp tests the FFT class by computing the transform of a complex exponential.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class FFTApp extends AbstractCalculation {

  /**
   * Main method for FFTApp program.
   *
   * @param args command line arguments
   */
  public void calculate() {
    DecimalFormat decimal = new DecimalFormat("0.0000"); // output format
    int N = 8;                                           // number of Fourier coefficients
    double[] z = new double[2*N];                        // array that will be transformed
    FFT fft = new FFT(N);                                // FFT implementation for N points
    int mode = control.getInt("mode");                   // mode or harmonic of e^(i*x)
    double x = 0, delta = 2*Math.PI/N;                   // signal will be sampled at f(x)
    for(int i = 0;i<N;i++) {
      z[2*i] = Math.cos(mode*x);   // real component of e^(i*mode*x)
      z[2*i+1] = Math.sin(mode*x); // imaginary component of e^(i*mode*x)
      x += delta;                  // increase x
    }
    fft.transform(z); // transform data; data will be in wrap-around order
    for(int i = 0;i<N;i++) {
      System.out.println("index = "+i+"\t real = "+decimal.format(z[2*i])+"\t imag = "+decimal.format(z[2*i+1]));
    }
  }

  public void reset() {
    control.setValue("mode", -1);
  }

  /**
   *   Starts the Java application by creating the CalculationControl and the Calculation.
   *   @param args String[]
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new FFTApp());
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
