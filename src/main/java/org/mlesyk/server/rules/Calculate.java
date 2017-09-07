package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Calculate extends AbstractRule {

    private List<ResultColumn> columns;
    private String operation;
    private boolean applied = false;
    int calculatedColumnId;
    ResultColumn calculatedColumn;
    ResultColumn column1;
    ResultColumn column2;

    public Calculate(int firstColumnId, int secondColumnId, String operation, List<ResultColumn> columns) {
        this.columns = columns;
        columnIds = new int[2];
        this.columnIds[0] = firstColumnId;
        this.columnIds[1] = secondColumnId;
        this.operation = operation;
    }

    @Override
    public void apply() {
        if(column1 == null || column2 == null) {
            column1 = columns.get(columnIds[0]);
            column2 = columns.get(columnIds[1]);
        }
        if(!applied) {
            calculatedColumnId = columnIds[0] > columnIds[1] ? columnIds[0] + 1: columnIds[1] + 1;
            calculatedColumn = new ResultColumn();
            calculatedColumn.setId(calculatedColumnId);
            calculatedColumn.setArrayColumnId(calculatedColumnId);
            calculatedColumn.setSourceFileColumnId(column1.getSourceFileColumnId());
            // shift all columns after calculated and change all IDs of their rules
            for (int i = calculatedColumnId; i < columns.size(); i++) {
                columns.get(i).setId(i + 1);
                columns.get(i).setArrayColumnId(i + 1);
                for(AbstractRule rule: columns.get(i).getRules()) {
                    rule.columnIds[0] = rule.columnIds[0] + 1;
                }
            }
            columns.add(calculatedColumnId, calculatedColumn);
            applied = true;
        }
        try {
            Double number1 = Double.parseDouble(column1.getData());
            Double number2 = Double.parseDouble(column2.getData());
            calculatedColumn.setData(Double.toString(MathUtil.calculate(number1, number2, operation)));
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }
    }

    @Override
    protected void changeResultColumnId(int value) {
        super.changeResultColumnId(value);
        calculatedColumnId = value;
    }

    @Override
    public int getResultColumnId() {
        return calculatedColumnId;
    }
}
