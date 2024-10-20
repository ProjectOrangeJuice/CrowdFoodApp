package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable {
    String ID;
    String productName;
    String[] ingredients;
    Map<String,Float> nutrition;
    String serving;



    int version;
    Map<String, Point> trust;
    String changed;
    Product[] changes;
    String error;

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
public String getServing() {
    return serving;
}
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, Float> getNutrition() {
        return nutrition;
    }

    public void setNutrition(Map<String, Float> nutrition) {
        this.nutrition = nutrition;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, Point> getTrust() {
        return trust;
    }

    public void setTrust(Map<String, Point> trust) {
        this.trust = trust;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public Product[] getChanges() {
        return changes;
    }

    public void setChanges(Product[] changes) {
        this.changes = changes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
