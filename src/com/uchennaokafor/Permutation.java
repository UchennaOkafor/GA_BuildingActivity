package com.uchennaokafor;

import java.util.*;

/**
 * Represents a chromosome
 */
public class Permutation {
    /**
     *
     */
    private final int MAX_BUILDINGS = 8;
    private final int MAX_ACTIVITIES = 7;

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

        for (int i = 0; i < MAX_BUILDINGS; i++) {
            availableBuildings.add(i);
        }

        for (int i = 0; i < MAX_ACTIVITIES; i++) {
            availableActivities.add(i);
        }

        Collections.shuffle(availableActivities, this.rand);
        Collections.shuffle(availableBuildings, this.rand);
    }

    public void mutateGene(int index) {
        int anotherRandomIndex = randomInt(index);

        Gene gene = new Gene(this.genes[index].getActivity(), this.genes[index].getBuilding());
        Gene randGene = new Gene(this.genes[anotherRandomIndex].getActivity(), this.genes[anotherRandomIndex].getBuilding());

        this.genes[index].setBuilding(randGene.getBuilding());
        this.genes[index].setActivity(gene.getActivity());

        this.genes[anotherRandomIndex].setBuilding(gene.getBuilding());
        this.genes[anotherRandomIndex].setActivity(randGene.getActivity());
    }

    private int randomInt(int index) {
        int a;

        do {
            a = this.rand.nextInt(this.genes.length);
        } while (a == index);

        return a;
    }

    private void generateRandomGenes() {
        List<Gene> chromosome = new ArrayList<>();
        Gene gene;

        do {
            gene = generateUniqueGene();
            if (gene != null) {
                chromosome.add(gene);
            }
        } while (gene != null);

        this.genes = chromosome.toArray(new Gene[]{});
    }

    public boolean isPermutationValid() {
        if (this.genes.length != MAX_ACTIVITIES) {
            return false;
        }

        for (Gene gene1 : this.genes) {
            for (Gene gene2 : this.genes) {
                if (gene1 != gene2 && gene1.getActivity() == gene2.getActivity()) {
                    return false;
                }
            }
        }

        return true;
    }

    private Gene generateUniqueGene() {
        if (availableActivities.size() == 0) {
            return null;
        }

        int randBuildingIndex = this.rand.nextInt(availableBuildings.size());
        int randActivityIndex = this.rand.nextInt(availableActivities.size());

        int randBuilding = availableBuildings.get(randBuildingIndex);
        int randActivity = availableActivities.remove(randActivityIndex);

        return new Gene(randActivity, randBuilding);
    }

    public int getFitnessScore() {
        int score = 0;

        for (Gene gene : this.genes) {
            score += FitnessCalc.getGeneScore(gene);
        }

        return score;
    }

    public Gene[] getGenes() {
        return this.genes;
    }

    public Gene getGene(int index) {
        return this.genes[index];
    }

    @Override
    public String toString() {
        return String.format("Permutation {Genes=%s}", Arrays.toString(genes));
    }
}
