package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class ChangeColumnPosition extends AbstractRule {

    // this Rule changes document structure, should be applied once
    private boolean applied;

    public ChangeColumnPosition(int currentPosition, int newPosition, List<ResultColumn> columns, int id) {
        this.id = id;
        columnIds = new int[]{currentPosition, newPosition};
        this.columns = columns;
        this.applied = false;
    }

    @Override
    public void apply() {
        if (!applied) {
            ResultColumn temp;
            if (columnIds[0] < columnIds[1]) {
                temp = columns.get(columnIds[1]);
                for (int i = columnIds[1]; i > columnIds[0]; i--) {
                    temp.setArrayColumnId(columns.get(i - 1).getArrayColumnId());
                    ResultColumn temp2 = columns.get(i - 1);
                    columns.set(i - 1, temp);
                    temp = temp2;
                }
            } else {
                temp = columns.get(columnIds[1]);
                for (int i = columnIds[1]; i < columnIds[0]; i++) {
                    temp.setArrayColumnId(columns.get(i + 1).getArrayColumnId());
                    ResultColumn temp2 = columns.get(i + 1);
                    columns.set(i + 1, temp);
                    temp = temp2;
                }
            }
            temp.setArrayColumnId(columnIds[1]);
            columns.set(columnIds[1], temp);
            applied = true;
        }
    }

    @Override
    public void rollback() {
        ResultColumn temp;
        if (columnIds[0] < columnIds[1]) {
            temp = columns.get(columnIds[0]);
            for (int i = columnIds[0]; i < columnIds[1]; i++) {
                temp.setArrayColumnId(columns.get(i + 1).getArrayColumnId());
                ResultColumn temp2 = columns.get(i + 1);
                columns.set(i + 1, temp);
                temp = temp2;
            }
        } else {
            temp = columns.get(columnIds[0]);
            for (int i = columnIds[0]; i > columnIds[1]; i--) {
                temp.setArrayColumnId(columns.get(i - 1).getArrayColumnId());
                ResultColumn temp2 = columns.get(i - 1);
                columns.set(i - 1, temp);
                temp = temp2;
            }
        }
        temp.setArrayColumnId(columnIds[0]);
        columns.set(columnIds[0], temp);
    }

    @Override
    public int[] getResultColumnId() {
        return new int[] {columnIds[1]};
    }
}
