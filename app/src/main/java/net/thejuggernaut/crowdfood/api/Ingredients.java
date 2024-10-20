package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Ingredients implements Serializable {

    String[] ingredients;
    int up;
    int down;
    int stamp;
    Nutrition[] changes;



    public int getUp() {
        return up;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
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
