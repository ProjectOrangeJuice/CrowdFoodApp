package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;
import java.util.Map;

public class Name implements Serializable {
    String name = "";
    Trust votes = new Trust();
    int stamp = 0;
    boolean vote = false;
    Name[] changes =new Name[0];

    public String getName() {
        return name;
    }
    public boolean isVoteable() {
        return vote;
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

    public Name[] getChanges() {
        return changes;
    }


}
