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

    public MergeColumns(int firstColumnId, int secondColumnId, List<ResultColumn> columns) {
        this.columns = columns;
        columnIds = new int[2];
        this.columnIds[0] = firstColumnId;
        columnIds[1] = secondColumnId;
    }

    @Override
    public void apply() {
        if(firstColumn == null || secondColumn == null) {
            firstColumn = columns.get(columnIds[0]);
            secondColumn = columns.get(columnIds[1]);
        }

        if (!applied) {
            firstColumn.getMergedColumns().add(secondColumn);
            secondColumn.setDeleted(true);
            firstColumn.joinData(secondColumn.getData());
            applied = true;
        } else {
            firstColumn.joinData(secondColumn.getData());
        }
    }
}
