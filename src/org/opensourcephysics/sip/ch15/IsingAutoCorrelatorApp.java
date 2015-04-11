/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;
import java.util.*;
import javax.swing.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

/**
 * IsingAutoCorrelatorApp computes the energy and magnetization
 * time autocorrelation function from IsingApp XML input data.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 04/10/05
 */
public class IsingAutoCorrelatorApp extends AbstractCalculation {
  PlotFrame plotFrame = new PlotFrame("tau", "<E(t+tau)E(t)> and <M(t+tau)M(t)>", "Time correlations");
  double[] energy = new double[0], magnetization = new double[0];
  int numberOfPoints;

  /**
   * Creates new arrays, reads data in and computes correlation.
   */
  public void calculate() {
    computeCorrelation(control.getInt("Maximum time interval, tau"));
  }

  public void readXMLData() {
    energy = new double[0];
    magnetization = new double[0];
    numberOfPoints = 0;
    String filename = "ising_data.xml";
    JFileChooser chooser = OSPRuntime.getChooser();
    int result = chooser.showOpenDialog(null);
    if(result==JFileChooser.APPROVE_OPTION) {
      filename = chooser.getSelectedFile().getAbsolutePath();
    } else {
      return;
    }
    XMLControlElement xmlControl = new XMLControlElement(filename);
    if(xmlControl.failedToRead()) {
      control.println("failed to read: "+filename);
    } else {
      // gets the datasets in the xml file
      Iterator<Dataset> it = xmlControl.getObjects(Dataset.class, false).iterator();
      while(it.hasNext()) {
        Dataset dataset = it.next();
        if(dataset.getName().equals("magnetization")) {
          magnetization = dataset.getYPoints();
        }
        if(dataset.getName().equals("energy")) {
          energy = dataset.getYPoints();
        }
      }
      numberOfPoints = magnetization.length;
      control.println("Reading: "+filename);
      control.println("Number of points = "+numberOfPoints);
    }
    calculate();
    plotFrame.repaint();
  }

  /**
   * Computes and plots correlation functions
   * @param tauMax is the maximum time for correlation functions
   */
  public void computeCorrelation(int tauMax) {
    plotFrame.clearData();
    double energyAccumulator = 0, magnetizationAccumulator = 0;
    double energySquaredAccumulator = 0, magnetizationSquaredAccumulator = 0;
    for(int t = 0;t<numberOfPoints;t++) {
      energyAccumulator += energy[t];
      magnetizationAccumulator += magnetization[t];
      energySquaredAccumulator += energy[t]*energy[t];
      magnetizationSquaredAccumulator += magnetization[t]*magnetization[t];
    }
    double averageEnergySquared = Math.pow(energyAccumulator/numberOfPoints, 2);
    double averageMagnetizationSquared = Math.pow(magnetizationAccumulator/numberOfPoints, 2);
    // compute normalization factors
    double normE = (energySquaredAccumulator/numberOfPoints)-averageEnergySquared;
    double normM = (magnetizationSquaredAccumulator/numberOfPoints)-averageMagnetizationSquared;
    for(int tau = 1;tau<=tauMax;tau++) {
      double c_MAccumulator = 0;
      double c_EAccumulator = 0;
      int counter = 0;
      for(int t = 0;t<numberOfPoints-tau;t++) {
        c_MAccumulator += magnetization[t]*magnetization[t+tau];
        c_EAccumulator += energy[t]*energy[t+tau];
        counter++;
      }
      // correlation function defined so that c(0) = 1 and c(infinity) -> 0
      plotFrame.append(0, tau, ((c_MAccumulator/counter)-averageMagnetizationSquared)/normM);
      plotFrame.append(1, tau, ((c_EAccumulator/counter)-averageEnergySquared)/normE);
    }
    plotFrame.setVisible(true);
  }

  /**
   * Resets input parameter
   */
  public void reset() {
    control.setValue("Maximum time interval, tau", 20);
    readXMLData();
  }

  /**
   * Starts Java application.
   * @param args  command line parameters
   */
  public static void main(String args[]) {
    CalculationControl.createApp(new IsingAutoCorrelatorApp());
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
