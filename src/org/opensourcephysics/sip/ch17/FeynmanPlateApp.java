/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.controls.*;

/**
 * FeynmanPlateApp displays rigid body dynamics using quaternions.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class FeynmanPlateApp extends AbstractSimulation {
  FeynmanPlate plate = new FeynmanPlate();

  /**
   * Initializes the simulation by reading  parameters and passing them to the rigid body model.
   */
  public void initialize() {
    plate.dt = control.getDouble("dt");
    plate.spaceL[0] = control.getDouble("Lx");
    plate.spaceL[1] = control.getDouble("Ly");
    plate.spaceL[2] = control.getDouble("Lz");
    plate.setInertia(1, 1, 2); // sets angular momentum of the place
  }

  /**
   * Does an simulation step by advancing the time and updating the space view.
   */
  protected void doStep() {
    plate.advanceTime();
  }

  /**
   * Resets the simulation.
   */
  public void reset() {
    control.setValue("Lx", 0.1);
    control.setValue("Ly", 0.0);
    control.setValue("Lz", 1.0);
    control.setValue("dt", 0.1);
    enableStepsPerDisplay(true);
    initialize();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new FeynmanPlateApp());
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
