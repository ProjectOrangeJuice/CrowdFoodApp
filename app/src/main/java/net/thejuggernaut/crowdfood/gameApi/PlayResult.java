package net.thejuggernaut.crowdfood.gameApi;

import net.thejuggernaut.crowdfood.api.Product;

import java.io.Serializable;

public class PlayResult  implements Serializable {
    boolean correct;
    boolean found;
    String error;
    Product product;
}
