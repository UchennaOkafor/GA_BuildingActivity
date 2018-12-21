package com.uchennaokafor;

public class Main {

    public static void main(String[] args) {
        // Create an initial population
        Population population = new Population(100, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;

        do {
            System.out.println("Generation: "+ generationCount +" Fittest: "+ population.getFittest().getFitnessScore());
            population = Algorithm.evolvePopulation(population);
        } while(++generationCount < 50);

        System.out.println("Generation: "+ generationCount);
        System.out.println("Genes:");
        System.out.println(population.getFittest());
    }
}