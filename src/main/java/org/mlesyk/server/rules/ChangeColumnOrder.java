package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;
import java.util.Set;

/**
 * Created by Maks on 24.08.2017.
 */
public class ChangeColumnOrder extends AbstractRule {

    private int currentPosition;
    private int newPosition;
    private List<ResultColumn> columns;

    // this Rule changes document structure, should be applied once
    private boolean applied;

    public ChangeColumnOrder(ResultColumn column, int newPosition, List<ResultColumn> columns) {
        this.currentPosition = column.getId();
        this.newPosition = newPosition;
        this.columns = columns;
        this.applied = false;
    }

    @Override
    public void apply() {
        if (!applied) {
            if (currentPosition < newPosition) {
                for (int i = newPosition; i > currentPosition; i--) {
                    columns.get(i).setId(columns.get(i - 1).getId());
                }
            } else {
                for (int i = newPosition; i < currentPosition; i++) {
                    columns.get(i).setId(columns.get(i + 1).getId());
                }
            }
            columns.get(currentPosition).setId(newPosition);
            applied = true;
        }
    }
}
