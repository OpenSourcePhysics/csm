/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;
import org.opensourcephysics.controls.*;

/**
 * IdealDemonApp simulates the microcanonical Monte Carlo of the ideal classical gas in one dimension.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class IdealDemonApp extends AbstractSimulation {
  IdealDemon idealGas = new IdealDemon();

  public void initialize() {
    idealGas.N = control.getInt("number of particles N");
    idealGas.systemEnergy = control.getDouble("desired total energy");
    idealGas.delta = control.getDouble("maximum velocity change");
    idealGas.initialize();
  }

  public void doStep() {
    idealGas.doOneMCStep();
  }

  public void stop() {
    double norm = 1.0/(idealGas.mcs*idealGas.N);
    control.println("mcs = "+idealGas.mcs);
    control.println("<Ed> = "+idealGas.demonEnergyAccumulator*norm);
    control.println("<E> = "+idealGas.systemEnergyAccumulator*norm);
    control.println("acceptance ratio = "+idealGas.acceptedMoves*norm);
  }

  public void reset() {
    control.setValue("number of particles N", 40);
    control.setValue("desired total energy", 40);
    control.setValue("maximum velocity change", 2.0);
  }

  public void resetData() {
    idealGas.resetData();
    idealGas.delta = control.getDouble("maximum velocity change");
    control.clearMessages();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new IdealDemonApp());
    control.addButton("resetData", "Reset Data"); //
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
