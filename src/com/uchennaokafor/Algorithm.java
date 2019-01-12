package com.uchennaokafor;

import java.util.*;

public class Algorithm {
    /* GA parameters */
    private static final double MUTATION_RATE = 0.015;
    private static final boolean ELITISM = true;
    private static Random RAND = new Random();

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        return evolve(pop);
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

        Double[] sortedWeights = getSortedWeights(pop);

        // Loop over the population size and create new individuals with crossover
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i+=2) {
            int[] parentIndexes = StochasticUniversalSampling.execute(sortedWeights, 2);
            Permutation parent1 = pop.getPermutationAt(parentIndexes[0]);
            Permutation parent2 = pop.getPermutationAt(parentIndexes[1]);

            Permutation[] offSprings = crossover(parent1, parent2);

            newPopulation.setPermutationAt(i, offSprings[0]);

            if (i + 1 != newPopulation.getPopulationSize()) {
                newPopulation.setPermutationAt(i + 1, offSprings[1]);
            }
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getPermutationAt(i));
        }

        return newPopulation;
    }

    private static Double[] getSortedWeights(Population pop) {
        List<Double> weights = new ArrayList<>();

        for (int j = 0; j < pop.getPopulationSize(); j++) {
            weights.add((double) pop.getPermutationAt(j).getFitnessScore());
        }

        weights.sort(Collections.reverseOrder());
        return weights.toArray(new Double[]{});
    }

    private static int[] generateCrossoverPoint(Permutation parent) {
        int amountOfGenes = parent.getGenes().length;
        int startIndex = RAND.nextInt(amountOfGenes / 2);
        int stopIndex, distance;

        do {
            stopIndex = RAND.nextInt(amountOfGenes);
            distance = stopIndex - startIndex;

        } while (distance <= 2 || distance >= 5);

        return new int[] {startIndex, stopIndex};
    }

    // Crossover individuals
    private static Permutation[] crossover(Permutation parent1, Permutation parent2) {
        Permutation child1 = crossoverPermutation(parent1, parent2);
        Permutation child2 = crossoverPermutation(parent2, parent1);

        return new Permutation[] {child1, child2};
    }

    private static Permutation crossoverPermutation(Permutation parent1, Permutation parent2) {
        int[] pointsParent1 = generateCrossoverPoint(parent1);

        int startIndex = pointsParent1[0];
        int stopIndex = pointsParent1[1];

        List<Gene> genes = new ArrayList<>();
        List<Gene> availableGenes = new ArrayList<>(Arrays.asList(parent2.getGenes()));

        for (int i = startIndex; i < stopIndex; i++) {
            Gene gene = parent1.getGene(i);
            genes.add(gene);
            availableGenes.removeIf(g -> g.getActivity() == gene.getActivity());
        }

        while (genes.size() != parent1.getGenes().length) {
            int randIndex = RAND.nextInt(availableGenes.size());
            genes.add(availableGenes.remove(randIndex));
        }

        return new Permutation(genes.toArray(new Gene[] {}));
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
}
