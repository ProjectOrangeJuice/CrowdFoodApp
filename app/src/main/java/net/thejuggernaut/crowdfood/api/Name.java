package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Name implements Serializable {
    String name;
    Trust votes;
    int stamp;
    Nutrition[] changes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Trust getVotes() {
        return votes;
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
}
