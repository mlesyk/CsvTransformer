package org.mlesyk.server.rules;

/**
 * Created by Maks on 24.08.2017.
 */
public abstract class AbstractRule {
    // rules are applied in order they were added
    protected int id;
    // id of columns for rules
    protected int[] columnIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getColumnsId() {
        return columnIds;
    }

    public void setColumnIds(int[] columnIds) {
        this.columnIds = columnIds;
    }

    public abstract void apply();
}
