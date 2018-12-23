package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        final int MAX_GENERATIONS = 50;
        final int POPULATION_SIZE = 100;

        // Create an initial population
        Population population = new Population(POPULATION_SIZE, true);
        int generationCount = 1;

        // Evolve our population until we reach a certain generation number
        do {
            System.out.printf("Generation: %d Fittest: %d \n", generationCount, population.getFittest().getFitnessScore());
            population = Algorithm.evolvePopulation(population);
        } while(generationCount++ < MAX_GENERATIONS);

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        System.out.printf("The fittest permutation is: %s \n", population.getFittest());
        System.out.printf("The fittest permutation has a score of: %d \n", population.getFittest().getFitnessScore());
    }
}