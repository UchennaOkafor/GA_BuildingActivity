package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        int populationSize = 200;
        int maxGenerations = 100;
        int generationCount = 1;

        Population population = new Population(populationSize, true);

        int fittestGeneration = 0;
        Chromosome fittest = population.getFittest().deepClone();

        // Evolve our population until we reach a certain generation number
        do {
            population = Algorithm.evolvePopulation(population);

            System.out.printf("Generation: %d Fittest: %d \n",
                    generationCount, population.getFittest().getFitness());

            if (population.getFittest().getFitness() >= fittest.getFitness()) {
                fittestGeneration = generationCount;
                fittest = population.getFittest().deepClone();
            }

            generationCount++;
        } while(generationCount < maxGenerations);

        System.out.printf("\nFinal Generation: %d \n", generationCount);
        System.out.printf("The fittest chromosome was found in " +
                "generation %d and has a fitness score of: %d \n", fittestGeneration, fittest.getFitness());
        System.out.printf("The fittest chromosome is: %s \n", fittest);
    }
}