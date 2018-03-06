package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class Calculate extends AbstractRule {
    private String operation;
    private boolean applied = false;
    private ResultColumn calculatedColumn;
    private ResultColumn column1;
    private ResultColumn column2;

    public Calculate(int firstColumnId, int secondColumnId, String operation, List<ResultColumn> columns, int id) {
        this.id = id;
        this.columns = columns;
        columnIds = new int[]{firstColumnId,secondColumnId};
        this.operation = operation;
    }

    @Override
    public void apply() {
        if(column1 == null || column2 == null) {
            column1 = columns.get(columnIds[0]);
            column2 = columns.get(columnIds[1]);
        }
        if(!applied) {
            int calculatedColumnId = columnIds[0] > columnIds[1] ? columnIds[0] + 1: columnIds[1] + 1;
            calculatedColumn = new ResultColumn(calculatedColumnId);
            calculatedColumn.setArrayColumnId(calculatedColumnId);
            calculatedColumn.setSourceFileColumnId(column1.getSourceFileColumnId());
            // shift all columns after calculated and change all IDs of their rules
            for (int i = calculatedColumnId; i < columns.size(); i++) {
                columns.get(i).setArrayColumnId(i + 1);
            }
            columns.add(calculatedColumnId, calculatedColumn);
            applied = true;
        }
        try {
            Double number1 = Double.parseDouble(column1.getData());
            Double number2 = Double.parseDouble(column2.getData());
            calculatedColumn.setData(Double.toString(MathUtil.calculate(number1, number2, operation)));
        } catch (NumberFormatException e) {
            log.error("Cell data is not a number", e);
        }
    }

    @Override
    public void rollback() {
        for (int i = calculatedColumn.getArrayColumnId(); i < columns.size(); i++) {
            columns.get(i).setArrayColumnId(i - 1);
        }
        columns.remove(calculatedColumn);
    }

    @Override
    public int[] getResultColumnId() {
        return new int[]{columnIds[0], columnIds[1], calculatedColumn.getArrayColumnId()};
    }
}
