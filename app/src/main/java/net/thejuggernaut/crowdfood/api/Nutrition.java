package net.thejuggernaut.crowdfood.api;

import java.util.Map;

public class Nutrition {
    Map<String,Float> nutrition;
    int up;
    int down;
    int stamp;
    Nutrition[] changes;

    public Map<String, Float> getNutrition() {
        return nutrition;
    }

    public void setNutrition(Map<String, Float> nutrition) {
        this.nutrition = nutrition;
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
