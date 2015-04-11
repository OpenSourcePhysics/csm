/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.display3d.simple3d.*;
import org.opensourcephysics.frames.*;

/**
 * RigidBodySpaceView shows the rigid body as seen from the intertial laboratory frame.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/10/2005
 */
public class FreeRotationSpaceView {
  Element ellipsoid = new ElementEllipsoid();
  Element omega = new ElementArrow();
  Element angularMomentum = new ElementArrow();
  ElementTrail omegaTrace = new ElementTrail();
  Display3DFrame frame = new Display3DFrame("Space view");
  FreeRotation rigidBody;
  double scale = 1;

  /**
   * Constucts space view for the given rigid body.
   * A space view shows the rigid body as seen from the intertial laboratory frame.
   * @param _rigidBody RigidBody
   */
  public FreeRotationSpaceView(FreeRotation _rigidBody) { // eliminate _rigidBody and use this
    rigidBody = _rigidBody;
    frame.setSize(600, 600);
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_AXES);
    omega.getStyle().setFillColor(java.awt.Color.RED);
    omegaTrace.getStyle().setLineColor(java.awt.Color.RED);
    angularMomentum.getStyle().setFillColor(java.awt.Color.GREEN);
    ellipsoid.setTransformation(rigidBody.getTransformation());
    frame.addElement(ellipsoid);
    frame.addElement(omega);
    frame.addElement(angularMomentum);
    frame.addElement(omegaTrace);
  }

  /**
   * Initializes the space view by setting the scale and clearing traces.
   *
   * @param a double
   * @param b double
   * @param c double
   */
  void initialize(double a, double b, double c) {
    ellipsoid.setSizeXYZ(2*a, 2*b, 2*c);
    scale = Math.max(Math.max(3*a, 3*b), 3*c); // bounding dimension for space frame
    frame.setPreferredMinMax(-scale, scale, -scale, scale, -scale, scale);
    omegaTrace.clear();
    update();
  }

  /**
   * Updates the space view when the orientation of the rigid body changes.
   */
  void update() {
    ellipsoid.setTransformation(rigidBody.getTransformation());
    double[] vec = ellipsoid.toSpaceFrame(rigidBody.getBodyFrameOmega());
    double norm = Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
    norm = Math.max(norm, 1.0e-6);
    double s = 0.75*scale/norm;
    omega.setSizeXYZ(s*vec[0], s*vec[1], s*vec[2]);
    omegaTrace.addPoint(s*vec[0], s*vec[1], s*vec[2]);
    vec = ellipsoid.toSpaceFrame(rigidBody.getBodyFrameAngularMomentum());
    // vec = rigidBody.getSpaceFrameAngularMomentum();
    norm = Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
    norm = Math.max(norm, 1.0e-6);
    s = 0.75*scale/norm;
    angularMomentum.setSizeXYZ(s*vec[0], s*vec[1], s*vec[2]);
    frame.repaint();
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
