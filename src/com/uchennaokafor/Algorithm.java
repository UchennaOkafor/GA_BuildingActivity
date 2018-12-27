package com.uchennaokafor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class Algorithm {
    /* GA parameters */
    private static final double MUTATION_RATE = 0.025;
    private static final boolean ELITISM = true;
    private static final Random RANDOM = new Random();

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop, int fittestScore, LocalDateTime endTime) {
        Population newPop = evolve(pop);

        while (fittestScore >= newPop.getFittest().getFitnessScore() &&
                ! LocalDateTime.now().isAfter(endTime)) {

            newPop = evolve(pop);
        }

        return newPop;
    }

    private static Population evolve(Population pop) {
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
        // Loop over the population size and create new individuals with crossover
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            Permutation offspring;

            do {
                Permutation p1 = tournamentSelection(pop);
                Permutation p2 = tournamentSelection(pop);
                offspring = crossover(p1, p2);

            } while (offspring == null);

            newPopulation.setPermutationAt(i, offspring);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getPermutationAt(i));
        }

        return newPopulation;
    }

    private static boolean isAccepted(Gene targetGene, List<Gene> chromosome) {
        for (Gene gene : chromosome) {
            if (gene.getBuilding() == targetGene.getBuilding()
                    || gene.getActivity() == targetGene.getActivity()) {
                return false;
            }
        }

        return true;
    }

    // Crossover individuals
    private static Permutation crossover(Permutation parent1, Permutation parent2) {
        int chromosomeLength = parent1.getGenes().length;
        //TODO, select random percentage of gene, e.g. 70%, and then see if you can take the remaining 30% from the other parent
        //Randomly choose which parent the 70% is taken from
        int amountToTake = (int) Math.ceil((chromosomeLength * 20f) / 100f);

        List<Gene> mainGenes = parent1.getGenesAtRandom(amountToTake);

        for (Gene gene : parent2.getGenes()) {
            if (isAccepted(gene, mainGenes)) {
                mainGenes.add(gene);
            }
        }

        if (mainGenes.size() == parent1.getGenes().length) {
            Permutation p = new Permutation(mainGenes.toArray(new Gene[] {}));

            if (p.isPermutationValid()) {
                return p;
            } else {
                //System.out.println("Invalid: " + p);
            }
        }

        return null;
    }

    // Mutate an individual
    private static void mutate(Permutation permutation) {
        // Loop through genes
        for (int i = 0; i < permutation.getGenes().length; i++) {
            if (Math.random() <= MUTATION_RATE) {
                // Create random gene
                permutation.mutateGene(i);
            }
        }
    }

    // Select individuals for crossover
    private static Permutation tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(pop.getPopulationSize() / 2, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < pop.getPopulationSize() / 2; i++) {
            int randomId = (int) (Math.random() * pop.getPopulationSize());
            tournament.setPermutationAt(i, pop.getPermutationAt(randomId));
        }

        // Get the fittest
        return tournament.getFittest();
    }
}
