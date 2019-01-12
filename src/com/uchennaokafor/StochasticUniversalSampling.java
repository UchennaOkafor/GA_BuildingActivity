package com.uchennaokafor;

/**
 * Source = http://puzzloq.blogspot.com/2013/03/stochastic-universal-sampling.html
 * Author = puzzloq
 */

public class StochasticUniversalSampling {
    /**
     *
     * @param population - set of individuals' segments. Segment is equal to individual's fitness.
     * @param n - number of individuals to choose from population.
     * @return set of indexes, pointing to the chosen individuals in the population set
     */
    public static int[] execute(Double[] population, int n) {
        // Calculate total fitness of population
        double f = 0.0;
        for (double segment : population) {
            f += segment;
        }
        // Calculate distance between the pointers
        double p = f / n;
        // Pick random number between 0 and p
        double start = Math.random() * p;
        // Pick n individuals
        int[] individuals = new int[n];
        int index = 0;
        double sum = population[index];
        for (int i = 0; i < n; i++) {
            // Determine pointer to a segment in the population
            double pointer = start + i * p;
            // Find segment, which corresponds to the pointer
            if (sum >= pointer) {
                individuals[i] = index;
            } else {
                for (++index; index < population.length; index++) {
                    sum += population[index];
                    if (sum >= pointer) {
                        individuals[i] = index;
                        break;
                    }
                }
            }
        }
        // Return the set of indexes, pointing to the chosen individuals
        return individuals;
    }
}