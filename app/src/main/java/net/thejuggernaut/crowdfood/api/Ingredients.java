package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Ingredients implements Serializable {

    String[] ingredients;
    Trust votes;
    int stamp;
    Nutrition[] changes;



    public Trust getVotes() {
        return votes;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
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
