package net.thejuggernaut.crowdfood.accountApi;

import java.io.Serializable;
import java.util.Map;

public class Info implements Serializable {

    Map<String,Float> recommendedNutrition;
    String  []Allergies = new String[0];


    public Map<String, Float> getRecommendedNutrition() {
        return recommendedNutrition;
    }

    public void setRecommendedNutrition(Map<String, Float> recommendedNutrition) {
        this.recommendedNutrition = recommendedNutrition;
    }

    public String[] getAllergies() {
        return Allergies;
    }

    public void setAllergies(String[] allergies) {
        Allergies = allergies;
    }
}
