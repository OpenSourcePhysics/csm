/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.Vector2DFrame;

/**
 * ElectricFieldApp draws the vector field from point charges.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class ElectricFieldApp extends AbstractCalculation implements InteractiveMouseHandler {
  int n = 20;                                // grid points on a side
  double a = 10;                             // viewing side length
  double[][][] eField = new double[2][n][n]; // stores electric field
  Vector2DFrame frame = new Vector2DFrame("x", "y", "Electric field");

  /**
   * The ElectricFieldApp constructor.
   */
  public ElectricFieldApp() {
    frame.setPreferredMinMax(-a/2, a/2, -a/2, a/2);
    frame.setZRange(false, 0, 2);
    frame.setAll(eField); // sets the vector field
    frame.setInteractiveMouseHandler(this);
  }

  /**
   * Adds a new charge.
   */
  public void calculate() {
    double x = control.getDouble("x");
    double y = control.getDouble("y");
    double q = control.getDouble("q");
    Charge charge = new Charge(x, y, q);
    frame.addDrawable(charge);
    calculateField();
  }

  /**
   * Removes charges and recalculates the field.
   */
  public void reset() {
    control.println("Calculate creates a new charge and updates the field.");
    control.println("You can drag charges.");
    frame.clearDrawables(); // removes all charges
    control.setValue("x", 0);
    control.setValue("y", 0);
    control.setValue("q", 1);
    calculateField();
  }

  /**
   * Calculates the field at the gridpoints
   */
  void calculateField() {
    for(int ix = 0;ix<n;ix++) {
      for(int iy = 0;iy<n;iy++) {
        eField[0][ix][iy] = eField[1][ix][iy] = 0; // zeros field
      }
    }
    // the charges in the frame
    List<Charge> chargeList = frame.getDrawables(Charge.class);
    Iterator<Charge> it = chargeList.iterator();
    while(it.hasNext()) {
      Charge charge = it.next();
      double xs = charge.getX(), ys = charge.getY();
      for(int ix = 0;ix<n;ix++) {
        double x = frame.indexToX(ix);
        double dx = (x-xs);             // distance of charge to gridpoint
        for(int iy = 0;iy<n;iy++) {
          double y = frame.indexToY(iy);
          double dy = (y-ys);           // charge to gridpoint
          double r2 = dx*dx+dy*dy;      // distance squared
          double r3 = Math.sqrt(r2)*r2; // distance cubed
          if(r3>0) {
            eField[0][ix][iy] += charge.q*dx/r3;
            eField[1][ix][iy] += charge.q*dy/r3;
          }
        }
      }
    }
    frame.setAll(eField);
  }

  /**
   * Handles mouse actions from the panel.
   *
   * @param panel
   * @param evt
   */
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt) {
    panel.handleMouseAction(panel, evt); // panel moves the charge
    if(panel.getMouseAction()==InteractivePanel.MOUSE_DRAGGED) {
      calculateField(); // remove this line if user interface is slugish
      panel.repaint();
    }
  }

  /**
   * The main method starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new ElectricFieldApp());
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
