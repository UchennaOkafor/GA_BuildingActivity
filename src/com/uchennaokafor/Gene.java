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

    @Override
    public String toString() {
        return "Gene{" +
                "activity=" + getActivity() +
                ", building=" + getBuilding() +
                '}';
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
}
