package com.uchennaokafor;

import java.util.*;

public class Algorithm {
    private static final double UNIFORM_RATE = 0.5;
    private static final double MUTATION_RATE = 0.015;
    private static final boolean ELITISM = true;

    public static Population evolvePopulation(Population pop) {
        int populationSize = pop.getPopulationSize();
        Population newPopulation = new Population(populationSize, false);

        // Keep our best individual
        if (ELITISM) {
            newPopulation.setChromosome(0, pop.getFittest());
        }

        //Ternary operation
        int elitismOffset = ELITISM ? 1 : 0;

        // Loop over the population in steps of 2 and create new children with crossover
        for (int i = elitismOffset; i < populationSize; i+=2) {
            int[] parentIndexes = susSelection(pop, 2);

            Chromosome parent1 = pop.getChromosome(parentIndexes[0]);
            Chromosome parent2 = pop.getChromosome(parentIndexes[1]);
            Chromosome[] children = crossover(parent1, parent2);

            newPopulation.setChromosome(i, children[0]);
            //Checks if at the end of for loop
            if (i + 1 != populationSize) {
                newPopulation.setChromosome(i + 1, children[1]);
            }
        }

        // Mutate population
        for (int i = elitismOffset; i < populationSize; i++) {
            mutate(newPopulation.getChromosome(i));
        }

        return newPopulation;
    }

    private static Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        int genesLength = parent1.getGenes().length;

        //Creates a copy of parent genes as List
        List<Gene> parent1Genes = Arrays.asList(parent1.getGenes());
        List<Gene> parent2Genes = Arrays.asList(parent2.getGenes());

        //Sorts genes in asc ending order based on the activity value
        parent1Genes.sort(Comparator.comparingInt(Gene::getActivity));
        parent2Genes.sort(Comparator.comparingInt(Gene::getActivity));

        Gene[] child1Genes = new Gene[genesLength];
        Gene[] child2Genes = new Gene[genesLength];

        /*
          Uses the uniform crossover operator to select genes for crossover.
          The genes were previously sorted by the activity allele, which ensures that
          during crossover, each gene will have a unique activity alleles for each chromosome/individual
         */
        for (int i = 0; i < genesLength; i++) {
            if (Math.random() <= UNIFORM_RATE) {
                child1Genes[i] = parent1Genes.get(i).deepClone();
                child2Genes[i] = parent2Genes.get(i).deepClone();
            } else {
                child1Genes[i] = parent2Genes.get(i).deepClone();
                child2Genes[i] = parent1Genes.get(i).deepClone();
            }
        }

        return new Chromosome[] {
            new Chromosome(child1Genes),
            new Chromosome(child2Genes)
        };
    }

    private static void mutate(Chromosome chromosome) {
        // Loop through genes and randomly mutate a gene
        for (int i = 0; i < chromosome.getGenes().length; i++) {
            if (Math.random() <= MUTATION_RATE) {
                chromosome.mutateGene(i);
            }
        }
    }

    /**
     * Selects parents for crossover by using Stochastic Universal Sampling algorithm
     */
    private static int[] susSelection(Population pop, int amount) {
        double[] populationFitnessValues = new double[pop.getPopulationSize()];
        double totalFitnessSum = 0.0;

        for (int i = 0; i < pop.getPopulationSize(); i++) {
            double fitness = (double) pop.getChromosome(i).getFitness();

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