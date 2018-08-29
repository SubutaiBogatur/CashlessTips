package meme.kiteq.tipit.model;

import java.io.Serializable;

public class ExposedRegisteredFn implements Serializable {
    public String inn;
    public String fn;

    public String getInn() {
        return inn;
    }

    public String getFn() {
        return fn;
    }
}
