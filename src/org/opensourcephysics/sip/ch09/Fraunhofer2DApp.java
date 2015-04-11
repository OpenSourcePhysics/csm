/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import org.opensourcephysics.frames.RasterFrame;
import org.opensourcephysics.numerics.FFT2D;

/**
 * Fraunhofer2DApp computes the 2D Fraunhofer diffraction pattern using the FFT alogrithm.
 *
 * @author       Wolfgang Christian
 * @version 1.0
 */
public class Fraunhofer2DApp {
  static final double PI2 = Math.PI*2;
  static final double LOG10 = Math.log(10);
  static final double ALPHA = Math.log(1.0e-3)/LOG10; // cutoff value

  /**
   * Starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    int n = 512;
    FFT2D fft2d = new FFT2D(n, n);
    double[] cdata = new double[2*n*n];
    double a = 10; // aperture screen dimension
    double dx = 2*a/n, dy = 2*a/n;
    double y = -a;
    for(int iy = 0;iy<n;iy++) {
      int offset = 2*iy*n;                    // offset to beginning of row
      double x = -a;
      for(int ix = 0;ix<n;ix++) {
        double r = Math.sqrt(x*x+y*y);
        cdata[offset+2*ix] = (r<0.5) ? 1 : 0; // circular aperture r=1
        cdata[offset+2*ix+1] = 0;
        x += dx;
      }
      y += dy;
    }
    fft2d.transform(cdata);
    fft2d.toNaturalOrder(cdata);
    double max = 0;
    // find the peak value
    for(int i = 0, n2 = n*n;i<n2;i++) {
      double re = cdata[2*i];   // real
      double im = cdata[2*i+1]; // imaginary
      max = Math.max(max, re*re+im*im);
    }
    int[] data = new int[n*n];
    // compute the intensity
    for(int i = 0, n2 = n*n;i<n2;i++) {
      double re = cdata[2*i];   // real
      double im = cdata[2*i+1]; // imaginary
      // log scale increases visibility of weaker fringes
      double logIntensity = Math.log((re*re+im*im)/max)/LOG10;
      data[i] = (logIntensity<=ALPHA) ? 0 : (int) (255*(1-logIntensity/ALPHA));
    }
    // raster for least memory and best speed
    RasterFrame frame = new RasterFrame("Fraunhofer Diffraction");
    frame.setBWPalette();
    frame.setAll(data, n, -0.5, 0.5, -0.5, 0.5); // send the fft data to the raster frame
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
