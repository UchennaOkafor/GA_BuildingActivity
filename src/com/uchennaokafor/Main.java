package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        // Create an initial population
        Population population = new Population(50, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;

        while (generationCount < 100) {
            generationCount++;
            System.out.println("Generation: "+ generationCount +" Fittest: "+ population.getFittest().getFitnessScore());
            population = Algorithm.evolvePopulation(population);
        }

        System.out.println("Generation: "+ generationCount);
        System.out.println("Genes:");
        System.out.println(population.getFittest());
    }
}