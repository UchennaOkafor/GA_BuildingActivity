package com.uchennaokafor;

import java.util.*;

/**
 * Represents a chromosome
 */
public class Permutation {
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

    public Permutation(List<Gene> genes) {
        this(genes.toArray(new Gene[]{}));
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
        int randomGeneIndex = generateRandomGeneIndex(index);

        Gene gene = this.genes[index].deepClone();
        Gene randGene = this.genes[randomGeneIndex].deepClone();

        this.genes[index].setBuilding(randGene.getBuilding());
        this.genes[index].setActivity(gene.getActivity());

        this.genes[randomGeneIndex].setBuilding(gene.getBuilding());
        this.genes[randomGeneIndex].setActivity(randGene.getActivity());
    }

    private int generateRandomGeneIndex(int indexToExclude) {
        int randomIndex;

        do {
            randomIndex = this.rand.nextInt(this.genes.length);
        } while (randomIndex == indexToExclude);

        return randomIndex;
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

        return new Gene(randBuilding, randActivity);
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

    public Permutation deepClone() {
        Gene[] genesCopy = new Gene[this.genes.length];

        for (int i = 0; i < this.genes.length; i++) {
            genesCopy[i] = this.genes[i].deepClone();
        }

        return new Permutation(genesCopy);
    }

    @Override
    public String toString() {
        return String.format("Permutation {Genes=%s}", Arrays.toString(genes));
    }
}
