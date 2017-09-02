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
    List<ResultColumn> columns;

    public Delete(int columnPosition, List<ResultColumn> columns) {
        this.columns = columns;
        columnIds = new int[1];
        this.columnIds[0] = columnPosition;
    }

    @Override
    public void apply() {
        if (!applied) {
            ResultColumn column = columns.get(columnIds[0]);
            column.setId(DELETED);
            applied = true;
        }
    }
}
