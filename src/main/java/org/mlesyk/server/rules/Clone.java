package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Clone extends AbstractRule {

    private List<ResultColumn> columns;
    private boolean applied = false;

    public Clone(int columnId, List<ResultColumn> columns) {
        columnIds = new int[1];
        this.columnIds[0] = columnId;
        this.columns = columns;
    }

    @Override
    public void apply() {
        ResultColumn column = columns.get(columnIds[0]);
        if(!applied) {
            ResultColumn clone = new ResultColumn(column);
            for (int i = column.getId() + 1; i < columns.size(); i++) {
                columns.get(i).setId(i + 1);
            }
            columns.add(column.getId() + 1, clone);
            applied = true;
        } else {
            ResultColumn clone = columns.get(column.getId() + 1);
            clone.setData(column.getData());
        }
    }
}
