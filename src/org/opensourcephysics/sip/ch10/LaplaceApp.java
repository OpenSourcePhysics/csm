/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;
import java.awt.event.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.display2d.*;
import org.opensourcephysics.frames.*;

/**
 * LaplaceApp uses the Jacobi relaxation method to solve Laplace's equation.
 *
 * Revised 04/04/2006 to fix missing "size" parameter in initialize method.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.1
 */
public class LaplaceApp extends AbstractSimulation implements InteractiveMouseHandler {
  Scalar2DFrame frame = new Scalar2DFrame("x", "y", "Electric potential");
  boolean[][] isConductor;
  double[][] potential; // electric potential
  double maximumError;
  int gridSize;         // number of sites on side of grid

  /**
   * The LaplaceApp constructor.
   */
  public LaplaceApp() {
    frame.setInteractiveMouseHandler(this);
  }

  /**
   * Initializes the animation and reads parameters from the control.
   */
  public void initialize() {
    maximumError = control.getDouble("maximum error");
    gridSize = control.getInt("size");  // "size" was missing in first printing of CSM book.
    initArrays();
    frame.setVisible(true);
    frame.showDataTable(true); // show the data table
  }

  /**
   * Initialize voltage and conductor arrays.
   */
  public void initArrays() {
    isConductor = new boolean[gridSize][gridSize];
    potential = new double[gridSize][gridSize];
    frame.setPaletteType(ColorMapper.DUALSHADE);
    // isConductor array is false by default; voltage in potential array is 0 by default
    for(int i = 0;i<gridSize;i++) {      // initialize the sides
      isConductor[0][i] = true;          // left boundary
      isConductor[gridSize-1][i] = true; // right boundary
      isConductor[i][0] = true;          // bottom boundary
      isConductor[i][gridSize-1] = true; // top boundary
    }
    for(int i = 5;i<gridSize-5;i++) { // set potential on inner conductor
      potential[gridSize/3][i] = 100;
      isConductor[gridSize/3][i] = true;
      potential[2*gridSize/3][i] = -100;
      isConductor[2*gridSize/3][i] = true;
    }
    frame.setAll(potential);
  }

  /**
   * Does a single relaxation step and redraws the panel.
   */
  public void doStep() {
    double error = 0;
    for(int i = 1;i<gridSize-1;i++) {
      for(int j = 1;j<gridSize-1;j++) {
        if(!isConductor[i][j]) { // change the voltage for non-conductors
          double v = (potential[i-1][j]+potential[i+1][j]+potential[i][j-1]+potential[i][j+1])/4;
          double dv = potential[i][j]-v;
          error = Math.max(error, Math.abs(dv));
          potential[i][j] = v;
        }
      }
    }
    frame.setAll(potential);
    if(error<maximumError) {
      animationThread = null; // this will stop the simulation thread
      control.calculationDone("Computation done.");
    }
  }

  /**
   * Resets the animation model to a predefined state.
   */
  public void reset() {
    control.setValue("maximum error", 0.1);
    control.setValue("size", 31);
    initialize();
  }

  /**
   * Handles mouse actions by dragging the current interactive drawable object.
   *
   * @param panel
   * @param evt
   */
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt) {
    switch(panel.getMouseAction()) {
    case InteractivePanel.MOUSE_DRAGGED :
    case InteractivePanel.MOUSE_PRESSED :
      double x = panel.getMouseX(); // mouse x in world units
      double y = panel.getMouseY();
      int i = frame.xToIndex(x);    // closest array index
      int j = frame.yToIndex(y);
      frame.setMessage("V="+decimalFormat.format(potential[i][j]));
      break;
    case InteractivePanel.MOUSE_RELEASED :
      panel.setMessage(null);
      break;
    }
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new LaplaceApp());
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
