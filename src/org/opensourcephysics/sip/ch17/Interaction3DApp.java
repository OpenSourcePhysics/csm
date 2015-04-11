/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.display3d.simple3d.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display3d.core.interaction.*;

/**
 * Interaction3DApp demonstrates how to add and handle actions in an Display3DFrame.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class Interaction3DApp implements InteractionListener {
  Interaction3DApp() {
    Display3DFrame frame = new Display3DFrame("3D interactions");
    frame.setPreferredMinMax(-2.5, 2.5, -2.5, 2.5, -2.5, 2.5);
    frame.addInteractionListener(this); // accepts interactions from the frame's 3D drawing panel
    Element particle = new ElementCircle();
    particle.setSizeXYZ(1, 1, 1);
    particle.getInteractionTarget(org.opensourcephysics.display3d.core.Element.TARGET_POSITION).setEnabled(true); // enables interactions that change positions
    particle.addInteractionListener(this); // accepts interactions from the particle
    frame.addElement(particle); // adds the particle to the panel
    ElementArrow arrow = new ElementArrow();
    // enables interactions that change the size
    arrow.getInteractionTarget(org.opensourcephysics.display3d.core.Element.TARGET_SIZE).setEnabled(true);
    arrow.addInteractionListener(this); // accepts interactions from the arrow
    frame.addElement(arrow);            // adds the arrow to the panel
    frame.enableInteraction(true);      // enables interactions with the 3D Frame
    frame.addInteractionListener(this); // accepts interactions from the frame
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * Handles the interactions sent to this class.
   * @param _evt InteractionEvent
   */
  public void interactionPerformed(InteractionEvent _evt) {
    Object source = _evt.getSource();
    if(_evt.getID()==InteractionEvent.MOUSE_PRESSED) {
      System.out.println("Mouse clicked");
    }
    if(source instanceof ElementCircle) {
      System.out.println("A particle has been hit");
    }
  }

  static public void main(String args[]) {
    new Interaction3DApp();
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
