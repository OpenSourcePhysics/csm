/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch13;
import java.awt.Graphics;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.*;

/**
 *  Draws a Koch curve.
 *
 *  @author Jan Tobochnik, Wolfgang Christian, Harvey Gould
 * @version 1.0  revised 06/21/05
 */
public class KochApp extends AbstractCalculation implements Drawable {
  DisplayFrame frame = new DisplayFrame("Koch Curve");
  int n = 0;

  public KochApp() {
    frame.setPreferredMinMax(-100, 600, -100, 600);
    frame.setSquareAspect(true);
    frame.addDrawable(this);
  }

  public void calculate() {
    n = control.getInt("Number of iterations");
    frame.setVisible(true);
  }

  public void iterate(double x1, double y1, double x2, double y2, int n, DrawingPanel panel, Graphics g) { // draw Koch curve using recursion
    if(n>0) {
      double dx = (x2-x1)/3;
      double dy = (y2-y1)/3;
      double xOneThird = x1+dx;   // new endpoint at 1/3 of line segment
      double yOneThird = y1+dy;
      double xTwoThird = x1+2*dx; // new endpoint at 2/3 of line segment
      double yTwoThird = y1+2*dy;
      // rotates line segment (dx, dy) by 60 degrees and adds to (xOneThird,yOneThird)
      double xMidPoint = (0.5*dx-0.866*dy+xOneThird);
      double yMidPoint = (0.5*dy+0.866*dx+yOneThird); // each line segment generates 4 new ones
      iterate(x1, y1, xOneThird, yOneThird, n-1, panel, g);
      iterate(xOneThird, yOneThird, xMidPoint, yMidPoint, n-1, panel, g);
      iterate(xMidPoint, yMidPoint, xTwoThird, yTwoThird, n-1, panel, g);
      iterate(xTwoThird, yTwoThird, x2, y2, n-1, panel, g);
    } else {
      int ix1 = panel.xToPix(x1);
      int iy1 = panel.yToPix(y1);
      int ix2 = panel.xToPix(x2);
      int iy2 = panel.yToPix(y2);
      g.drawLine(ix1, iy1, ix2, iy2);
    }
  }

  public void draw(DrawingPanel panel, Graphics g) {
    iterate(0, 0, 500, 0, n, panel, g);
  }

  public void reset() {
    control.setValue("Number of iterations", 3);
  }

  public static void main(String args[]) {
    CalculationControl.createApp(new KochApp());
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
