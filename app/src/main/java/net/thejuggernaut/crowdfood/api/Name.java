package net.thejuggernaut.crowdfood.api;

import java.util.Map;

public class Name {
    String name;
    int up;
    int down;
    int stamp;
    Nutrition[] changes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    public Nutrition[] getChanges() {
        return changes;
    }

    public void setChanges(Nutrition[] changes) {
        this.changes = changes;
    }
}
