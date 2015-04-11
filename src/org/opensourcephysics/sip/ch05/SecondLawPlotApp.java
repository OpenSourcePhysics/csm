/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch05;
import org.opensourcephysics.frames.PlotFrame;

/**
 * SecondLawPlotApp demonstrates how to create log-log plots using
 * data from planetary orbits to demonstrate Kepler's second law.
 *
 * The program plots the log of the planet's semi-major axis vs. the log of the orbital period.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class SecondLawPlotApp {

  /**
   * Command line entry point for program.
   * @param args
   */
  public static void main(String[] args) {
    PlotFrame frame = new PlotFrame("ln(a)", "ln(T)", "Kepler's second law");
    frame.setLogScale(true, true);
    frame.setConnected(false);
    double[] period = {
      0.241, 0.615, 1.0, 1.88, 11.86, 29.50, 84.0, 165, 248
    };
    double[] a = {
      0.387, 0.723, 1.0, 1.523, 5.202, 9.539, 19.18, 30.06, 39.44
    };
    frame.append(0, a, period);
    frame.setVisible(true);
    // defines titles of table columns
    frame.setXYColumnNames(0, "T (years)", "a (AU)");
    // shows data table; can also be done from frame menu
    frame.showDataTable(true);
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
