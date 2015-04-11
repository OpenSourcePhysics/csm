/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.display3d.simple3d.*;

/**
 * BoxWithArrows creates a 3D visualization that can be transformed as a group.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class BoxWithArrows extends Group {
  Element body = new ElementBox();
  Element xaxisBody = new ElementArrow();
  Element yaxisBody = new ElementArrow();
  Element zaxisBody = new ElementArrow();

  public BoxWithArrows() {
    xaxisBody.getStyle().setFillColor(java.awt.Color.RED);
    xaxisBody.getStyle().setResolution(new Resolution(10));
    yaxisBody.getStyle().setFillColor(java.awt.Color.GREEN);
    yaxisBody.getStyle().setResolution(new Resolution(10));
    zaxisBody.getStyle().setFillColor(java.awt.Color.BLUE);
    zaxisBody.getStyle().setResolution(new Resolution(10));
    xaxisBody.setSizeXYZ(5, 0, 0);
    yaxisBody.setSizeXYZ(0, 5, 0);
    zaxisBody.setSizeXYZ(0, 0, 5);
    body.getStyle().setFillColor(new java.awt.Color(0, 0, 255, 64)); // transparent blue
    body.getStyle().setLineColor(java.awt.Color.BLUE);
    body.setSizeXYZ(1.0, 2.0, 4.0);
    addElement(body);      // adds the body to the group
    addElement(xaxisBody); // adds the arrow to the group
    addElement(yaxisBody); // adds the arrow to the group
    addElement(zaxisBody); // adds the arrow to the group
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
