/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch08;
/*
 * There are many cases in which it is desirable to read and write particle phase
 * configurations.  For long running simulations, backup of intermediate configurations
 * prevents potential loss in the case of a computer crash.  Saved
 * configurations enable new data analysis without additional time consuming
 * simulation runs.  Difficult to generate equilibrium particle configurations
 * can be reused when they are available from a file.
 * The standard Java API has facilities for reading and writing files.
 * The most obvious way to save an MD phase configuration would be to use these and write
 * all positions and velocities simply as numbers directly into a file.  Additional
 * simulation parameters and configuration information would be stored using some custom
 * format.  Although it is the traditional approach for data storage, this has
 * the disadvantages of: possibility for programmer error, confusing code, and difficulty in
 * sharing data between programs.
 * An alternative is to use a more structured format for storing data.  The Open
 * Source Physics library has built in support for the Extensive Markup Language (XML),
 * which is rapidly becoming a universal standard.
 * XML also has the advantage that it is a human readable format; just by looking at an
 * XML file you may get some idea of what's inside.
 * The OSP XML facilities are available with the following classes:
 */

import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLControlElement;

public class ExampleXMLApp {
  public static void main(String[] args) {
    /*
     * XML documents are controlled through the XMLControl interface.  An empty XMLControl
     * can be created by instantiating an XMLControlElement:
     */
    XMLControl xmlOut = new XMLControlElement();
    /*
     * All data that we will store in XML will be referenced by tags, which are
     * unique names.  The OSP library allows us to easily read and write data corresponding
     * to its tags.  In the following method calls, the tag is the first parameter, and
     * the data to store is the second parameter.  Data that can be stored includes
     * numbers, number arrays, and strings.
     */
    xmlOut.setValue("comment", "An XML description of an array.");
    xmlOut.setValue("x positions", new double[] {1, 3, 4});
    xmlOut.setValue("x velocities", new double[] {0, -1, 1});
    /*
     * Once all data has been stored in the XMLControl object, it can be easily exported to a
     * file "particle_configuration.xml" by calling the write() method.
     */
    xmlOut.write("particle_configuration.xml");
    /*
     * A new XMLControl object can also be created with data initialized from a file.  In
     * this case, we'll immediately read from the file we just saved.  Note that we
     * are generating a new XMLControl object xmlIn.
     */
    XMLControl xmlIn = new XMLControlElement("particle_configuration.xml");
    /*
     * Our new XMLControl object xmlIn should nevertheless contain the same data as the
     * object we saved xmlOut.  Its data can be accessed by tag name, using the getString() and
     * getObject() methods.
     */
    System.out.println(xmlIn.getString("comment"));
    double[] xPos = (double[]) xmlIn.getObject("x positions");
    double[] xVel = (double[]) xmlIn.getObject("x velocities");
    for(int i = 0;i<xPos.length;i++) {
      System.out.println("x[i]="+xPos[i]+" vx[i]="+xVel[i]);
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
