package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class MergeColumns extends AbstractRule {
    private boolean applied;
    private ResultColumn firstColumn;
    private ResultColumn secondColumn;

    public MergeColumns(int firstColumnId, int secondColumnId, List<ResultColumn> columns, int id) {
        this.id = id;
        this.columns = columns;
        columnIds = new int[]{firstColumnId,secondColumnId};
    }

    @Override
    public void apply() {
        if(firstColumn == null || secondColumn == null) {
            firstColumn = columns.get(columnIds[0]);
            secondColumn = columns.get(columnIds[1]);
        }

        if (!applied) {
            firstColumn.getMergedColumns().add(secondColumn);
            // mark merged column as deleted and move it to the end of list
            // but do not change arrayColumnId to make possible
            // remove MergeColumn rule from rules chain
            secondColumn.setDeleted(true);
            columns.remove(columnIds[1]);
            columns.add(secondColumn);
            firstColumn.joinData(secondColumn.getData());
            applied = true;
        } else {
            firstColumn.joinData(secondColumn.getData());
        }
    }

    @Override
    public void rollback() {
        firstColumn.getMergedColumns().remove(secondColumn);
        secondColumn.setDeleted(false);
        columns.remove(secondColumn);
        columns.add(columnIds[1], secondColumn);
    }

    @Override
    public int[] getResultColumnId() {
        return columnIds;
    }
}
