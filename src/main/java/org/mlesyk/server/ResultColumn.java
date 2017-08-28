package org.mlesyk.server;

import org.mlesyk.server.rules.AbstractRule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Maks on 24.08.2017.
 */
public class ResultColumn implements Comparable {
    // order of resulted column
    private int id;

    // data source for current column
    private int sourceFileColumnId;

    private static int counter;

    // filled with data in cell when iterating each row
    private String data;

    private List<AbstractRule> rules;

    public ResultColumn() {
        rules = new ArrayList<AbstractRule>();
        id = counter;
        sourceFileColumnId = counter;
        counter++;
    }

    public ResultColumn(ResultColumn source) {
        rules = new ArrayList<AbstractRule>();
        this.id = source.id + 1;
        this.sourceFileColumnId = source.sourceFileColumnId;
        this.data = source.data;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
//        while(!rules.isEmpty()){
//            rules.poll().apply();
//        }
        for(AbstractRule rule: rules) {
            rule.apply();
        }
    }

    public void addRule(AbstractRule rule) {
        rules.add(rule);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        if (this.id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Object obj) {
        ResultColumn other = (ResultColumn) obj;
        return ( this.id - other.id );
    }

    @Override
    public String toString() {
        return data;
    }
}
