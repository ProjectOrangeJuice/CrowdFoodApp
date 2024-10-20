package net.thejuggernaut.crowdfood.api;

import android.graphics.Point;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable {
    String ID;
    Name productName = new Name();
    Ingredients ingredients = new Ingredients();
    Nutrition nutrition = new Nutrition();






    int version;
    String error;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Name getProductName() {
        return productName;
    }

    public void setProductName(Name productName) {
        this.productName = productName;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
//    public Product(String ID, String productName, String[] ingredients, Map<String, Float> nut, int version,
//                   Map<String, Point> trust, String changed, Product[] changes, String error){
//        this.ID = ID;
//        this.productName = productName;
//        this.ingredients = ingredients;
//        this.nutrition = nut;
//        this.version = version;
//        this.trust = trust;
//        this.changed = changed;
//        this.changes = changes;
//        this.error = error;
//    }


}
