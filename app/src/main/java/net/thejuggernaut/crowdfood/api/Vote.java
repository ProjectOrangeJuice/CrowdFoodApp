package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;

public class Vote  implements Serializable {
    private int name;
    private int ingredients;
    private int serving;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIngredients() {
        return ingredients;
    }

    public void setIngredients(int ingredients) {
        this.ingredients = ingredients;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }
}
