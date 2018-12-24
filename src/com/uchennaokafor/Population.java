package com.uchennaokafor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {

    private Permutation[] permutations;
    private int debugFitness;

    public Population(int size, boolean initialize) {
        this.permutations = new Permutation[size];

        if (initialize) {
            initializePopulation(size);
            updateFitness();
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
            if (fittest!= null && permutation != null &&
                    fittest.getFitnessScore() <= permutation.getFitnessScore()) {
                fittest = permutation;
            }
        }

        return fittest;
    }

    private void updateFitness() {
        debugFitness = getFittest().getFitnessScore();
    }

    public List<Permutation> getHalfTopFittest() {
        List<Permutation> permutationList = Arrays.asList(this.permutations);
        permutationList.sort(Comparator.comparingInt(Permutation::getFitnessScore));

        return permutationList.subList(0, permutationList.size() / 2);
    }

    public void setPermutationAt(int index, Permutation permutation) {
        this.permutations[index] = permutation;
        updateFitness();
    }

    public Permutation getPermutationAt(int index) {
        return this.permutations[index];
    }

    public int getPopulationSize() {
        return this.permutations.length;
    }
}