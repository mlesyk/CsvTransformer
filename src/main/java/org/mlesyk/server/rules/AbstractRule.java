package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public abstract class AbstractRule {
    // source/target id of columns for rules
    protected int[] columnIds;

    protected List<ResultColumn> columns;

    protected void changeResultColumnId(int value) {
        columnIds[0] = value;
    }

    public int getResultColumnId() {
        return columnIds[0];
    }

    public int[] getColumnsId() {
        return columnIds;
    }

    public void setColumnIds(int[] columnIds) {
        this.columnIds = columnIds;
    }

    public abstract void apply();
}
