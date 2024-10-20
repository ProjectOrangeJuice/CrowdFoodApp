package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Nutrition implements Serializable {
    Map<String,float[]> nutrition;
    Trust votes = new Trust();
    int stamp = 0;
    boolean vote = false;
    String weight = "";
    String recommended = "";
    Nutrition[] changes = new Nutrition[0];

    public Trust getVotes() {
        return votes;
    }
    public boolean isVoteable() {
        return vote;
    }

    public Map<String, float[]> getNutrition() {
        return nutrition;
    }

    public void setNutrition(Map<String, float[]> nutrition) {
        this.nutrition = nutrition;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }
}
