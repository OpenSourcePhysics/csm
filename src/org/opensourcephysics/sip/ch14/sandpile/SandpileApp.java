/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */


package  org.opensourcephysics.sip.ch14.sandpile;

import  org.opensourcephysics.controls.*;
import  org.opensourcephysics.frames.*;


/**
 * SandpileApp simulates and displays an idealized 2D model of a sandpile.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class SandpileApp extends AbstractSimulation {
  Sandpile sandpile = new Sandpile();
  LatticeFrame height = new LatticeFrame("x", "y", "Sandpile");
  PlotFrame plotFrame = new PlotFrame("ln s", "ln N", "Distribution of toppled sites");

  public SandpileApp () {
    height.setIndexedColor(0, java.awt.Color.WHITE);
    height.setIndexedColor(1, java.awt.Color.BLUE);
    height.setIndexedColor(2, java.awt.Color.GREEN);
    height.setIndexedColor(3, java.awt.Color.RED);
    height.setIndexedColor(4, java.awt.Color.BLACK);
  }

  public void initialize () {
    sandpile.L = control.getInt("L");
    height.setPreferredMinMax(0, sandpile.L, 0, sandpile.L);
    sandpile.initialize(height);
  }

  public void doStep () {
    sandpile.step();
  }

  public void stop () {
    plotFrame.clearData();
    for (int s = 1; s < sandpile.distribution.length; s++) {
      double f = sandpile.distribution[s];
      double N = sandpile.numberOfGrains;
      if (f > 0) {
        plotFrame.append(0, Math.log(s), Math.log(f/N));
      }
    }
    plotFrame.render();
  }

  public void reset () {
    control.setValue("L", 10);
    enableStepsPerDisplay(true);
  }

  public void resetAverages () {
    sandpile.resetAverages();
  }

  public static void main (String[] args) {
    SimulationControl control = SimulationControl.createApp(new SandpileApp());
    control.addButton("resetAverages", "resetAverages");
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

