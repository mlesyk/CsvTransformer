package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class MathFilter extends AbstractRule {
    private String condition;
    private double conditionValue;
    private ResultColumn column;


    public MathFilter(int columnId, String condition, double conditionValue, List<ResultColumn> columns) {
        columnIds = new int[1];
        this.columnIds[0] = columnId;
        this.columns = columns;
        this.condition = condition;
        this.conditionValue = conditionValue;
    }

    @Override
    public void apply() {
        try {
            if(column == null) {
                column = columns.get(columnIds[0]);
            }
            double columnValue = Double.parseDouble(column.getData());
            column.setSkipRow(!MathUtil.filter(columnValue, conditionValue, condition));
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }

    }
}
