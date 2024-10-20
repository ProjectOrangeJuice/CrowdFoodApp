package net.thejuggernaut.crowdfood.gameApi;

import java.io.Serializable;

public class Question  implements Serializable {
    String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
