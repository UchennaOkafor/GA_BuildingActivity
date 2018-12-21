package com.uchennaokafor;

import java.util.*;

/**
 *
 */
public class Permutation {
    /**
     *
     */
    private Gene[] genes;
    private Random rand;
    private List<Integer> availableBuildings;
    private List<Integer> availableActivities;

    public Permutation() {
        initialize();
        generateGenes();
    }

    public Permutation(Gene[] genes) {
        initialize();
        this.genes = genes;
    }

    private void initialize() {
        this.rand = new Random();
        this.availableBuildings = new ArrayList<>();
        this.availableActivities = new ArrayList<>();

        int MAX_BUILDINGS = 8;
        int MAX_ACTIVITIES = 7;

        for (int i = 0; i < MAX_BUILDINGS - 1; i++) {
            availableBuildings.add(i);
        }

        for (int i = 0; i < MAX_ACTIVITIES - 1; i++) {
            availableActivities.add(i);
        }
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

    public void mutateGene(int index) {
        this.genes[index] = generateUniqueGene();
    }

    public void generateGenes() {
        List<Gene> genes = new ArrayList<>();

        do {
            Gene gene = generateUniqueGene();
            if (gene == null) {
                break;
            } else {
                genes.add(gene);
            }
        } while (true);

        this.genes = genes.toArray(new Gene[]{});
    }

    private Gene generateUniqueGene() {
        if (availableActivities.size() == 0 || availableBuildings.size() == 0) {
            return null;
        }

        int randBuildingIndex = this.rand.nextInt(availableBuildings.size());
        int randActivityIndex = this.rand.nextInt(availableActivities.size());

        int randBuilding = availableBuildings.remove(randBuildingIndex);
        int randActivity = availableActivities.remove(randActivityIndex);

        return new Gene(randActivity, randBuilding);
    }

    @Override
    public String toString() {
        return "Permutation {" +
                "genes=" + Arrays.toString(genes) +
                '}';
    }
}
