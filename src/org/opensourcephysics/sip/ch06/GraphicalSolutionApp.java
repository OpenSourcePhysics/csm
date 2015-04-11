/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch06;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.PlotFrame;

/**
 * GraphicalSolutionApp presents a graphical solution to the logistic map.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class GraphicalSolutionApp extends AbstractSimulation {
  PlotFrame plotFrame = new PlotFrame("x", "f(x)", "graphical solution");
  double r;    // control parameter
  int iterate; // iterate of f(x)
  double x, y;
  double x0, y0;

  public GraphicalSolutionApp() {
    plotFrame.setPreferredMinMax(0, 1, 0, 1);
    plotFrame.setConnected(true);
    plotFrame.setXPointsLinked(true);
    plotFrame.setMarkerShape(0, 0); // second argument indicates no marker
    plotFrame.setMarkerShape(2, 0);
  }

  public void reset() {
    control.setValue("r", 0.89);
    control.setValue("x", 0.2);
    control.setAdjustableValue("iterate", 1);
  }

  public void initialize() {
    r = control.getDouble("r");
    x = control.getDouble("x");
    iterate = control.getInt("iterate");
    x0 = x;
    y0 = 0;
    clear();
  }

  public void startRunning() {
    if(iterate!=control.getInt("iterate")) {
      iterate = control.getInt("iterate");
      clear();
    }
    r = control.getDouble("r");
  }

  public void doStep() {
    y = f(x, r, iterate);
    plotFrame.append(1, x0, y0);
    plotFrame.append(1, x0, y);
    plotFrame.append(1, y, y);
    x = x0 = y0 = y;
    control.setValue("x", x);
  }

  void drawFunction() {
    int nplot = 200; // # of points at which function computed
    double delta = 1.0/nplot;
    double x = 0;
    double y = 0;
    for(int i = 0;i<=nplot;i++) {
      y = f(x, r, iterate);
      plotFrame.append(0, x, y);
      x += delta;
    }
  }

  void drawLine() { // draws line y = x
    for(double x = 0;x<1;x += 0.001) {
      plotFrame.append(2, x, x);
    }
  }

  public double f(double x, double r, int iterate) {
    if(iterate>1) {
      double y = f(x, r, iterate-1);
      return 4*r*y*(1-y);
    }
	return 4*r*x*(1-x);
  }

  public void clear() {
    plotFrame.clearData();
    drawFunction();
    drawLine();
    plotFrame.repaint();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new GraphicalSolutionApp());
    control.addButton("clear", "Clear", "Clears the trajectory.");
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
