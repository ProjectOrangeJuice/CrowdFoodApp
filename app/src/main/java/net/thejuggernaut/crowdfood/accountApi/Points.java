package net.thejuggernaut.crowdfood.accountApi;

import java.io.Serializable;


public class Points implements Serializable {
    int scan;
    int trust;

    public int getScan() {
        return scan;
    }

    public String getTrust() {
        if(trust > 2){
            return "High";
        }else if (trust == 1){
            return "Medium";
        }

        return "Low";
    }
}
