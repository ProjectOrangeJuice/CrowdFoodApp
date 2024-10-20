package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;

public class Vote  implements Serializable {
    private String id;
    private int name;
    private int ingredients;
    private int nutrition;

    public Vote(String barcode, int name, int ing, int nut){
        this.id = barcode;
        this.name = name;
        this.ingredients = ing;
        this.nutrition = nut;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIngredients() {
        return ingredients;
    }

    public void setIngredients(int ingredients) {
        this.ingredients = ingredients;
    }

    public int getNutrition() {
        return nutrition;
    }

    public void setNutrition(int serving) {
        this.nutrition = serving;
    }
}
