/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.ComplexPlotFrame;

/**
 * BoxSuperpositionApp creates a linear superposition of quantum eigenstates.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class BoxSuperpositionApp extends AbstractSimulation {
  ComplexPlotFrame psiFrame = new ComplexPlotFrame("x", "|Psi|", "Time dependent wave function");
  BoxSuperposition superposition;
  double time, dt;

  public BoxSuperpositionApp() {
    psiFrame.limitAutoscaleY(-1, 1);
  }

  public void initialize() {
    time = 0;
    psiFrame.setMessage("t = "+decimalFormat.format(time));
    dt = control.getDouble("dt");
    double[] re = (double[]) control.getObject("real coef");
    double[] im = (double[]) control.getObject("imag coef");
    int numberOfPoints = control.getInt("number of points");
    superposition = new BoxSuperposition(numberOfPoints, re, im);
    psiFrame.append(superposition.x, superposition.realPsi, superposition.imagPsi);
  }

  public void doStep() {
    time += dt;
    superposition.update(time);
    psiFrame.clearData();
    psiFrame.append(superposition.x, superposition.realPsi, superposition.imagPsi);
    psiFrame.setMessage("t = "+decimalFormat.format(time));
  }

  public void reset() {
    control.setValue("dt", 0.005);
    control.setValue("real coef", new double[] {0.707, 0, 0.707});
    control.setValue("imag coef", new double[] {0, 0, 0});
    control.setValue("number of points", 50);
    initialize();
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new BoxSuperpositionApp());
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
