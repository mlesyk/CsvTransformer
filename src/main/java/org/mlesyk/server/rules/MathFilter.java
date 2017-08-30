package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class MathFilter extends AbstractRule {

    private List<ResultColumn> columns;
    private int columnId;
    private String condition;
    double conditionValue;

    public MathFilter(int columnId, String condition, double conditionValue, List<ResultColumn> columns) {
        this.columnId = columnId;
        this.columns = columns;
        this.condition = condition;
        this.conditionValue = conditionValue;
    }

    @Override
    public void apply() {
        ResultColumn column = columns.get(columnId);
        try {
            double columnValue = Double.parseDouble(column.getData());
            column.setSkipRow(!MathUtil.filter(columnValue, conditionValue, condition));
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }

    }
}
