package org.mlesyk.server.rules;

/**
 * Created by Maks on 24.08.2017.
 */
public abstract class AbstractRule {
    // rules are applied in order they were added
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void apply();
}
