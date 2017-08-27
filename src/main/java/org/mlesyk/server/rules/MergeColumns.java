package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

/**
 * Created by Maks on 24.08.2017.
 */
public class MergeColumns extends AbstractRule {

    private ResultColumn firstColumn;
    private ResultColumn secondColumn;

    public MergeColumns(ResultColumn firstColumn, ResultColumn secondColumn) {
        this.firstColumn = firstColumn;
        this.secondColumn = secondColumn;
    }

    @Override
    public void apply() {
            secondColumn.setId(firstColumn.getId());
    }
}
