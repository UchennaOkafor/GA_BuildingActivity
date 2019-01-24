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

        // Loop over the population size and create new individuals with crossover
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i+=2) {
            int[] parentIndexes = susSelection(pop, 2);

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

    private static Permutation[] crossover(Permutation parent1, Permutation parent2) {
        int[] pointsParent1 = generateCrossoverPoint(parent1);
        int genesSize = parent1.getGenes().length;
        int startIndex = pointsParent1[0];
        int stopIndex = pointsParent1[1];

        List<Gene> parent1Genes = new ArrayList<>(Arrays.asList(parent1.getGenes()));
        List<Gene> parent2Genes = new ArrayList<>(Arrays.asList(parent2.getGenes()));

        List<Gene> child1Genes = new ArrayList<>(parent1Genes.subList(startIndex, stopIndex));
        parent1Genes.subList(startIndex, stopIndex).clear();
        List<Gene> child2Genes = new ArrayList<>(parent1Genes);

        for (Gene gene : parent2Genes) {
            if (child1Genes.size() == genesSize && child2Genes.size() == genesSize) {
                break;
            }

            if (child1Genes.stream().noneMatch(
                    g -> g.getActivity() == gene.getActivity())) {

                child1Genes.add(gene.deepClone());
            }

            if (child2Genes.stream().noneMatch(
                    g -> g.getActivity() == gene.getActivity())) {

                child2Genes.add(gene.deepClone());
            }
        }

        return new Permutation[] {
            new Permutation(child1Genes),
            new Permutation(child2Genes)
        };
    }


    private static void mutate(Permutation permutation) {
        // Loop through genes and randomly mutate a gene
        for (int i = 0; i < permutation.getGenes().length; i++) {
            if (Math.random() <= MUTATION_RATE) {
                permutation.mutateGene(i);
            }
        }
    }

    //Selects parents for crossover by using Stochastic Universal Sampling algorithm
    private static int[] susSelection(Population pop, int amount) {
        double[] populationFitnessValues = new double[pop.getPopulationSize()];
        double totalFitnessSum = 0.0;

        for (int i = 0; i < pop.getPopulationSize(); i++) {
            double fitness = (double) pop.getPermutationAt(i).getFitnessScore();

            populationFitnessValues[i] = fitness;
            totalFitnessSum += fitness;
        }

        double pointerDistance = totalFitnessSum / amount;
        double start = Math.random() * pointerDistance;

        int[] individualIndexes = new int[amount];

        for (int i = 0; i < amount; i++) {
            double pointer = start + (i * pointerDistance);
            double partialSum = 0;

            for (int j = 0; j < populationFitnessValues.length; j++) {
                partialSum += populationFitnessValues[j];
                if (partialSum >= pointer) {
                    individualIndexes[i] = j;
                    break;
                }
            }
        }

        return individualIndexes;
    }
}