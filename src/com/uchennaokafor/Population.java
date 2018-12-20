package com.uchennaokafor;

import java.util.Random;

public class Population {

    private Permutation[] permutations;
    private Random rand;

    public Population() {
        rand = new Random();
        generatePopulation();
    }

    public void generatePopulation() {
        int maxBuildings = 8 - 1;
        int maxActivities = 7 - 1;

        for (int i = 0; i < maxBuildings; i++) {
            for (int j = 0; j < maxActivities; j++) {
                rand.nextInt(8);
            }
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