package net.thejuggernaut.crowdfood.gameApi;

import net.thejuggernaut.crowdfood.api.Product;

import java.io.Serializable;

public class PlayResult  implements Serializable {
    boolean correct;
    boolean found;
    String error;
    Product product;

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
