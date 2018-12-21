package com.uchennaokafor;

import java.util.Arrays;

/**
 *
 */
public class Permutation {
    /**
     *
     */
    private Gene[] genes;

    public Permutation(Gene[] genes) {
        this.genes = genes;
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

    public Gene[] getGenes() {
        return genes;
    }

    public Gene getGene(int index) {
        return genes[index];
    }

    public void mutateGene(int index, Gene newGene) {
        this.genes[index] = newGene;
    }

    @Override
    public String toString() {
        return "Permutation {" +
                "genes=" + Arrays.toString(genes) +
                '}';
    }
}
