package org.mlesyk.server;

import org.mlesyk.server.rules.AbstractRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ResultColumn represents column of CSV file
 */
public class ResultColumn implements Comparable {
    // data source for current column
    private int sourceFileColumnId;

    private boolean skipRow;

    private boolean deleted;

    private List<ResultColumn> mergedColumns;

    // filled with data in cell when iterating each row
    private String data;

    // id of column in array of columns, used for sorted output
    private int arrayColumnId;

    public ResultColumn(int arrayColumnId) {
        mergedColumns = new ArrayList<ResultColumn>();
        this.arrayColumnId = arrayColumnId;
        this.sourceFileColumnId = arrayColumnId;
        skipRow = false;
    }

    public ResultColumn(ResultColumn source) {
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

    public int getSourceFileColumnId() {
        return sourceFileColumnId;
    }

    public void setSourceFileColumnId(int sourceFileColumnId) {
        this.sourceFileColumnId = sourceFileColumnId;
    }

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
