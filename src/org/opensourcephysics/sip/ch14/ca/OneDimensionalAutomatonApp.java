/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.ca;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 *  OneDimensionalAutomatonApp takes the decimal representation of a rule as input and produces the rule array.
 *
 *  The rule array is computed in the updatemethod using periodic boundary conditions.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class OneDimensionalAutomatonApp extends AbstractCalculation {
  LatticeFrame automaton = new LatticeFrame("");
  int[] update = new int[8]; // update[] maps neighborhood configurations to 0 or 1

  public void calculate() {
    control.clearMessages();
    int L = control.getInt("Linear dimension");
    int tmax = control.getInt("Maximum time");
    automaton.resizeLattice(L, tmax); // default is lattice sites all zero
    // seed lattice by putting 1 in middle of first row
    automaton.setValue(L/2, 0, 1);
    // choose color of empty and occupied sites
    automaton.setIndexedColor(0, java.awt.Color.YELLOW); // empty
    automaton.setIndexedColor(1, java.awt.Color.BLUE);   // occupied
    setRule(control.getInt("Rule number"));
    for(int t = 1;t<tmax;t++) {
      iterate(t, L);
    }
  }

  public void iterate(int t, int L) {
    for(int i = 0;i<L;i++) {
      // read the neighborhood bits around index i, using periodic b.c's
      int left = automaton.getValue((i-1+L)%L, t-1);
      int center = automaton.getValue(i, t-1);
      int right = automaton.getValue((i+1)%L, t-1);
      // encode left, center, and right bits into one integer value
      // between 0 and 7
      int neighborhood = (left<<2)+(center<<1)+(right<<0);
      // update[neighborhood] gives the new site value for this neighborhood
      automaton.setValue(i, t, update[neighborhood]);
    }
  }

  public void setRule(int ruleNumber) {
    control.println("Rule = "+ruleNumber+"\n");
    control.println("111   110   101   100   011   010   001   000");
    for(int i = 7;i>=0;i--) {
      // (ruleNumber >>> i) shifts the contents of ruleNumber to the right by i
      // bits. In particular, the ith bit of ruleNumber resides in the rightmost
      // position of this expression. After "and"ing with the number 1, we are
      // left with either the number 0 or 1, depending on whether the ith
      // bit of ruleNumber was cleared or set.
      update[i] = ((ruleNumber>>>i)&1);
      control.print("  "+update[i]+"     ");
    }
    control.println();
  }

  public void reset() {
    control.setValue("Rule number", 90);
    control.setValue("Maximum time", 100);
    control.setValue("Linear dimension", 500);
  }

  public static void main(String args[]) {
    CalculationControl.createApp(new OneDimensionalAutomatonApp());
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
