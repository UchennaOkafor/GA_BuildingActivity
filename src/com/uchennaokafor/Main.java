package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        final int POPULATION_SIZE = 100;
        final int MAX_GEN_NO_IMPROVEMENTS = 20;

        Population population = new Population(POPULATION_SIZE, true);
        Chromosome fittest = population.getFittest().deepClone();

        int generationCount = 1;
        int noImprovementCounter = 0;
        int fittestGeneration = generationCount;

        do {
            System.out.printf("Generation: %d Fittest: %d \n",
                generationCount, population.getFittest().getFitness());

            Population newPopulation = Algorithm.evolvePopulation(population);

            //Keeps the fittest
            if (population.getFittest().getFitness() >= fittest.getFitness()) {
                fittestGeneration = generationCount;
                fittest = population.getFittest().deepClone();
            }

            //Counts how many generations has passed with no improvements
            if (newPopulation.getFittest().getFitness() ==
                    population.getFittest().getFitness()) {
                noImprovementCounter++;
            } else {
                noImprovementCounter = 0;
            }

            generationCount++;
            population = newPopulation;

            //Terminates if there has been no improvements for n generations
        } while (noImprovementCounter < MAX_GEN_NO_IMPROVEMENTS);

        System.out.printf("\nThere has been no improvements for %d generations", MAX_GEN_NO_IMPROVEMENTS);
        System.out.printf("\nThe fittest chromosome was found in generation %d and has a fitness score of %d", fittestGeneration, fittest.getFitness());
        System.out.printf("\nThe fittest chromosome is: %s\n", fittest);
    }
}