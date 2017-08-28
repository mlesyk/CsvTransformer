package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Clone extends AbstractRule {

    private List<ResultColumn> columns;
    ResultColumn column;
    AbstractRule[] rules;

    private boolean applied = false;

    public Clone(ResultColumn column, List<ResultColumn> columns, AbstractRule... rules) {
        this.column = column;
        this.columns = columns;
        this.rules = rules;
    }

    @Override
    public void apply() {
        ResultColumn clone = new ResultColumn(column);
        if(!applied) {
            for (int i = column.getId() + 1; i < columns.size(); i++) {
                columns.get(i).setId(i + 1);
            }
            columns.add(column.getId() + 1, clone);
            for(AbstractRule rule: rules) {
                clone.addRule(rule);
            }
            applied = true;
        }
        clone.applyAllRules();
    }
}
