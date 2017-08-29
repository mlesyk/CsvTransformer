package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * Created by Maks on 24.08.2017.
 */
public class Calculate extends AbstractRule {

    private List<ResultColumn> columns;
    private int firstColumnId;
    private int secondColumnId;
    private String operation;

    private boolean applied = false;

    public Calculate(int firstColumnId, int secondColumnId, String operation, List<ResultColumn> columns) {
        this.columns = columns;
        this.firstColumnId = firstColumnId;
        this.secondColumnId = secondColumnId;
        this.operation = operation;
    }

    @Override
    public void apply() {
        ResultColumn column1 = columns.get(firstColumnId);
        ResultColumn column2 = columns.get(secondColumnId);

        int resultColumnId = firstColumnId > secondColumnId ? firstColumnId + 1: secondColumnId + 1;

        if(!applied) {
            ResultColumn calculatedColumn = new ResultColumn();
            calculatedColumn.setId(resultColumnId);
            calculatedColumn.setSourceFileColumnId(column1.getSourceFileColumnId());
            for (int i = resultColumnId; i < columns.size(); i++) {
                columns.get(i).setId(i + 1);
            }
            columns.add(resultColumnId, calculatedColumn);
            applied = true;
        }
        try {
            Double number1 = Double.parseDouble(column1.getData());
            Double number2 = Double.parseDouble(column2.getData());
            columns.get(resultColumnId).setData(Double.toString(MathUtil.calculate(number1, number2, operation)));
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }
    }
}
