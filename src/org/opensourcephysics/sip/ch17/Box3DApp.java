/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import java.awt.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display3d.simple3d.*;

/**
 * Box3DApp demonstrates various 3D features.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class Box3DApp {
  public static void main(String[] args) {
    // creates a drawing frame and a drawing panel
    Display3DFrame frame = new Display3DFrame("3D demo");
    frame.setPreferredMinMax(-10, 10, -10, 10, -10, 10);
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_AXES);
    frame.setAllowQuickRedraw(false); // use shading when rotating
    Element block = new ElementBox();
    block.setXYZ(0, 0, 0);
    block.setSizeXYZ(6, 6, 3);
    block.getStyle().setFillColor(Color.RED);
    block.getStyle().setResolution(new Resolution(6, 6, 3)); // divides block into subblocks
    frame.addElement(block);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
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
