/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;

/**
 * SimpleFFT performs a Fast Fourier Transform of complex data.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class SimpleFFT {

  /**
   * Transforms the data.
   *
   * @param real
   * @param imag
   */
  public static void transform(double[] real, double[] imag) {
    int N = real.length;
    int pow = 0;
    while(N/2>0) {
      if(N%2==0) { // N should be even
        pow++;
        N /= 2;
      } else {
        throw new IllegalArgumentException("Number of points in this FFT implementation must be even.");
      }
    }
    int N2 = N/2;
    int jj = N2;
    // rearrange input according to bit reversal
    for(int i = 1;i<N-1;i++) {
      if(i<jj) {
        double tempRe = real[jj];
        double tempIm = imag[jj];
        real[jj] = real[i];
        imag[jj] = imag[i];
        real[i] = tempRe;
        imag[i] = tempIm;
      }
      int k = N2;
      while(k<=jj) {
        jj = jj-k;
        k = k/2;
      }
      jj = jj+k;
    }
    jj = 1;
    for(int p = 1;p<=pow;p++) {
      int inc = 2*jj;
      double wp1 = 1, wp2 = 0;
      double theta = Math.PI/jj;
      double cos = Math.cos(theta), sin = -Math.sin(theta);
      for(int j = 0;j<jj;j++) {
        for(int i = j;i<N;i += inc) {
          // calculate the transform of 2^p
          int ip = i+jj;
          double tempRe = wp1*real[ip]-wp2*imag[ip];
          double tempIm = wp2*real[ip]+wp1*imag[ip];
          real[ip] = real[i]-tempRe;
          imag[ip] = imag[i]-tempIm;
          real[i] = real[i]+tempRe;
          imag[i] = imag[i]+tempIm;
        }
        double temp = wp1;
        wp1 = wp1*cos-wp2*sin;
        wp2 = temp*sin+wp2*cos;
      }
      jj = inc;
    }
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
