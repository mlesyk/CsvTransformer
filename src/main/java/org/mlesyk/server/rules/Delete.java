package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Delete extends AbstractRule {
    // this Rule changes document structure, should be applied once
    private boolean applied;
    ResultColumn deletedColumn;

    public Delete(int columnPosition, List<ResultColumn> columns, int id) {
        this.id = id;
        this.columns = columns;
        columnIds = new int[]{columnPosition};
    }

    @Override
    public void apply() {
        if (!applied) {
            deletedColumn = columns.get(columnIds[0]);
            // mark as deleted and move it to the end of list
            // but do not change arrayColumnId to make possible
            // remove Delete rule from rules chain
            deletedColumn.setDeleted(true);
            columns.remove(columnIds[0]);
            columns.add(deletedColumn);
            applied = true;
        }
    }

    @Override
    public void rollback() {
        deletedColumn.setDeleted(false);
        columns.remove(deletedColumn);
        columns.add(columnIds[0], deletedColumn);
    }

    @Override
    public int[] getResultColumnId() {
        return columnIds;
    }
}
