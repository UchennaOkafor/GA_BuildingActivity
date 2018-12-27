package com.uchennaokafor;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        final int SIMULATION_DURATION = 120; //5 seconds
        final int POPULATION_SIZE = 50;

        // Create an initial population
        Population population = new Population(POPULATION_SIZE, true);
        runSimulation(population, SIMULATION_DURATION);
    }

    private static void runSimulation(Population pop, long simulationDuration) {
        int generationCount = 1;
        Permutation best = pop.getFittest();
        final LocalDateTime FINISH_TIME
                = LocalDateTime.now().plusSeconds(simulationDuration);

        // Evolve our population until we reach a certain generation number
        do {
            System.out.printf("Generation: %d Fittest: %d \n", generationCount, pop.getFittest().getFitnessScore());
            pop = Algorithm.evolvePopulation(pop, best.getFitnessScore(), FINISH_TIME);
            if (pop.getFittest().getFitnessScore() >= best.getFitnessScore()) {
                best = pop.getFittest();
            }
            generationCount++;
        } while(! LocalDateTime.now().isAfter(FINISH_TIME));

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        System.out.printf("The fittest permutation is: %s \n", pop.getFittest());
        System.out.printf("The fittest permutation has a score of: %d \n", pop.getFittest().getFitnessScore());
    }
}