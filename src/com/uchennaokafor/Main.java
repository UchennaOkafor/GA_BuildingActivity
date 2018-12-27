package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        int generationCount = 1;
        final int POPULATION_SIZE = 5000;
        Population population = new Population(POPULATION_SIZE, true);
        Permutation best = population.getFittest();

        // Evolve our population until we reach a certain generation number
        do {
            System.out.printf("Generation: %d Fittest: %d \n", generationCount, population.getFittest().getFitnessScore());
            population = Algorithm.evolvePopulation(population);
            if (population.getFittest().getFitnessScore() >= best.getFitnessScore()) {
                best = population.getFittest();
            }
            generationCount++;
        } while(generationCount < 100);

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        System.out.printf("The fittest permutation is: %s \n", population.getFittest());
        System.out.printf("The fittest permutation has a score of: %d \n", population.getFittest().getFitnessScore());
    }
}