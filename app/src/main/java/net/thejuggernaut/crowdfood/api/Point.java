package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;

public class Point  implements Serializable {
    String user;
    int confirm;
    int deny;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public int getDeny() {
        return deny;
    }

    public void setDeny(int deny) {
        this.deny = deny;
    }
}
