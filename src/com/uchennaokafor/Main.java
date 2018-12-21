package com.uchennaokafor;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        System.out.println(Objects.hash(5, 1));
        System.out.println(Objects.hash(1, 5));

        Population population = new Population(50);
        Permutation permutation = population.getFittest();
        System.out.println(permutation);
        System.out.println(permutation.getFitnessScore());
    }
}