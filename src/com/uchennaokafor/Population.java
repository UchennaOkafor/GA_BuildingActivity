package com.uchennaokafor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Population {

    private int size;
    private Permutation[] permutations;
    private Random rand;

    public Population(int size, boolean initialize) {
        this.rand = new Random();
        this.size = size;
        this.permutations = new Permutation[size];

        if (initialize) {
            initializePopulation(size);
        }
    }

    private void initializePopulation(int populationSize) {
        int maxBuildings = 8 - 1;
        int maxActivities = 7 - 1;
        int chromosomeLength = maxBuildings + 1;

        for (int i = 0; i < populationSize; i++) {
            Gene[] genes = new Gene[chromosomeLength];
            Set<Integer> hashCodeSet = new HashSet<>();

            for (int j = 0; j < chromosomeLength; j++) {
                Gene gene;

                do {
                    int building = this.rand.nextInt(maxBuildings);
                    int activity = this.rand.nextInt(maxActivities);
                    gene = new Gene(activity, building);
                } while (hashCodeSet.contains(gene.hashCode()));

                hashCodeSet.add(gene.hashCode());
                genes[j] = gene;
            }

            this.permutations[i] = new Permutation(genes);
        }
    }

    public Permutation getFittest() {
        Permutation fittest = this.permutations[0];
        // Loop through individuals to find fittest
        for (Permutation permutation : this.permutations) {
            if (fittest.getFitnessScore() <= permutation.getFitnessScore()) {
                fittest = permutation;
            }
        }

        return fittest;
    }

    public void setPermutationAt(int index, Permutation permutation) {
        this.permutations[index] = permutation;
    }

    public Permutation getPermutationAt(int index) {
        return this.permutations[index];
    }

    public int getPopulationSize() {
        return size;
    }
}