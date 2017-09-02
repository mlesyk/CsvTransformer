package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class MergeColumns extends AbstractRule {
    List<ResultColumn> columns;

    public MergeColumns(int firstColumnId, int secondColumnId, List<ResultColumn> columns) {
        this.columns = columns;
        columnIds = new int[2];
        columnIds[0] = firstColumnId;
        columnIds[1] = secondColumnId;
    }

    @Override
    public void apply() {
        ResultColumn firstColumn = columns.get(columnIds[0]);
        ResultColumn secondColumn = columns.get(columnIds[1]);
        secondColumn.setId(firstColumn.getId());
    }
}
