/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.InteractiveMouseHandler;
import org.opensourcephysics.display.InteractivePanel;

/** MethaneApp models CH4 in order to demonstrates how to transform 3D shapes.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class MethaneApp implements InteractiveMouseHandler {
  DisplayFrame frame = new DisplayFrame("Methane");
  Methane methane = new Methane();
  double mouseX = 0, mouseY = 0;

  /**
   * Constructs the MethaneApp.
   */
  public MethaneApp() {
    frame.addDrawable(methane);
    frame.setPreferredMinMax(-1, 1, -1, 1);
    frame.setInteractiveMouseHandler(this);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Handles the mouse action.
   *
   * @param panel InteractivePanel
   * @param evt MouseEvent
   */
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt) {
    switch(panel.getMouseAction()) {
    case InteractivePanel.MOUSE_DRAGGED :
      double dx = panel.getMouseX()-mouseX;
      double dy = panel.getMouseY()-mouseY;
      Rotation3D rotation = new Rotation3D(Math.sqrt(dx*dx+dy*dy), new double[] {dy, 0, dx});
      methane.transform(rotation);
      mouseX += dx;
      mouseY += dy;
      panel.repaint();
      break;
    case InteractivePanel.MOUSE_PRESSED :
      mouseX = panel.getMouseX();
      mouseY = panel.getMouseY();
      break;
    }
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    new MethaneApp();
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
