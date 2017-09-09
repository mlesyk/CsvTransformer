package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class ChangeColumnPosition extends AbstractRule {

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
            ResultColumn temp;
            if (columnIds[0] < columnIds[1]) {
                temp = columns.get(columnIds[1]);
                for (int i = columnIds[1]; i > columnIds[0]; i--) {
                    temp.setArrayColumnId(columns.get(i - 1).getArrayColumnId());
                    for (AbstractRule rule : temp.getRules()) {
                        rule.columnIds[0] = rule.columnIds[0] - 1;
                    }
                    ResultColumn temp2 = columns.get(i - 1);
                    columns.set(i - 1, temp);
                    temp = temp2;
                }
            } else {
                temp = columns.get(columnIds[1]);
                for (int i = columnIds[1]; i < columnIds[0]; i++) {
                    temp.setArrayColumnId(columns.get(i + 1).getArrayColumnId());
                    for (AbstractRule rule : temp.getRules()) {
                        rule.columnIds[0] = rule.columnIds[0] + 1;
                    }
                    ResultColumn temp2 = columns.get(i + 1);
                    columns.set(i + 1, temp);
                    temp = temp2;
                }
            }
            temp.setArrayColumnId(columnIds[1]);
            columns.set(columnIds[1], temp);
            for (int i = 0; i < temp.getRules().size(); i++) {
                if (temp.getRules().get(i) == this) {
                    continue;
                }
                AbstractRule rule = temp.getRules().get(i);
                rule.changeResultColumnId(columnIds[1]);
                if (columnIds[0] < columnIds[1]) {
                    if (rule instanceof MergeColumns && rule.columnIds[1] < columnIds[1] && rule.columnIds[1] > columnIds[0]) {
                        rule.columnIds[1] = rule.columnIds[1] - 1;
                    }
                    if (rule instanceof Calculate && rule.columnIds[1] < columnIds[1] && rule.columnIds[1] > columnIds[0]) {
                        rule.columnIds[1] = rule.columnIds[1] - 1;
                    }

                } else {
                    if (rule instanceof MergeColumns && rule.columnIds[1] > columnIds[1] && rule.columnIds[1] < columnIds[0]) {
                        rule.columnIds[1] = rule.columnIds[1] + 1;
                    }
                    if (rule instanceof Calculate && rule.columnIds[1] > columnIds[1] && rule.columnIds[1] < columnIds[0]) {
                        rule.columnIds[1] = rule.columnIds[1] + 1;
                    }
                }
            }
            applied = true;
        }
    }
}
