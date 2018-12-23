package com.uchennaokafor;

import java.util.*;
import java.util.stream.Stream;

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
        generateRandomGenes();
    }

    public Permutation(Gene[] genes) {
        initialize();
        this.genes = genes;

        for (Gene gene : this.genes) {
            availableActivities.remove(new Integer(gene.getActivity()));
            availableBuildings.remove(new Integer(gene.getBuilding()));
        }
    }

    private void initialize() {
        this.rand = new Random();
        this.availableBuildings = new ArrayList<>();
        this.availableActivities = new ArrayList<>();

        final int MAX_BUILDINGS = 8;
        final int MAX_ACTIVITIES = 7;

        for (int i = 0; i < MAX_BUILDINGS; i++) {
            availableBuildings.add(i);
        }

        for (int i = 0; i < MAX_ACTIVITIES; i++) {
            availableActivities.add(i);
        }

        Collections.shuffle(availableActivities);
        Collections.shuffle(availableBuildings);
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
        int newBuildingNo = this.availableBuildings.remove(0);
        int geneBuildingNo = this.genes[index].getBuilding();
        this.availableBuildings.add(geneBuildingNo);

        this.genes[index].setBuilding(newBuildingNo);
    }

    private void generateRandomGenes() {
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

    public boolean isPermutationValid() {
        for (Gene gene1 : this.genes) {
            for (Gene gene2 : this.genes) {
                if (gene1 != gene2) {
                    if (isIncompatible(gene1, gene2)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isIncompatible(Gene gene1, Gene gene2) {
        return gene1.getActivity() == gene2.getActivity() ||
                gene1.getBuilding() == gene2.getBuilding();
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
        return String.format("Permutation {Genes=%s}", Arrays.toString(genes));
    }
}
