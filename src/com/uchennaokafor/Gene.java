package com.uchennaokafor;

import java.util.Objects;

public class Gene {

    private int activity;
    private int building;

    public Gene(int activity, int building) {
        this.activity = activity;
        this.building = building;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getUserFriendlyActivity() {
        return getActivity() + 1;
    }

    public int getUserFriendlyBuilding() {
        return getBuilding() + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gene)) return false;
        Gene gene = (Gene) o;
        return getActivity() == gene.getActivity() &&
                getBuilding() == gene.getBuilding();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivity(), getBuilding());
    }

    @Override
    public String toString() {
        return String.format("{Building=%d, Activity=%d}",
                getUserFriendlyBuilding(),getUserFriendlyActivity());
    }
}