package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Clone extends AbstractRule {
    private boolean applied = false;
    private ResultColumn column;
    private ResultColumn clone;

    public Clone(int columnId, List<ResultColumn> columns, int id) {
        this.id = id;
        columnIds = new int[]{columnId};
        this.columns = columns;
    }

    @Override
    public void apply() {
        if(column == null) {
            column = columns.get(columnIds[0]);
        }
        if (!applied) {
            clone = new ResultColumn(column);
            // shift all columns after cloned and change all IDs of their rules
            for (int i = clone.getArrayColumnId(); i < columns.size(); i++) {
                columns.get(i).setArrayColumnId(i + 1);
            }
            columns.add(clone.getArrayColumnId(), clone);
            clone.setData(column.getData());
            applied = true;
        } else {
            clone.setData(column.getData());
        }
    }

    @Override
    public void rollback() {
        for (int i = clone.getArrayColumnId(); i < columns.size(); i++) {
            columns.get(i).setArrayColumnId(i - 1);
        }
        columns.remove(clone);
    }

    @Override
    public int[] getResultColumnId() {
        return columnIds;
    }
}
