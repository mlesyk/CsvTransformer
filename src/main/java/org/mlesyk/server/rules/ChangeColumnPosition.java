package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class ChangeColumnPosition extends AbstractRule {

    private int currentPosition;
    private int newPosition;
    private List<ResultColumn> columns;

    // this Rule changes document structure, should be applied once
    private boolean applied;

    public ChangeColumnPosition(int currentPosition, int newPosition, List<ResultColumn> columns) {
        this.currentPosition = currentPosition;
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
