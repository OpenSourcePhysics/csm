/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.genetic;

/**
 * Phenotype models a population of phenotypes using a random bond Ising model.
 *
 * The fitness of each member of the population is determined by computing the energy of the lattice of spins
 * corresponding to each member of the population.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class Phenotype {
  int L;
  int[][][] J; // random bonds
  int[] populationFitness, selectedPopulationFitness;
  int totalFitness;
  int highestEnergy;
  int bestFitness;

  public void initialize() {
    J = new int[L][L][2];
    highestEnergy = 2*L*L; // highest possible energy
    bestFitness = 0;
    for(int i = 0;i<L;i++) {
      for(int j = 0;j<L;j++) {
        for(int bond = 0;bond<2;bond++) {
          if(Math.random()>0.5) {
            J[i][j][bond] = 1;
          } else {
            J[i][j][bond] = -1;
          }
        }
      }
    }
  }

  public void determineFitness(GenePool genePool) {
    totalFitness = 0;
    int state[][] = new int[L][L];
    populationFitness = new int[genePool.numberOfGenotypes];
    for(int n = 0;n<genePool.numberOfGenotypes;n++) {
      for(int i = 0;i<L;i++) {
        for(int j = 0;j<L;j++) { // sets up lattice based on genotype
          int position = i+j*L;
          if(genePool.genotype[n][position]) {
            state[i][j] = 1;
          } else {
            state[i][j] = -1;
          }
        }
      }
      for(int i = 0;i<L;i++) {
        for(int j = 0;j<L;j++) { // compute energy of lattice configuration
          populationFitness[n] -= state[i][j]*(J[i][j][0]*state[(i+1)%L][j]+J[i][j][1]*state[i][(j+1)%L]);
        }
      }
      // define fitness to be positive and low energy -> high fitness
      populationFitness[n] = highestEnergy-populationFitness[n];
      totalFitness += populationFitness[n];
    }
  }

  public void select(GenePool genePool) {
    selectedPopulationFitness = new int[genePool.numberOfGenotypes];
    boolean savedGenotype[][] = new boolean[genePool.numberOfGenotypes][genePool.genotypeSize];
    for(int n = 0;n<genePool.numberOfGenotypes;n++) {
      genePool.copyGenotype(genePool.genotype[n], savedGenotype[n]);
    }
    for(int n = 0;n<genePool.populationNumber;n++) {
      /* use non-uniform probability distribution to select
      gene  proportional to fitness */
      int fitnessFraction = (int) (Math.random()*totalFitness);
      int choice = 0;
      int fitnessSum = populationFitness[0];
      while(fitnessSum<fitnessFraction) {
        choice++;
        fitnessSum += populationFitness[choice];
      }
      selectedPopulationFitness[n] = populationFitness[choice];
      if(selectedPopulationFitness[n]>bestFitness) {
        bestFitness = selectedPopulationFitness[n];
      }
      genePool.copyGenotype(savedGenotype[choice], genePool.genotype[n]);
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
