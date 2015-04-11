/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */
package org.opensourcephysics.sip.ch05;
import org.opensourcephysics.frames.PlotFrame;

/**
 * ScatterAnalysis accumulates particle scattering data and plots the differential cross section.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class ScatterAnalysis {
  int numberOfBins = 18;
  PlotFrame frame = new PlotFrame("angle", "sigma", "differential cross section");
  double[] bins = new double[numberOfBins];
  double dtheta = Math.PI/(numberOfBins);
  double totalN = 0; // total number of scattered particles

  /**
   * Clears the cross section data.
   */
  void clear() {
    for(int i = 0;i<numberOfBins;i++) {
      bins[i] = 0;
    }
    totalN = 0;
    frame.clearData();
    frame.repaint();
  }

  /**
   * Detects a particle and accumulates cross section data.
   *
   * @param b the impact parameter
   * @param db the impact parameter increment
   * @param theta the scattering angle
   */
  void detectParticle(double b, double theta) {
    theta = Math.abs(theta); // treats positive and negative angles equally to get better statistics
    int index = (int) (theta/dtheta);
    bins[index] += b;
    totalN += b;
  }

  /**
   * Plots the cross section data.
   *
   * @param  radius the beam radius
   */
  void plotCrossSection(double radius) {
    double targetDensity = 1/Math.PI/radius/radius;
    double delta = (dtheta*180)/Math.PI; // uses degrees for plot
    frame.clearData();
    for(int i = 0;i<numberOfBins;i++) {
      double domega = 2*Math.PI*Math.sin((i+0.5)*dtheta)*dtheta;
      double sigma = bins[i]/totalN/targetDensity/domega;
      frame.append(0, (i+0.5)*delta, sigma);
    }
    frame.setVisible(true);
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
