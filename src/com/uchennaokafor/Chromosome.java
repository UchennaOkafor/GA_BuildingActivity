package com.uchennaokafor;

import java.util.*;

public class Chromosome {
    private final int MAX_BUILDINGS = 8;
    private final int MAX_ACTIVITIES = 7;

    private Gene[] genes;
    private Random rand;
    private List<Integer> availableBuildings;
    private List<Integer> availableActivities;

    public Chromosome() {
        initialize();
        generateRandomGenes();
    }

    public Chromosome(Gene[] genes) {
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
        List<Gene> generatedGenes = new ArrayList<>();
        Gene gene;

        do {
            gene = generateUniqueGene();
            if (gene != null) {
                generatedGenes.add(gene);
            }
        } while (gene != null);

        this.genes = generatedGenes.toArray(new Gene[]{});
    }

    public boolean isChromosomeValid() {
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

    public int getFitness() {
        return FitnessCalc.getChromosomeFitness(this);
    }

    public Gene[] getGenes() {
        return this.genes;
    }

    public Chromosome deepClone() {
        Gene[] genesCopy = new Gene[this.genes.length];

        for (int i = 0; i < this.genes.length; i++) {
            genesCopy[i] = this.genes[i].deepClone();
        }

        return new Chromosome(genesCopy);
    }

    @Override
    public String toString() {
        return String.format("Chromosome {Genes=%s}", Arrays.toString(genes));
    }
}