package com.uchennaokafor;

import sun.security.x509.GeneralName;

public class Algorithm {
    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }
        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Permutation crossover(Permutation parent1, Permutation parent2) {
        int size = parent1.getGenes().length;

        Gene[] genes = new Gene[size];
        for (int i = 0; i < size; i++) {
            Gene gene;

            // Crossover
            if (Math.random() <= uniformRate) {
                gene = parent1.getGene(i);
            } else {
                gene = parent2.getGene(i);
            }

            genes[i] = gene;
        }
        return new Permutation(genes);
    }

    // Mutate an individual
    private static void mutate(Permutation permutation) {
        // Loop through genes
        for (int i = 0; i < permutation.getGenes().length; i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene

                //Call a method to generate gene
                permutation.mutateGene(i, new Gene(1,1));
            }
        }
    }
}
