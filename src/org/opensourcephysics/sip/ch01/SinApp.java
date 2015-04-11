/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch01;
import javax.swing.JFrame;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;

/**
 * SinApp creates a plot of a sin function.
 *
 * This class demonstrates:
 *   1)  how to instantiate objects.
 *   2)  how to add points to the dataset ot produce a plot
 */
public class SinApp {
  public static void main(String[] args) {
    // create the plot
    PlottingPanel plot = new PlottingPanel("Time", "Amp", "Sin Function");
    DrawingFrame frame = new DrawingFrame(plot); // create frame
    Dataset dataset = new Dataset();             // create dataset
    plot.addDrawable(dataset); // add dataset to plot
    double t = 0, dt = 0.01; // initialize variables
    while(t<2) {                                // start loop
      dataset.append(t, Math.sin(2*Math.PI*t)); // add point
      t += dt;                                  // increment time
    }
    frame.setVisible(true); // show the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
