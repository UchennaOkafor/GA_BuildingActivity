package com.uchennaokafor;

public class FitnessCalc {

    private static int[][] suitabilityIndicies = new int[][]{
        { 35, 10, 12, 7, 12, 24, 15},
        { 30, 14, 16, 25, 25, 29, 28},
        { 18, 22, 21, 29, 27, 37, 37},
        { 20, 34, 27, 26, 26, 34, 28},
        { 24, 26, 35, 13, 15, 37, 29},
        { 24, 19, 25, 24, 24, 20, 23},
        { 18, 22, 30, 25, 27, 21, 19},
        { 33, 29, 22, 25, 25, 25, 33},
    };

    /**
     * Calculates the fitness for each chromosome by adding up the suitability index values for each gene
     */
    public static int getChromosomeFitness(Chromosome genes) {
        int fitness = 0;

        for (Gene gene : genes.getGenes()) {
            int buildingIndex = gene.getBuilding();
            int activityIndex = gene.getActivity();

            fitness += suitabilityIndicies[buildingIndex][activityIndex];
        }

        return fitness;
    }
}