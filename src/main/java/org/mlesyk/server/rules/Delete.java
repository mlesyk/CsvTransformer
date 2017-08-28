package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Delete extends AbstractRule {
    public static final int DELETED = -1;

    // this Rule changes document structure, should be applied once
    private boolean applied;
    private int columnPosition;
    List<ResultColumn> columns;
    private ResultColumn column;

    public Delete(int columnPosition, List<ResultColumn> columns) {
        this.columns = columns;
        this.columnPosition = columnPosition;
    }

    @Override
    public void apply() {
        if (!applied) {
            column = columns.get(columnPosition);
            column.setId(DELETED);
            applied = true;
        }
    }
}
