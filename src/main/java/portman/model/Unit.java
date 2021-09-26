package portman.model;

import java.util.ArrayList;

public class Unit {
    private int number;
    private String name;
    private ArrayList<String> activities = new ArrayList<>();

    public Unit(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setActivity(String activity) {
        activities.add(activity);
    }

    public String getActivity(int index) {
        return activities.get(index);
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public int getNumberOfActivities() {
        return activities.size();
    }

    public void clear() {
        activities.clear();
    }
}
