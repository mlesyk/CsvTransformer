package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Calculate extends AbstractRule {

    private List<ResultColumn> columns;
    private String operation;
    private boolean applied = false;

    public Calculate(int firstColumnId, int secondColumnId, String operation, List<ResultColumn> columns) {
        this.columns = columns;
        columnIds = new int[2];
        this.columnIds[0] = firstColumnId;
        this.columnIds[1] = secondColumnId;
        this.operation = operation;
    }

    @Override
    public void apply() {
        ResultColumn column1 = columns.get(columnIds[0]);
        ResultColumn column2 = columns.get(columnIds[1]);

        // check if columnId was changed by ChangeColumnPosition rule
        int[] newResultIds = Arrays.copyOf(columnIds, columnIds.length);
        for(int i = 0; i < columnIds.length; i++) {
            for(AbstractRule rule: columns.get(i).getRules()) {
                if(rule instanceof ChangeColumnPosition && ((ChangeColumnPosition) rule).isApplied()) {
                    newResultIds[i] = columns.get(i).getId();
                }
            }
        }

        int resultColumnId = newResultIds[0] > newResultIds[1] ? newResultIds[0] + 1: newResultIds[1] + 1;

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
