/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.genetic;
import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.display.*;

/**
 * GeneticApp carries out the evolution of a genetic algorithm.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
*/
public class GenePool implements Drawable {
  int populationNumber;
  int numberOfGenotypes;
  int recombinationRate;
  int mutationRate;
  int genotypeSize;
  boolean[][] genotype;
  int generation = 0;
  Phenotype phenotype;

  public void initialize(Phenotype phenotype) {
    this.phenotype = phenotype;
    generation = 0;
    numberOfGenotypes = populationNumber+2*recombinationRate+mutationRate;
    genotype = new boolean[numberOfGenotypes][genotypeSize];
    for(int i = 0;i<populationNumber;i++) {
      for(int j = 0;j<genotypeSize;j++) {
        if(Math.random()>0.5) {
          genotype[i][j] = true;                       // sets genes randomly
        }
      }
    }
  }

  public void copyGenotype(boolean a[], boolean b[]) { // copy a to b
    for(int i = 0;i<genotypeSize;i++) {
      b[i] = a[i];
    }
  }

  public void recombine() {
    for(int r = 0;r<recombinationRate;r += 2) {
      int i = (int) (Math.random()*populationNumber); // chooses random genotype
      int j = 0;
      do {
        j = (int) (Math.random()*populationNumber); // chooses second random genotype
      } while(i==j);
      int size = 1+(int) (0.5*genotypeSize*Math.random()); // random size to recombine
      int startPosition = (int) (genotypeSize*Math.random()); // random location
      int r1 = populationNumber+r;   // index for new genotype
      int r2 = populationNumber+r+1; // index for second new genotype
      copyGenotype(genotype[i], genotype[r1]);
      copyGenotype(genotype[j], genotype[r2]);
      for(int position = startPosition;position<startPosition+size;position++) {
        int pbcPosition = position%genotypeSize;
        genotype[r1][pbcPosition] = genotype[j][pbcPosition]; // make new genotypes
        genotype[r2][pbcPosition] = genotype[i][pbcPosition];
      }
    }
  }

  public void mutate() {
    int index = populationNumber+2*recombinationRate; // index for new genotype
    for(int m = 0;m<mutationRate;m++) {
      int n = (int) (Math.random()*populationNumber); // choice random existing genotype
      int position = (int) (genotypeSize*Math.random()); // random position to mutate
      copyGenotype(genotype[n], genotype[index+m]);         // copy genotype
      genotype[index+m][position] = !genotype[n][position]; // mutate
    }
  }

  public void evolve() {
    recombine();
    mutate();
    generation++;
  }

  public void draw(DrawingPanel panel, Graphics g) {
    // draws genotype as string of red or green squares and lists fitness for each genotype
    if(genotype==null) {
      return;
    }
    if(phenotype.selectedPopulationFitness==null) {
      return;
    }
    int sizeX = Math.abs(panel.xToPix(0.8)-panel.xToPix(0));
    int sizeY = Math.abs(panel.yToPix(0.6)-panel.yToPix(0));
    for(int n = 0;n<populationNumber;n++) {
      int ypix = panel.yToPix(1.5*n)-sizeY;
      for(int position = 0;position<genotypeSize;position++) {
        if(genotype[n][position]) {
          g.setColor(Color.red);
        } else {
          g.setColor(Color.green);
        }
        int xpix = panel.xToPix(position)-sizeX;
        g.fillRect(xpix, ypix, sizeX, sizeY);
      }
      g.setColor(Color.black);
      g.drawString(String.valueOf(phenotype.selectedPopulationFitness[n]), panel.xToPix(genotypeSize+1), ypix+sizeY);
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
