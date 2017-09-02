package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class ChangeColumnPosition extends AbstractRule {

    private List<ResultColumn> columns;

    // this Rule changes document structure, should be applied once
    private boolean applied;

    public ChangeColumnPosition(int currentPosition, int newPosition, List<ResultColumn> columns) {
        columnIds = new int[2];
        this.columnIds[0] = currentPosition;
        this.columnIds[1] = newPosition;
        this.columns = columns;
        this.applied = false;
    }

    @Override
    public void apply() {
        if (!applied) {
            if (columnIds[0] < columnIds[1]) {
                for (int i = columnIds[1]; i > columnIds[0]; i--) {
                    columns.get(i).setId(columns.get(i - 1).getId());
                }
            } else {
                for (int i = columnIds[1]; i < columnIds[0]; i++) {
                    columns.get(i).setId(columns.get(i + 1).getId());
                }
            }
            columns.get(columnIds[0]).setId(columnIds[1]);
            applied = true;
        }
    }

    public boolean isApplied() {
        return applied;
    }
}
