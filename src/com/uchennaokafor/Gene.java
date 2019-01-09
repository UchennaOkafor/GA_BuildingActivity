package com.uchennaokafor;

public class Gene {

    private int activity;
    private int building;

    public Gene(int building, int activity) {
        this.building = building;
        this.activity = activity;
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
    public String toString() {
        return String.format("{Building=%d, Activity=%d}",
                getUserFriendlyBuilding(),getUserFriendlyActivity());
    }
}