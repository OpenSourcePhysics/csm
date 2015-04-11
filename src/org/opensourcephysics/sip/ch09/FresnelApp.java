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
 *FresnelApp computes the Fresnel diffraction pattern from a circular aperture.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class FresnelApp {
  final static double PI2 = Math.PI*2;
  final static double PI4 = PI2*PI2;

  /**
   *Starts the Java application.
   *@param args[]  the input parameters
   */
  public static void main(String[] args) {
    int N = 512;                        // power of 2 for optimum speed
    double z = 0.5e+6;                  // distance from aperture to screen
    FFT2D fft2d = new FFT2D(N, N);
    double[] cdata = new double[2*N*N]; // complex data
    double a = 6000;                    // aperture mask dimension
    double dx = 2*a/N, dy = 2*a/N;
    double x = -a;
    for(int ix = 0;ix<N;ix++) {
      int offset = 2*ix*N;
      double y = -a;
      for(int iy = 0;iy<N;iy++) {
        double r2 = (x*x+y*y);
        cdata[offset+2*iy] = (r2<4e6) ? 1 : 0; // circular aperture
        cdata[offset+2*iy+1] = 0;
        y += dy;
      }
      x += dx;
    }
    fft2d.transform(cdata);
    // get arrays containing the wavenumbers in wrapped order
    double[] kx = fft2d.getWrappedOmegaX(-a, a);
    double[] ky = fft2d.getWrappedOmegaY(-a, a);
    for(int ix = 0;ix<N;ix++) {
      int offset = 2*ix*N; // offset to beginning of row
      for(int iy = 0;iy<N;iy++) {
        double radical = PI4-kx[ix]*kx[ix]-ky[iy]*ky[iy];
        if(radical>0) {
          double phase = z*Math.sqrt(radical);
          double real = Math.cos(phase);
          double imag = Math.sin(phase);
          double temp = cdata[offset+2*iy];
          cdata[offset+2*iy] = real*cdata[offset+2*iy]-imag*cdata[offset+2*iy+1];
          cdata[offset+2*iy+1] = real*cdata[offset+2*iy+1]+imag*temp;
        } else { // evanescent waves decay exponentially
          double decay = Math.exp(-z*Math.sqrt(-radical));
          cdata[offset+2*iy] *= decay;
          cdata[offset+2*iy+1] *= decay;
        }
      }
    }
    fft2d.inverse(cdata);
    double max = 0;
    for(int i = 0;i<N*N;i++) {    // find max intensity
      double real = cdata[2*i];   // real
      double imag = cdata[2*i+1]; // imaginary
      max = Math.max(max, real*real+imag*imag);
    }
    // intensity is squared magnitude of the amplitude
    int[] data = new int[N*N];
    for(int i = 0, N2 = N*N;i<N2;i++) {
      double real = cdata[2*i];   // real
      double imag = cdata[2*i+1]; // imaginary
      data[i] = (int) (255*(real*real+imag*imag)/max);
    }
    // raster for least memory and best speed
    RasterFrame frame = new RasterFrame("Fraunhofer Diffraction");
    frame.setBWPalette();
    frame.setAll(data, N, -0.5, 0.5, -0.5, 0.5);
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
