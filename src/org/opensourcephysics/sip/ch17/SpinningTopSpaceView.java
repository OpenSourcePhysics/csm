/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display3d.simple3d.*;
import org.opensourcephysics.numerics.Transformation;

/**
 * SpinningTopSpaceView shows a spinning top as seen from the laboratory frame.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/10/2005
 */
public class SpinningTopSpaceView {
  Group topGroup = new Group();
  Element shaft = new ElementCylinder();
  Element disk = new ElementCylinder();
  Element base = new ElementCylinder();
  Element post = new ElementCylinder();
  Element orientation = new ElementArrow();
  ElementTrail orientationTrace = new ElementTrail();
  Display3DFrame frame = new Display3DFrame("Space View");
  SpinningTop rigidBody;

  public SpinningTopSpaceView(SpinningTop rigidBody) {
    this.rigidBody = rigidBody;
    frame.setSize(600, 600);
    // panel.setDisplayMode(VisualizationHints.DISPLAY_NO_PERSPECTIVE);
    double d = 4;
    frame.setPreferredMinMax(-d, d, -d, d, -d, d);
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_AXES);
    orientation.getStyle().setFillColor(java.awt.Color.RED);
    orientationTrace.getStyle().setLineColor(java.awt.Color.BLACK);
    base.setSizeXYZ(2, 2, 0.15);
    base.getStyle().setResolution(new Resolution(4, 12, 1));
    base.getStyle().setFillColor(java.awt.Color.RED);
    base.setZ(-3);
    post.setSizeXYZ(0.2, 0.2, 3);
    post.getStyle().setResolution(new Resolution(2, 10, 15));
    post.setZ(-1.5); // shift by half the length
    post.getStyle().setFillColor(java.awt.Color.RED);
    shaft.setSizeXYZ(0.2, 0.2, 3);
    shaft.setXYZ(0, 0, 1.5);
    shaft.getStyle().setResolution(new Resolution(1, 10, 15));
    disk.setSizeXYZ(1.75, 1.75, 0.25);
    disk.setXYZ(0, 0, 2.0);
    disk.getStyle().setResolution(new Resolution(4, 12, 1));
    topGroup.addElement(shaft);
    topGroup.addElement(disk);
    topGroup.setTransformation(rigidBody.getTransformation());
    frame.addElement(base);
    frame.addElement(post);
    frame.addElement(orientation);
    frame.addElement(orientationTrace);
    frame.addElement(topGroup);
  }

  void initialize() {
    double dx = 1/Math.sqrt(rigidBody.I1); // dimension of ellipsoid is inverse to inertia
    double dy = 1/Math.sqrt(rigidBody.I2);
    double dz = 1/Math.sqrt(rigidBody.I3);
    double scale = Math.max(Math.max(4*dx, 4*dy), 4*dz); // bounding dimension
    frame.setPreferredMinMax(-scale, scale, -scale, scale, -scale, scale);
    orientationTrace.clear();
    update();
  }

  void update() {
    Transformation transformation = rigidBody.getTransformation();
    topGroup.setTransformation(transformation);
    double s = 1.5*shaft.getSizeZ();
    double[] vec = topGroup.toSpaceFrame(new double[] {0, 0, 1});
    orientation.setSizeXYZ(s*vec[0], s*vec[1], s*vec[2]);
    orientationTrace.addPoint(s*vec[0], s*vec[1], s*vec[2]);
    frame.render();
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
