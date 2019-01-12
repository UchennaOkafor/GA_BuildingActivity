package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        int populationSize = 500;
        int maxGenerations = 200;
        int generationCount = 1;

        Population population = new Population(populationSize, true);

        int fittestGeneration = 0;
        Permutation fittest = population.getFittest().deepClone();

        // Evolve our population until we reach a certain generation number
        do {
            population = Algorithm.evolvePopulation(population);

            System.out.printf("Generation: %d Fittest: %d \n",
                    generationCount, population.getFittest().getFitnessScore());

            if (population.getFittest().getFitnessScore() >= fittest.getFitnessScore()) {
                fittestGeneration = generationCount;
                fittest = population.getFittest().deepClone();
            }

            generationCount++;
        } while(generationCount < maxGenerations);

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        System.out.printf("The fittest permutation was found in " +
                "generation %d and has a fitness score of: %d \n", fittestGeneration, fittest.getFitnessScore());
        System.out.printf("The fittest permutation is: %s \n", fittest);
    }
}