/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import java.util.*;
import java.awt.event.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.display2d.*;
import org.opensourcephysics.frames.*;

/**
 * HuygensApp adds the field from one or more planar point sources.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class HuygensApp extends AbstractSimulation implements InteractiveMouseHandler {
  static final double PI2 = Math.PI*2;
  Scalar2DFrame frame = new Scalar2DFrame("x", "y", "Intensity from point sources");
  double time = 0;
  double[][] realPhasor, imagPhasor, amplitude;
  int n;    // grid points on a side
  double a; // side length

  /**
   * Constructs the HuygensApp
   */
  public HuygensApp() {
    // interpolated plot looks best
    frame.convertToInterpolatedPlot();
    frame.setPaletteType(ColorMapper.RED);
    frame.setInteractiveMouseHandler(this);
  }

  /**
   * Initializes the animation
   */
  public void initialize() {
    n = control.getInt("grid size");
    a = control.getDouble("length");
    frame.setPreferredMinMax(-a/2, a/2, -a/2, a/2);
    realPhasor = new double[n][n];
    imagPhasor = new double[n][n];
    amplitude = new double[n][n];
    frame.setAll(amplitude);
    initPhasors();
  }

  /**
   * Initializes the phasors
   */
  void initPhasors() {
    for(int ix = 0;ix<n;ix++) {
      for(int iy = 0;iy<n;iy++) {
        imagPhasor[ix][iy] = realPhasor[ix][iy] = 0; // zero the phasor
      }
    }
    // an iterator for the sources in the frame
    Iterator<Drawable> it = frame.getDrawables().iterator(); // source iterator
    int counter = 0;                               // counts the number of sources
    while(it.hasNext()) {
      InteractiveShape source = (InteractiveShape) it.next();
      counter++;
      double xs = source.getX(), ys = source.getY();
      for(int ix = 0;ix<n;ix++) {
        double x = frame.indexToX(ix);
        double dx = (xs-x);                                     // source->gridpoint
        for(int iy = 0;iy<n;iy++) {
          double y = frame.indexToY(iy);
          double dy = (ys-y);                                   // charge->gridpoint
          double r = Math.sqrt(dx*dx+dy*dy);
          realPhasor[ix][iy] += (r==0) ? 0 : Math.cos(PI2*r)/r; // real
          imagPhasor[ix][iy] += (r==0) ? 0 : Math.sin(PI2*r)/r; // imaginary
        }
      }
    }
    double cos = Math.cos(-PI2*time);
    double sin = Math.sin(-PI2*time);
    for(int ix = 0;ix<n;ix++) {
      for(int iy = 0;iy<n;iy++) {
        // only the real part of the complex field is physical
        double re = cos*realPhasor[ix][iy]-sin*imagPhasor[ix][iy];
        amplitude[ix][iy] = re*re;
      }
    }
    frame.setZRange(false, 0, 0.2*counter); // scale the intensity
    frame.setAll(amplitude);
  }

  /**
   * Resets the animation
   */
  public void reset() {
    time = 0;
    control.setValue("grid size", 128);
    control.setValue("length", 10);
    frame.clearDrawables();
    frame.setMessage("t = "+decimalFormat.format(time));
    control.println("Source button creates a new source.");
    control.println("Drag sources after they are created.");
    initialize();
  }

  /**
   * Creates a point source and adds it to the frame.
   */
  public void createSource() {
    InteractiveShape ishape = InteractiveShape.createCircle(0, 0, 0.5);
    frame.addDrawable(ishape);
    initPhasors();
    frame.repaint();
  }

  /**
   * Handles mouse actions by dragging the current interactive object and
   * reinitializing the phasors.
   *
   * @param panel
   * @param evt
   */
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt) {
    panel.handleMouseAction(panel, evt); // panel moves the source
    if(panel.getMouseAction()==InteractivePanel.MOUSE_DRAGGED) {
      initPhasors();
    }
  }

  /**
   * Does an animation step
   */
  protected void doStep() {
    time += 0.1;
    double cos = Math.cos(PI2*time);
    double sin = Math.sin(PI2*time);
    for(int ix = 0;ix<n;ix++) {
      for(int iy = 0;iy<n;iy++) {
        double re = cos*realPhasor[ix][iy]+sin*imagPhasor[ix][iy]; // real part
        amplitude[ix][iy] = re*re;
      }
    }
    frame.setAll(amplitude);
    frame.setMessage("t="+decimalFormat.format(time));
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    OSPControl control = SimulationControl.createApp(new HuygensApp());
    control.addButton("createSource", "Source");
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
