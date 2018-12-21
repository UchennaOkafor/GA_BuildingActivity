package com.uchennaokafor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 */
public class Permutation {
    /**
     *
     */
    private Gene[] genes;
    private Random rand;
    private Set<Integer> hashCodeSet;

    private final int MAX_BUILDINGS = 8 - 1;
    private final int MAX_ACTIVITIES = 7 - 1;
    private final int CHROMOSOME_LENGTH = MAX_BUILDINGS + 1;

    public Permutation() {
        this.rand = new Random();
        this.hashCodeSet = new HashSet<>();
        generateGenes();
    }

    public Permutation(Gene[] genes) {
        this.genes = genes;
        this.rand = new Random();
        this.hashCodeSet = new HashSet<>();
    }

    /**
     *
     * @return
     */
    public int getFitnessScore() {
        int score = 0;

        for (Gene gene : genes) {
            score += FitnessCalc.getGeneScore(gene);
        }

        return score;
    }

    public boolean isGeneExist(Gene gene) {
        return hashCodeSet.contains(gene.hashCode());
    }

    public Gene[] getGenes() {
        return genes;
    }

    public Gene getGene(int index) {
        return genes[index];
    }

    public void mutateGene(int index) {
        this.genes[index] = generateUniqueGene();
    }

    public void generateGenes() {
        Gene[] genes = new Gene[CHROMOSOME_LENGTH];

        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            genes[i] = generateUniqueGene();
        }

        this.genes = genes;
    }

    private Gene generateUniqueGene() {
        Gene gene;

        do {
            int building = this.rand.nextInt(MAX_BUILDINGS);
            int activity = this.rand.nextInt(MAX_ACTIVITIES);
            gene = new Gene(activity, building);
        } while (this.hashCodeSet.contains(gene.hashCode()));

        this.hashCodeSet.add(gene.hashCode());
        return gene;
    }

    @Override
    public String toString() {
        return "Permutation {" +
                "genes=" + Arrays.toString(genes) +
                '}';
    }
}
