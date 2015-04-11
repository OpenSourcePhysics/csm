/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch19;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.*;
import java.awt.*;

/**

 * GalaxyApp models evolution of a galaxy using percolation ideas

 * Model proposed by Schulman and Seiden

 * @version 1.0

 * @author J. Tobochnik  3/25/05

 *

 */
public class GalaxyApp extends AbstractSimulation implements Drawable {
  DisplayFrame frame = new DisplayFrame("Galaxy");
  final static double twoPi = 2.0*Math.PI;
  final static double twoPiOver6 = twoPi/6;
  double p, v, dt, t;
  int numberOfRings, numberOfActiveCells, numberOfCells;
  int[] starLifeTime, cellR, cellA, activeCellLabel;

  public GalaxyApp() {
    frame.addDrawable(this);
  }

  public void initialize() {
    t = 0;
    numberOfRings = control.getInt("Number of rings");
    numberOfActiveCells = control.getInt("Initial number of active cells");
    p = control.getDouble("star formation probability");
    v = control.getDouble("cell velocity");
    dt = control.getDouble("time step");
    frame.setPreferredMinMax(-numberOfRings-1.0, numberOfRings+1.0, -numberOfRings-1.0, numberOfRings+1.0);
    numberOfCells = 3*numberOfRings*(numberOfRings+1);
    cellR = new int[numberOfCells];
    cellA = new int[numberOfCells];
    int cellLabel = 0;
    // initial values of r and a for each cell label
    for(int r = 1;r<=numberOfRings;r++) {
      for(int a = 0;a<r*6;a++) {
        cellR[cellLabel] = r;
        cellA[cellLabel] = a;
        cellLabel++;
      }
    }
    starLifeTime = new int[numberOfCells];
    activeCellLabel = new int[numberOfCells];
    initializeGalaxy();
  }

  public void initializeGalaxy() {
    int i = 0;
    while(i<numberOfActiveCells) {
      int label = (int) (Math.random()*numberOfCells);
      if(starLifeTime[label]!=15) {
        starLifeTime[label] = 15; // activate region for 15 time steps
        activeCellLabel[i] = label;
        i++;
      }
    }
  }

  public void formNewStars() {
    // copy list of active cells into another array
    int[] currentActiveCellLabel = activeCellLabel.clone();
    int currentNumberOfActiveCells = numberOfActiveCells;
    numberOfActiveCells = 0;
    for(int i = 0;i<currentNumberOfActiveCells;++i) {
      int cellLabel = currentActiveCellLabel[i];
      int r = cellR[cellLabel];
      int a = cellA[cellLabel];
      // activate neighboring cells in same ring
      createStars(r, pbc(a+1, r));
      createStars(r, pbc(a-1, r));
      // activate cells in next larger ring
      if(r<numberOfRings-1) {
        int ap = aForOtherRadius(a, r, r+1);
        createStars(r+1, pbc(ap, r+1));
        createStars(r+1, pbc(ap+1, r+1));
      }
      // activate cells in next smaller ring
      if(r>1) {
        int am = aForOtherRadius(a, r, r-1);
        createStars(r-1, pbc(am, r-1));
        createStars(r-1, pbc(am+1, r-1));
      }
    }
  }

  public int pbc(int a, int r) {
    return(a+6*r)%(6*r);
  }

  public int aForOtherRadius(int a, int r, int rOther) {
    // angle corresponding to a at time t
    double angle = twoPiOver6*a/r+((v*t)/r);
    angle -= twoPi*(int) (angle/twoPi);
    // change in angle for cell at other r
    double angleChange = ((v*t)/rOther);
    angleChange -= twoPi*(int) (angleChange/twoPi);
    // return value of a for cell at other r
    return(int) ((rOther/twoPiOver6)*(angle-angleChange));
  }

  public void createStars(int r, int a) {
    int label = a+3*r*(r-1);
    if((Math.random()<p)&&(starLifeTime[label]!=15)) {
      activeCellLabel[numberOfActiveCells] = label;
      numberOfActiveCells++;
      starLifeTime[label] = 15;
    }
  }

  public void doStep() {
    t += dt;
    formNewStars();
    frame.setMessage("t = "+decimalFormat.format(t)+" #active = "+numberOfActiveCells);
  }

  public void reset() {
    control.setValue("Number of rings", 50);
    control.setValue("Initial number of active cells", 200);
    control.setValue("star formation probability", 0.18);
    control.setValue("cell velocity", 1.0);
    control.setValue("time step", 10.0);
  }

  public void draw(DrawingPanel panel, Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, panel.getWidth(), panel.getHeight()); // color background black
    g.setColor(Color.yellow);
    for(int label = 0;label<numberOfCells;label++) {
      if(starLifeTime[label]>0) {
        int r = cellR[label];
        int a = cellA[label];
        double angle = twoPiOver6*a/r+(v*t)/r; // angle for cell at time t
        double x = r*Math.cos(angle);
        double y = r*Math.sin(angle);
        double ds = starLifeTime[label]/15.0;
        int leftPixels = panel.xToPix(x);
        int topPixels = panel.yToPix(y);
        int heightPixels = (int) (panel.getYPixPerUnit()*ds);
        int widthPixels = (int) (panel.getXPixPerUnit()*ds);
        g.fillOval(leftPixels, topPixels, widthPixels, heightPixels);
        starLifeTime[label]--;                 // decrease star cluster lifetime
      }
    }
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new GalaxyApp());
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
