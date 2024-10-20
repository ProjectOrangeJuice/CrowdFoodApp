package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Nutrition implements Serializable {
    Map<String,float[]> nutrition;
    Trust votes;
    int stamp;
    String weight;
    String recommended;
    Nutrition[] changes;

    public Trust getVotes() {
        return votes;
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
