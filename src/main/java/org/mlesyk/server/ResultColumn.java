package org.mlesyk.server;

import org.mlesyk.server.rules.AbstractRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public class ResultColumn implements Comparable {
    // data source for current column
    private int sourceFileColumnId;

    private static int counter;

    private boolean skipRow;

    private boolean deleted;

    private boolean iterated;

    private List<ResultColumn> mergedColumns;

    // filled with data in cell when iterating each row
    private String data;

    private List<AbstractRule> rules;

    // id of column in array of columns
    private int arrayColumnId;

    public ResultColumn() {
        rules = new ArrayList<AbstractRule>();
        mergedColumns = new ArrayList<ResultColumn>();
        arrayColumnId = counter;
        sourceFileColumnId = counter;
        counter++;
        skipRow = false;
    }

    public ResultColumn(ResultColumn source) {
        rules = new ArrayList<AbstractRule>();
        mergedColumns = source.getMergedColumns();
        this.arrayColumnId = source.getArrayColumnId() + 1;
        this.sourceFileColumnId = source.sourceFileColumnId;
        this.data = source.data;
        skipRow = false;
    }

    public int getArrayColumnId() {
        return arrayColumnId;
    }

    public void setArrayColumnId(int arrayColumnId) {
        this.arrayColumnId = arrayColumnId;
    }


    public boolean isSkipRow() {
        if(skipRow) { // should work only for one row (single iteration)
            this.skipRow = false;
            return true;
        } else {
            return false;
        }
    }

    public void setSkipRow(boolean skipRow) {
        this.skipRow = skipRow;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<ResultColumn> getMergedColumns() {
        return mergedColumns;
    }

    public void setMergedColumns(List<ResultColumn> mergedColumns) {
        this.mergedColumns = mergedColumns;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResultColumn joinData(String data) {
        this.data = new StringBuilder().append(this.data).append(" ").append(data).toString();
        return this;
    }

    public static void setCounter(int counter) {
        ResultColumn.counter = counter;
    }

    public boolean isIterated() {
        return iterated;
    }

    public void setIterated(boolean iterated) {
        this.iterated = iterated;
    }

    public int getSourceFileColumnId() {
        return sourceFileColumnId;
    }

    public void setSourceFileColumnId(int sourceFileColumnId) {
        this.sourceFileColumnId = sourceFileColumnId;
    }

    public List<AbstractRule> getRules() {
        return rules;
    }

    public void setRules(List<AbstractRule> rules) {
        this.rules = rules;
    }

    public void applyAllRules() {
        for(AbstractRule rule: rules) {
            rule.apply();
        }
    }

    public void addRule(AbstractRule rule) {
        rules.add(rule);
    }

    public void addAllRules(AbstractRule[] rules) {this.rules.addAll(Arrays.asList(rules));}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + arrayColumnId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultColumn other = (ResultColumn) obj;
        if (this.arrayColumnId != other.arrayColumnId)
            return false;
        return true;
    }

    @Override
    public int compareTo(Object obj) {
        ResultColumn other = (ResultColumn) obj;
        return ( this.arrayColumnId - other.arrayColumnId );
    }

    @Override
    public String toString() {
        return data;
    }
}
