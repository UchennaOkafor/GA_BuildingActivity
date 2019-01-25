package com.uchennaokafor;

public class Population {

    private Chromosome[] chromosomes;

    public Population(int size, boolean initialize) {
        this.chromosomes = new Chromosome[size];

        if (initialize) {
            initializePopulation(size);
        }
    }

    private void initializePopulation(int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            //Creates a random chromosome
            this.chromosomes[i] = new Chromosome();
        }
    }

    public Chromosome getFittest() {
        Chromosome fittest = this.chromosomes[0];

        // Loop through chromosomes to find fittest
        for (Chromosome chromosome : this.chromosomes) {
            if (fittest.getFitness() <= chromosome.getFitness()) {
                fittest = chromosome;
            }
        }

        return fittest;
    }

    public void setChromosome(int index, Chromosome chromosome) {
        this.chromosomes[index] = chromosome;
    }

    public Chromosome getChromosome(int index) {
        return this.chromosomes[index];
    }

    public int getPopulationSize() {
        return this.chromosomes.length;
    }
}