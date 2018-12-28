package com.uchennaokafor;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        final int POPULATION_SIZE = 500;
        int generationCount = 1;

        Population population = new Population(POPULATION_SIZE, true);
        Map<Integer, Permutation> map = new TreeMap<>();

        // Evolve our population until we reach a certain generation number
        do {
            population = Algorithm.evolvePopulation(population);
            map.put(generationCount, new Permutation(population.getFittest().getGenes()));

            System.out.printf("Generation: %d Fittest: %d \n",
                    generationCount, population.getFittest().getFitnessScore());

            generationCount++;

        } while(generationCount < 100);

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        Permutation best = map.values().stream().max(Comparator.comparingInt(Permutation::getFitnessScore)).get();

        System.out.printf("The fittest permutation is: %s \n", best);
        System.out.printf("The fittest permutation was found in generation %d and has a score of: %d \n", 1, best.getFitnessScore());
    }
}