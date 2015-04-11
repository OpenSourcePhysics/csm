/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;
import java.awt.event.MouseEvent;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * ElectricFieldApp draws electric field lines.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class FieldLineApp extends AbstractCalculation implements InteractiveMouseHandler {
  DisplayFrame frame = new DisplayFrame("x", "y", "Field lines");

  /**
   * The FieldLineApp constructor.
   */
  public FieldLineApp() {
    frame.setInteractiveMouseHandler(this);
    frame.setPreferredMinMax(-10, 10, -10, 10);
  }

  /**
   * Adds a charge to the panel and does the calculation.
   */
  public void calculate() {
    frame.removeObjectsOfClass(FieldLine.class); // remove old field lines
    double x = control.getDouble("x");
    double y = control.getDouble("y");
    double q = control.getDouble("q");
    Charge charge = new Charge(x, y, q);
    frame.addDrawable(charge);
  }

  /**
   * Removes all charges and field lines and sets the initial conditions.
   */
  public void reset() {
    control.println("Calculate creates a new charge and clears the field lines.");
    control.println("You can drag charges.");
    control.println("Double click in display to compute a field line.");
    frame.clearDrawables(); // remove charges and field lines
    control.setValue("x", 0);
    control.setValue("y", 0);
    control.setValue("q", 1);
  }

  /**
   * Handles mouse actions by dragging charges and starting field line calculations.
   *
   * @param panel
   * @param evt
   */
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt) {
    panel.handleMouseAction(panel, evt); // panel handles dragging
    switch(panel.getMouseAction()) {
    case InteractivePanel.MOUSE_DRAGGED :
      if(panel.getInteractive()==null) {
        return;
      }
      frame.removeObjectsOfClass(FieldLine.class); // field is invalid
      frame.repaint();                             // repaint to keep the screen up to date
      break;
    case InteractivePanel.MOUSE_CLICKED :
      if(evt.getClickCount()>1) {                  // check for double click
        double x = panel.getMouseX(), y = panel.getMouseY();
        FieldLine fieldLine = new FieldLine(frame, x, y, +0.1);
        panel.addDrawable(fieldLine);
        fieldLine = new FieldLine(frame, x, y, -0.1);
        panel.addDrawable(fieldLine);
      }
      break;
    }
  }

  /**
   * The main method starts the Java application.
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new FieldLineApp());
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
