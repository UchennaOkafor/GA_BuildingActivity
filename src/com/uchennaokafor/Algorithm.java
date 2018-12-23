package com.uchennaokafor;

public class Algorithm {
    /* GA parameters */
    private static final double UNIFORM_RATE = 0.5;
    private static final double MUTATION_RATE = 0.015;
    private static final int TOURNAMENT_SIZE = 10;
    private static final double CROSSOVER_RATE = 0.4;
    private static final boolean ELITISM = true;

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getPopulationSize(), false);

        // Keep our best individual
        if (ELITISM) {
            newPopulation.setPermutationAt(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (ELITISM) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.getPopulationSize(); i++) {
            Permutation offspring;

            do {
                Permutation p1 = tournamentSelection(pop);
                Permutation p2 = tournamentSelection(pop);
                offspring = crossover(p1, p2);

//                System.out.println("What");
//                System.out.println(p1);
//                System.out.println(p2);
                //break;

            } while (offspring == null);

            newPopulation.setPermutationAt(i, offspring);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getPermutationAt(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Permutation crossover(Permutation parent1, Permutation parent2) {
        int chromosomeLength = parent1.getGenes().length;

        //System.out.println("Parent 1: " + parent1);
        //System.out.println("Parent 2: " + parent2);

        Gene[] genes = new Gene[chromosomeLength];
        for (int i = 0; i < chromosomeLength; i++) {
            Gene gene;

            // Crossover
            if (Math.random() <= UNIFORM_RATE) {
                gene = parent1.getGene(i);
            } else {
                gene = parent2.getGene(i);
            }

//            if (isGeneCompatible(gene, genes)) {
//                return null;
//            }

            genes[i] = gene;
        }

        Permutation p = new Permutation(genes);

        if (p.isPermutationValid()) {
            return p;
        }

        return null;
    }

    // Mutate an individual
    private static void mutate(Permutation permutation) {
        // Loop through genes
        for (int i = 0; i < permutation.getGenes().length; i++) {
            if (Math.random() <= MUTATION_RATE) {
                // Create random gene
                //permutation.mutateGene(i);
            }
        }
    }

    // Select individuals for crossover
    private static Permutation tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(TOURNAMENT_SIZE, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * pop.getPopulationSize());
            tournament.setPermutationAt(i, pop.getPermutationAt(randomId));
        }

        // Get the fittest
        return tournament.getFittest();
    }
}
