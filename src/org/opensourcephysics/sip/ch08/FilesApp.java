/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch08;
// gets needed classes, * means get all classes in controls folder or subdirectory

import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import java.io.*;

public class FilesApp extends AbstractCalculation {
  DisplayFrame display = new DisplayFrame("", "", "");

  public void calculate() { // Does a calculation
    readFile();
  }

  public static void main(String[] args) {
    // Create a calculation control structure using this class
    CalculationControl.createApp(new FilesApp());
  }

  public void readFile() {
    File file = GUIUtils.showOpenDialog(display);
    String dir = file.getParent();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String s = null;
      while((s = br.readLine())!=null) {
        if(s.equals("// start break")) {
          s = br.readLine();
          s = s.substring(3)+".java";
          File outfile = new File(dir, s);
          FileWriter fw = new FileWriter(outfile);
          PrintWriter pw = new PrintWriter(fw);
          s = br.readLine();
          while(s.equals("// end break")==false) {
            pw.println(s);
            s = br.readLine();
          }
          pw.close();
        }
      }
      br.close();
    } catch(IOException e) {}
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
