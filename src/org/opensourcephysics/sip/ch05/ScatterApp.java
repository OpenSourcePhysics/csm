/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */
package org.opensourcephysics.sip.ch05;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * ScatterApp displays the scattering trajectories of particles and computes differential cross sections.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class ScatterApp extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "Trajectories");
  ScatterAnalysis analysis = new ScatterAnalysis();
  Scatter trajectory = new Scatter();
  double vx;    // speed of the incident particle
  double b, db; // impact parameter and increment
  double bmax;  // maximum impact parameter

  /**
   * Constructs ScatterApp.
   */
  public ScatterApp() {
    frame.setPreferredMinMax(-5, 5, -5, 5);
    frame.setSquareAspect(true);
  }

  /**
   * Does a scattering computation for a single impact parameter.
   */
  public void doStep() {
    if(trajectory.calculateTrajectory(frame, b, vx)) {
      analysis.detectParticle(b, trajectory.getAngle());
    } else {
      control.println("Trajectory did not converge at b = "+b);
    }
    frame.setMessage("b = "+decimalFormat.format(b));
    b += db; // increases the impact parameter
    frame.repaint();
    if(b>bmax) {
      control.calculationDone("Maximum impact parameter reached");
      analysis.plotCrossSection(b);
    }
  }

  /**
   * Initializes the animation after reading the values in the control.
   */
  public void initialize() {
    vx = control.getDouble("vx");
    bmax = control.getDouble("bmax");
    db = control.getDouble("db");
    b = db/2; // starts b at average value of first interval 0->db
    // b will increment to 3*db/2, 5*db/2, 7*db/2, ...
    frame.setMessage("b = 0");
    frame.clearDrawables(); // removes old trajectories
    analysis.clear();
  }

  /**
   * Resets the scatter program its default state.
   */
  public void reset() {
    control.setValue("vx", 3);
    control.setValue("bmax", 0.25);
    control.setValue("db", 0.01);
    initialize();
  }

  /**
   * Start Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new ScatterApp());
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
