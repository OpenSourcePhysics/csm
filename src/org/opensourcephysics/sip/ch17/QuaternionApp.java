/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.numerics.Quaternion;
import org.opensourcephysics.frames.*;

/**
 * QuaternionApp demonstrates how to use quaternions to rotate objects.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class QuaternionApp extends AbstractCalculation {
  Display3DFrame frame = new Display3DFrame("Quaternion rotations");
  Quaternion transformation = new Quaternion(1, 0, 0, 0);
  BoxWithArrows box = new BoxWithArrows();

  /**
   * Constructs the QuaternionApp.
   */
  public QuaternionApp() {
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_AXES);
    frame.setAllowQuickRedraw(false); // scene is simple, so draw it properly when rotating
    frame.setPreferredMinMax(-6, 6, -6, 6, -6, 6);
    box.setTransformation(transformation);
    frame.addElement(box);
  }

  /**
   * Does a calcuation by setting the transformation and applying the transformation to the box.
   */
  public void calculate() {
    double q0 = control.getDouble("q0");
    double q1 = control.getDouble("q1");
    double q2 = control.getDouble("q2");
    double q3 = control.getDouble("q3");
    transformation.setCoordinates(q0, q1, q2, q3);
    box.setTransformation(transformation);
  }

  /**
   * Resets the default values.
   * The default quaternion represents the identity transformation.
   */
  public void reset() {
    control.clearMessages();
    control.setValue("q0", 1);
    control.setValue("q1", 0); // initial orientation is along x axis
    control.setValue("q2", 0);
    control.setValue("q3", 0);
    calculate();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new QuaternionApp());
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
