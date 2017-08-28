package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class MergeColumns extends AbstractRule {

    private int firstColumnId;
    private int secondColumnId;
    List<ResultColumn> columns;

    public MergeColumns(int firstColumnId, int secondColumnId, List<ResultColumn> columns) {
        this.columns = columns;
        this.firstColumnId = firstColumnId;
        this.secondColumnId = secondColumnId;
    }

    @Override
    public void apply() {
        ResultColumn firstColumn = columns.get(firstColumnId);
        ResultColumn secondColumn = columns.get(secondColumnId);
        secondColumn.setId(firstColumn.getId());
    }
}
