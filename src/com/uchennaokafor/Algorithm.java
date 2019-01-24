package com.uchennaokafor;

import java.util.*;

public class Algorithm {
    // GA parameters
    private static final double UNIFORM_RATE = 0.5;
    private static final double MUTATION_RATE = 0.015;
    private static final boolean ELITISM = true;

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getPopulationSize(), false);

        // Keep our best individual
        if (ELITISM) {
            newPopulation.setPermutationAt(0, pop.getFittest());
        }

        int elitismOffset;
        if (ELITISM) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        // Loop over the population in steps of 2 and create new individuals with crossover
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i+=2) {
            int[] parentIndexes = susSelection(pop, 2);

            Permutation parent1 = pop.getPermutationAt(parentIndexes[0]);
            Permutation parent2 = pop.getPermutationAt(parentIndexes[1]);

            Permutation[] children = crossover(parent1, parent2);

            newPopulation.setPermutationAt(i, children[0]);

            if (i + 1 != newPopulation.getPopulationSize()) {
                newPopulation.setPermutationAt(i + 1, children[1]);
            }
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getPermutationAt(i));
        }

        return newPopulation;
    }

    private static Permutation[] crossover(Permutation parent1, Permutation parent2) {
        int genesLength = parent1.getGenes().length;

        List<Gene> parent1Genes = Arrays.asList(parent1.getGenes());
        List<Gene> parent2Genes = Arrays.asList(parent2.getGenes());

        //Sorts genes in ascending order based on the activity value
        parent1Genes.sort(Comparator.comparingInt(Gene::getActivity));
        parent2Genes.sort(Comparator.comparingInt(Gene::getActivity));

        Gene[] child1Genes = new Gene[genesLength];
        Gene[] child2Genes = new Gene[genesLength];

        //Uses uniform crossover to select genes for crossover
        for (int i = 0; i < genesLength; i++) {
            if (Math.random() <= UNIFORM_RATE) {
                child1Genes[i] = parent1Genes.get(i).deepClone();
                child2Genes[i] = parent2Genes.get(i).deepClone();
            } else {
                child1Genes[i] = parent2Genes.get(i).deepClone();
                child2Genes[i] = parent1Genes.get(i).deepClone();
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