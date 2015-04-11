/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch14.genetic;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
 * GeneticApp simulates and displays a genetic algorithm such GenePool.
 *
 * @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
 * @version 1.0  revised 06/24/05
 */
public class GeneticApp extends AbstractSimulation {
  GenePool genePool = new GenePool();
  Phenotype phenotype = new Phenotype();
  DisplayFrame frame = new DisplayFrame("Gene pool");

  public void initialize() {
    phenotype.L = control.getInt("Lattice size");
    genePool.populationNumber = control.getInt("Population size");
    genePool.recombinationRate = control.getInt("Recombination rate");
    genePool.mutationRate = control.getInt("Mutation rate");
    genePool.genotypeSize = phenotype.L*phenotype.L;
    genePool.initialize(phenotype);
    phenotype.initialize();
    frame.addDrawable(genePool);
    frame.setPreferredMinMax(-1.0, genePool.genotypeSize+5, -1.0, genePool.populationNumber+2);
    frame.setSize(phenotype.L*phenotype.L*10, genePool.populationNumber*20);
  }

  public void doStep() {
    genePool.evolve();
    phenotype.determineFitness(genePool);
    phenotype.select(genePool);
    control.clearMessages();
    control.println(genePool.generation+" generations, best fitness = "+phenotype.bestFitness);
  }

  public void reset() {
    control.setValue("Lattice size", 8);
    control.setValue("Population size", 20);
    control.setValue("Recombination rate", 10);
    control.setValue("Mutation rate", 4);
  }

  public static void main(String args[]) {
    SimulationControl.createApp(new GeneticApp());
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
