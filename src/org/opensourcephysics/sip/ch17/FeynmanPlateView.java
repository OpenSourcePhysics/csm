/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.display3d.simple3d.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.VectorMath;

/**
 * FeynmanPlateView shows a rotating plate as seen from the space (laboratory) frame.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class FeynmanPlateView {
  Element plate = new ElementCylinder();
  Element omega = new ElementArrow();
  Element angularMomentum = new ElementArrow();
  Element bodyX = new ElementArrow(); // x axis in body frame
  Element bodyY = new ElementArrow(); // y axis in body frame
  ElementTrail trailX = new ElementTrail();
  ElementTrail trailY = new ElementTrail();
  ElementText labelOmega = new ElementText("$\\omega$");
  Group bodyGroup = new Group();
  Display3DFrame frame = new Display3DFrame("Space View");
  FeynmanPlate rigidBody;

  /**
   * Constucts space view for a rotating plate.
   * A space view shows the rigid body as seen from the intertial laboratory frame.
   * @param rigidBody RigidBody
   */
  public FeynmanPlateView(FeynmanPlate rigidBody) {
    this.rigidBody = rigidBody; // save a reference to the mathematical model
    plate.setSizeXYZ(2, 2, 0.1);
    bodyX.setSizeXYZ(1.4, 0, 0);
    bodyY.setSizeXYZ(0, 1.4, 0);
    frame.setPreferredMinMax(-2, 2, -2, 2, -2, 2);
    frame.setSize(600, 600);
    plate.getStyle().setFillColor(java.awt.Color.LIGHT_GRAY);
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_NONE);
    // frame.setDecorationType(VisualizationHints.DECORATION_AXES);
    omega.getStyle().setFillColor(java.awt.Color.YELLOW);
    angularMomentum.getStyle().setFillColor(java.awt.Color.BLUE);
    bodyX.getStyle().setResolution(new Resolution(10));
    bodyY.getStyle().setResolution(new Resolution(10));
    bodyX.getStyle().setFillColor(java.awt.Color.RED);
    bodyY.getStyle().setFillColor(java.awt.Color.GREEN);
    trailX.getStyle().setLineColor(java.awt.Color.RED);
    trailY.getStyle().setLineColor(java.awt.Color.GREEN);
    bodyGroup.addElement(plate);
    bodyGroup.addElement(omega);
    bodyGroup.addElement(labelOmega);
    bodyGroup.addElement(bodyX);
    bodyGroup.addElement(bodyY);
    // coordinates in space
    frame.addElement(trailX);
    frame.addElement(trailY);
    frame.addElement(angularMomentum);
    frame.addElement(bodyGroup);
    frame.setAllowQuickRedraw(false); // The scene is simple, so draw it properly when rotating
    bodyGroup.setTransformation(rigidBody.getTransformation());
  }

  /**
   * Initializes the space view by setting the scale and clearing traces.
   */
  void initialize() {
    trailX.clear();
    trailY.clear();
    update();
  }

  /**
   * Updates the space view to show changes in the rigid body model.
   */
  void update() {
    bodyGroup.setTransformation(rigidBody.getTransformation());
    double[] vec = new double[] {rigidBody.wx, rigidBody.wy, rigidBody.wz};
    double norm = Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
    norm = Math.max(norm, 1.0e-6);
    double s = VectorMath.magnitude(rigidBody.spaceL)/norm;
    omega.setSizeXYZ(s*vec[0], s*vec[1], s*vec[2]); // scale omega to same length as angular momentum
    labelOmega.setXYZ(s*vec[0], s*vec[1], s*vec[2]);
    angularMomentum.setSizeXYZ(rigidBody.spaceL);
    double[] tipX = new double[] {bodyX.getSizeX(), 0, 0};
    bodyGroup.toSpaceFrame(tipX);
    trailX.addPoint(tipX[0], tipX[1], tipX[2]);
    double[] tipY = new double[] {0, bodyY.getSizeY(), 0};
    bodyGroup.toSpaceFrame(tipY);
    trailY.addPoint(tipY[0], tipY[1], tipY[2]);
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
