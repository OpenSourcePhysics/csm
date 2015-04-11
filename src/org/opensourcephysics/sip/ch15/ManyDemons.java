/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch15;
import java.awt.*;
import org.opensourcephysics.display.*;

/**
 *  ManyDemons simulation of thermal gradient using a demon at each site.
 *  The left end can add energy, the right end takes it out.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 07/05/05
 */
public class ManyDemons implements Drawable {
  public int[] s;
  public int N;
  public int systemEnergy;
  public int[] demonEnergy;
  public int mcs = 0; // number of MC moves per particle
  public double systemEnergyAccumulator = 0;
  public double[] demonEnergyAccumulator;
  public int magnetization = 0;
  public double[] mAccumulator;
  public int acceptedMoves = 0;
  public int timeToAddEnergy;

  public void initialize() {
    s = new int[N];
    demonEnergy = new int[N];
    demonEnergyAccumulator = new double[N];
    mAccumulator = new double[N];
    for(int i = 0;i<N;++i) {
      s[i] = 1; // all spins up
    }
    int tries = 0;
    int E = -N;
    magnetization = N;
    while((E<systemEnergy)&&(tries<10*N)) {
      int k = (int) (1+Math.random()*(N-2));
      int dE = 2*s[k]*(s[k+1]+s[k-1]);
      if(dE>0) {
        E += dE;
        s[k] = -s[k];
        magnetization += 2*s[k];
      }
      tries++;
    }
    System.out.println("Initial energy = "+E);
    systemEnergy = E;
    resetData();
  }

  public double temperature(int i) {
    return 4/Math.log(1.0+4.0/(demonEnergyAccumulator[i]/(mcs)));
  }

  public void resetData() {
    for(int i = 0;i<N;++i) {
      demonEnergyAccumulator[i] = 0;
      mAccumulator[i] = 0;
    }
    mcs = 0;
    systemEnergyAccumulator = 0;
    acceptedMoves = 0;
  }

  public void step() {
    for(int j = 1;j<N-1;++j) {
      int i = (int) (1+Math.random()*(N-2));
      int dE = 2*s[i]*(s[i+1]+s[i-1]);
      if(dE<=demonEnergy[i]) {
        s[i] = -s[i];
        acceptedMoves++;
        systemEnergy += dE;
        demonEnergy[i] -= dE;
        magnetization += 2*s[i];
      }
      systemEnergyAccumulator += systemEnergy;
    }
    for(int j = 1;j<N-1;++j) {
      demonEnergyAccumulator[j] += demonEnergy[j];
      mAccumulator[j] += s[j];
    }
    mcs++;
    if(mcs%timeToAddEnergy==0) {
      boundarySpins();
    }
  }

  public void boundarySpins() {
    /* attempt to add energy at spin 0 and remove it at spin N
   when spins 0 and 1 are aligned and spin N-2 and N-1 are not aligned */
    if((s[0]*s[1]==1)&&(s[N-2]*s[N-1]==-1)) {
      demonEnergyAccumulator[0] += 2;
      demonEnergyAccumulator[N-1] -= 2;
      s[0] = -s[0];
      s[N-1] = -s[N-1];
    }
  }

  public void draw(DrawingPanel panel, Graphics g) {
    if(s==null) {
      return;
    }
    g.setColor(Color.red);
    for(int i = 1;i<N-1;i++) {
      Arrow spin = new Arrow(i, 0, 0, 1.0*s[i]);
      spin.draw(panel, g);
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
