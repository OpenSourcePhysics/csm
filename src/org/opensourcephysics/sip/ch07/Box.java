/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch07;
import java.awt.*;
import org.opensourcephysics.display.*;

/**
 * Box contains data for articles in a partitioned box.
 *
 * @author Jan Tobochnik, Wolfgang Christian,  Harvey Gould
 * @version 1.0  revised 04/21/05
 */
public class Box implements Drawable {
  public double x[], y[];
  public int N, nleft, time;

  /**
   * Initializes the box, places particles in random positions on left side.
   */
  public void initialize() {
    x = new double[N]; // location of particles (for visualization purposes only)
    y = new double[N];
    nleft = N; // start with all particles on the left
    time = 0;
    for(int i = 0;i<N;i++) {
      x[i] = 0.5*Math.random(); // needed only for visualization
      y[i] = Math.random();
    }
  }

  /**
   * Moves one particle to the other side
   */
  public void step() {
    int i = (int) (Math.random()*N);
    if(x[i]<0.5) {
      nleft--; // move to right
      x[i] = 0.5*(1+Math.random());
      y[i] = Math.random();
    } else {
      nleft++; // move to left
      x[i] = 0.5*Math.random();
      y[i] = Math.random();
    }
    time++;
  }

  /**
   * Draws particles and partitioned box
   */
  public void draw(DrawingPanel panel, Graphics g) {
    if(x==null) {
      return;
    }
    int size = 2;
    int xMiddle = panel.xToPix(0.5); // position of partition in middle of box
    g.setColor(Color.black);
    g.drawLine(xMiddle, panel.yToPix(0), xMiddle, panel.yToPix(0.45));
    g.drawLine(xMiddle, panel.yToPix(0.55), xMiddle, panel.yToPix(1.0));
    g.setColor(Color.red);
    for(int i = 0;i<N;i++) {
      int xpix = panel.xToPix(x[i]);
      int ypix = panel.yToPix(y[i]);
      g.fillOval(xpix, ypix, size, size);
    }
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
