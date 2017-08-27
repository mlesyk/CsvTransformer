package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

/**
 * Created by Maks on 24.08.2017.
 */
public class Delete extends AbstractRule {
    public static final int DELETED = -1;

    // this Rule changes document structure, should be applied once
    private boolean applied;

    private ResultColumn column;

    public Delete(ResultColumn column) {
        this.column = column;
    }

    @Override
    public void apply() {
        column.setId(DELETED);
    }
}
