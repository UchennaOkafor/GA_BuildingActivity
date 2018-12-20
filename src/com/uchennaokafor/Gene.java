package com.uchennaokafor;

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
}
