package com.uchennaokafor;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        final int TIME_TO_RUN_S = 60; //5 seconds
        final int POPULATION_SIZE = 8;
        final Instant START = Instant.now();

        // Create an initial population
        Population population = new Population(POPULATION_SIZE, true);
        Permutation best = population.getFittest();
        int generationCount = 1;
        Instant now;

        // Evolve our population until we reach a certain generation number
        do {
            System.out.printf("Generation: %d Fittest: %d \n", generationCount, population.getFittest().getFitnessScore());
            population = Algorithm.evolvePopulation(best.getFitnessScore(), population);
            if (population.getFittest().getFitnessScore() >= best.getFitnessScore()) {
                best = population.getFittest();
                System.out.println("Best: " + best);
            }
            now = Instant.now();
            System.out.println("Seconds elapsed: " + Duration.between(START, now).getSeconds());
            generationCount++;
        } while(Duration.between(START, now).getSeconds() <= TIME_TO_RUN_S);

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        System.out.printf("The fittest permutation is: %s \n", population.getFittest());
        System.out.printf("The fittest permutation has a score of: %d \n", population.getFittest().getFitnessScore());
    }
}