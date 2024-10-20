package net.thejuggernaut.crowdfood.api;

import java.io.Serializable;

public class Trust  implements Serializable {
    public int trustUp;
    public int trustDown;

    public int getTrustUp() {
        return trustUp;
    }

    public int getTrustDown() {
        return trustDown;
    }
}
