package com.uchennaokafor;

public class Algorithm {
    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getPopulationSize(), false);

        // Keep our best individual
        if (elitism) {
            newPopulation.setPermutationAt(0, pop.getFittest());
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
        for (int i = elitismOffset; i < pop.getPopulationSize(); i++) {
            Permutation p1 = tournamentSelection(pop);
            Permutation p2 = tournamentSelection(pop);
            Permutation newPermutation = crossover(p1, p2);
            newPopulation.setPermutationAt(i, newPermutation);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            //mutate(newPopulation.getIndividual(i));
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

    // Select individuals for crossover
    private static Permutation tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getPopulationSize());
            tournament.setPermutationAt(i, pop.getPermutationAt(randomId));
        }

        // Get the fittest
        return tournament.getFittest();
    }
}
