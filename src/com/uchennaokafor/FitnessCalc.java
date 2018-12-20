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

    public static int getGeneScore(Gene gene) {
        int activityIndex = gene.getActivity() - 1;
        int buildingIndex = gene.getBuilding() - 1;
        //Subtracting one because arrays start at 0

        return suitabilityIndicies[activityIndex][buildingIndex];
    }
}
