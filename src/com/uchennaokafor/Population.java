package com.uchennaokafor;

public class Population {

    private Permutation[] permutations;

    public Population(int size, boolean initialize) {
        this.permutations = new Permutation[size];

        if (initialize) {
            initializePopulation(size);
        }
    }

    private void initializePopulation(int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            this.permutations[i] = new Permutation();
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
        return this.permutations.length;
    }
}