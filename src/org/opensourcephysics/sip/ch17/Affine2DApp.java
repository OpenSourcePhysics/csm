/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import java.awt.*;
import java.awt.geom.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

/**
 * Affine2DApp demonstrates how to apply an affine transformation to a 2D shape.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class Affine2DApp extends AbstractCalculation {
  DisplayFrame frame = new DisplayFrame("2D Affine transformation");
  RectShape rect = new RectShape();

  /**
   * Performs a calculation by reading the transformation matrix and applying it to the shape.
   */
  public void calculate() {
    double[][] matrix = new double[3][]; // allocate 3 rows but not the row elements
    // set the first row of the matrix
    matrix[0] = (double[]) control.getObject("row 0"); // array element becomes the first row
    // set the second row
    matrix[1] = (double[]) control.getObject("row 1"); // array element becomes the second row
    // set the third row
    matrix[2] = (double[]) control.getObject("row 2"); // array element becomes the third row
    rect.transform(matrix);
  }

  /**
   * Resets the calculation by setting the transformation to the identity and
   * creating a default rectangle.
   */
  public void reset() {
    control.clearMessages();
    control.setValue("row 0", new double[] {1, 0, 0});
    control.setValue("row 1", new double[] {0, 1, 0});
    control.setValue("row 2", new double[] {0, 0, 1});
    rect = new RectShape();
    frame.clearDrawables();
    frame.addDrawable(rect);
    calculate();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    CalculationControl.createApp(new Affine2DApp());
  }

  /**
   * Inner class to create a drawable rectangle.
   */
  class RectShape implements Drawable { // inner class
    Shape shape = new Rectangle2D.Double(50, 50, 100, 100);

    /**
     * Draws the rectangle in a drawing panel.
     * @param panel
     * @param g
     */
    public void draw(DrawingPanel panel, Graphics g) {
      Graphics2D g2 = ((Graphics2D) g);
      g2.setPaint(Color.BLUE);
      g2.fill(shape);
      g2.setPaint(Color.RED);
      g2.draw(shape);
    }

    /**
     * Transforms the shape using the given matrix.
     * @param mat double[][]
     */
    public void transform(double[][] mat) {
      shape = (new AffineTransform(mat[0][0], mat[1][0], mat[0][1], mat[1][1], mat[0][2], mat[1][2])).createTransformedShape(shape);
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
