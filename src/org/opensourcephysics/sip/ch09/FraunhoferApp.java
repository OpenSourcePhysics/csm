/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.FFT;

/**
 * FraunhoferApp computes the Fraunhofer diffraction pattern using the FFT alogrithm.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class FraunhoferApp {
  static final double PI2 = Math.PI*2;
  static final double LOG10 = Math.log(10);           // Math.log is natural log
  static final double ALPHA = Math.log(1.0e-3)/LOG10; // cutoff value

  /**
   * Starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    PlotFrame plot = new PlotFrame("x", "intensity", "Fraunhofer diffraction");
    int N = 512;
    FFT fft = new FFT(N);
    double[] cdata = new double[2*N];
    double a = 10; // aperture screen dimension
    double dx = (2*a)/N;
    double x = -a;
    for(int ix = 0;ix<N;ix++) {
      cdata[2*ix] = (Math.abs(x)<0.4) ? 1 : 0; // slit
      cdata[(2*ix)+1] = 0;
      x += dx;
    }
    fft.transform(cdata);
    fft.toNaturalOrder(cdata);
    double max = 0;
    // find the max intensity value
    for(int i = 0;i<N;i++) {
      double real = cdata[2*i];     // real
      double imag = cdata[(2*i)+1]; // imaginary
      max = Math.max(max, (real*real)+(imag*imag));
      plot.append(0, i, (real*real)+(imag*imag));
    }
    plot.setVisible(true);
    // c reate N by 30 raster plot to show an image
    int[][] data = new int[N][30];
    // c ompute pixel intensity
    for(int i = 0;i<N;i++) {
      double real = cdata[2*i];     // real
      double imag = cdata[(2*i)+1]; // imaginary
      // log scale increases visibility of weaker fringes
      double logIntensity = Math.log(((real*real)+(imag*imag))/max)/LOG10;
      int intensity = (logIntensity<=ALPHA) ? 0 : (int) (254*(1-(logIntensity/ALPHA)));
      for(int j = 0;j<30;j++) {
        data[i][j] = intensity;
      }
    }
    RasterFrame frame = new RasterFrame("Fraunhofer Diffraction (log scale)");
    frame.setBWPalette();
    frame.setAll(data); // send the fft data to the raster frame
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
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
