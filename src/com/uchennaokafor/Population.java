package com.uchennaokafor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Population {

    private Permutation[] permutations;
    private Random rand;

    public Population(int populationSize) {
        rand = new Random();
        permutations = new Permutation[populationSize];
        generatePopulation(populationSize);
    }

    private void generatePopulation(int populationSize) {
        int maxBuildings = 8 - 1;
        int maxActivities = 7 - 1;
        int chromosomeLength = maxBuildings + 1;

        for (int i = 0; i < populationSize; i++) {
            Gene[] genes = new Gene[chromosomeLength];
            Set<Integer> hashCodeSet = new HashSet<>();

            for (int j = 0; j < chromosomeLength; j++) {
                Gene gene;

                do {
                    int building = rand.nextInt(maxBuildings);
                    int activity = rand.nextInt(maxActivities);
                    gene = new Gene(activity, building);
                } while (hashCodeSet.contains(gene.hashCode()));

                hashCodeSet.add(gene.hashCode());
                genes[j] = gene;
            }

            permutations[i] = new Permutation(genes);
        }
    }

    public Permutation getFittest() {
        Permutation fittest = permutations[0];
        // Loop through individuals to find fittest
        for (Permutation permutation : permutations) {
            if (fittest.getFitnessScore() <= permutation.getFitnessScore()) {
                fittest = permutation;
            }
        }

        return fittest;
    }
}