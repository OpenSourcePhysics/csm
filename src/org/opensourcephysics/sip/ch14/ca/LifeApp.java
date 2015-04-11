/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.ca;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.controls.*;
import java.awt.Color;

/**
 * LifeApp implements the "Game of Life" invented by John Conway and popularized
 * by Martin Gardner in his Mathemtatical Recreations column in Scientific American. (October 1970)
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class LifeApp extends AbstractSimulation {
  LatticeFrame latticeFrame = new LatticeFrame("Game of Life");
  byte[][] newCells;
  int size = 16;

  /**
   * Constructs the LifeApp.
   */
  public LifeApp() {
    latticeFrame.setToggleOnClick(true, 0, 1);
    latticeFrame.setIndexedColor(0, Color.RED);
    latticeFrame.setIndexedColor(1, Color.BLUE);
  }

  /**
   * Initialzie the game of life.
   */
  public void initCells(int size) {
    this.size = size;
    newCells = new byte[size][size];
    latticeFrame.setAll(newCells, 0, size, 0, size);
    latticeFrame.setValue(size/2, size/2, 1);
    latticeFrame.setValue(size/2-1, size/2, 1);
    latticeFrame.setValue(size/2+1, size/2, 1);
    latticeFrame.setValue(size/2, size/2-1, 1);
    latticeFrame.setValue(size/2, size/2+1, 1);
  }

  /**
   * Clears all cells.
   */
  public void clear() {
    latticeFrame.setAll(new byte[size][size]);
    latticeFrame.repaint();
  }

  /**
   * Sets the default parameters in the control.
   */
  public void reset() {
    control.println("Click in drawingPanel to toggle life.");
    control.setValue("grid size", 16);
    initCells(16);
  }

  /**
   * Set the default parameters in the control.
   */
  public void initialize() {
    initCells(control.getInt("grid size"));
  }

  /**
   * Calculate the number of neighbors in a peridodic lattice.
   */
  private int calcNeighborsPeriodic(int row, int col) {
    int neighbors = -latticeFrame.getValue(row, col); // do not count self
    row += size; // add the size so that the mod operator works for row=0 and col=0
    col += size;
    for(int i = -1;i<=1;i++) {
      for(int j = -1;j<=1;j++) {
        neighbors += latticeFrame.getValue((row+i)%size, (col+j)%size);
      }
    }
    return neighbors;
  }

  /**
   * Step the Lattice by one generation.
   */
  public void doStep() {
    for(int i = 0;i<size;i++) {
      for(int j = 0;j<size;j++) {
        newCells[i][j] = 0;
      }
    }
    for(int i = 0;i<size;i++) {
      for(int j = 0;j<size;j++) {
        switch(calcNeighborsPeriodic(i, j)) {
        case 0 :
        case 1 :
          newCells[i][j] = 0;                                  // dies
          break;
        case 2 :
          newCells[i][j] = (byte) latticeFrame.getValue(i, j); // life goes on
          break;
        case 3 :
          newCells[i][j] = 1;                                  // condition for birth
          break;
        default :
          newCells[i][j] = 0;                                  // dies of overcrowding if >3
        }
      }
    }
    latticeFrame.setAll(newCells);
  }

  /* --------------- application target  --------------- */
  public static void main(String[] args) {
    OSPControl control = SimulationControl.createApp(new LifeApp());
    control.addButton("clear", "Clear"); // optional custom action
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
