package org.mlesyk.server.rules;

import org.mlesyk.Loggers;
import org.mlesyk.server.ResultColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maks on 24.08.2017.
 */
public abstract class AbstractRule {
    protected final Logger log = LoggerFactory.getLogger(Loggers.CSV_CORE_RULE);

    protected int id;
    // source/target id of columns for rules
    protected int[] columnIds;

    protected List<Integer> rulesRemovalIds = new ArrayList<Integer>();

    protected List<ResultColumn> columns;

    public abstract void apply();

    public abstract void rollback();

    public int getId() {
        return id;
    }

    public abstract int[] getResultColumnId();

    public List<Integer> getRulesRemovalIds() {
        return rulesRemovalIds;
    }
}
