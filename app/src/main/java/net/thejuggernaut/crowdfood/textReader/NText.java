package net.thejuggernaut.crowdfood.textReader;

import java.util.Map;

public class NText {
    String Nutrition;
    Map<String,float[]> correction;

    public String getNutrition() {
        return Nutrition;
    }

    public void setNutrition(String nutrition) {
        Nutrition = nutrition;
    }

    public Map<String, float[]> getCorrection() {
        return correction;
    }

    public void setCorrection(Map<String, float[]> correction) {
        this.correction = correction;
    }
}
