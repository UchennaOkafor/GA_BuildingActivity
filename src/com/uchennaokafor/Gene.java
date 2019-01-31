package com.uchennaokafor;

public class Gene {
    /**
     *     The alleles
     */
    private int activity;
    private int building;

    public Gene(int building, int activity) {
        this.building = building;
        this.activity = activity;
    }

    public int getActivity() {
        return activity;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    /**
     * Constructs a new instance of the object with the same member values
     * to avoid a reference type bug, effectively cloning the object
     */
    public Gene deepClone() {
        return new Gene(building, activity);
    }

    /**
     * A user friendly representation of each gene object
     */
    @Override
    public String toString() {
        return String.format("{Building=%d, Activity=%d}",
                getBuilding() + 1, getActivity() + 1);
    }
}