package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Ingredients implements Serializable {

    String[] ingredients = new String[0];
    Trust votes = new Trust();
    boolean vote = false;
    int stamp = 0;
    Ingredients[] changes = new Ingredients[0];


    public boolean isVoteable() {
        return vote;
    }

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

    public Ingredients[] getChanges() {
        return changes;
    }


}
