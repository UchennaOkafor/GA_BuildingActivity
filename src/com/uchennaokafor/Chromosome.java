package com.uchennaokafor;

import java.util.*;

public class Chromosome {
    private final int MAX_BUILDINGS = 8;
    private final int MAX_ACTIVITIES = 7;

    private Gene[] genes;
    private Random rand;

    /**
     * Initializes chromosome and generates random genes
     */
    public Chromosome() {
        this.rand = new Random();
        this.genes = generateRandomGenes();
    }

    /**
     * Initializes chromosome and assigns genes
     */
    public Chromosome(Gene[] genes) {
        this.rand = new Random();
        this.genes = genes;
    }

    /**
     * Generates an array of random activity/building association
     */
    private Gene[] generateRandomGenes() {
        Gene[] generatedGenes = new Gene[MAX_ACTIVITIES];

        for (int activity = 0; activity < MAX_ACTIVITIES; activity++) {
            int randBuilding = rand.nextInt(MAX_BUILDINGS);
            generatedGenes[activity] = new Gene(randBuilding, activity);
        }

        return generatedGenes;
    }

    /**
     * Mutates a gene using random resetting operation
     */
    public void mutateGene(int index) {
        int currentBuildingValue = genes[index].getBuilding();
        int randBuilding;

        //Makes sure that the randomly generated building value
        //is the same as the current gene's building value
        do  {
            randBuilding = rand.nextInt(MAX_BUILDINGS);
        } while (randBuilding == currentBuildingValue);

        genes[index].setBuilding(randBuilding);
    }

    /**
     * Checks the validity of the genes in the chromosome.
     * This is useful if the genes have been manually modified/assigned
     */
    public boolean isChromosomeValid() {
        if (genes.length != MAX_ACTIVITIES) {
            return false;
        }

        //Ensures that the same activity allele isn't assigned more than once
        for (Gene gene1 : genes) {
            for (Gene gene2 : genes) {
                if (gene1 != gene2 && gene1.getActivity() == gene2.getActivity()) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getFitness() {
        return FitnessCalc.getChromosomeFitness(this);
    }

    public Gene[] getGenes() {
        return genes;
    }

    /**
     * Constructs a new instance of the object with the same member values
     * to avoid a reference type bug, effectively cloning the object
     */
    public Chromosome deepClone() {
        Gene[] genesCopy = new Gene[genes.length];

        for (int i = 0; i < genes.length; i++) {
            genesCopy[i] = genes[i].deepClone();
        }

        return new Chromosome(genesCopy);
    }

    @Override
    public String toString() {
        return String.format("Chromosome {Genes=%s}", Arrays.toString(genes));
    }
}