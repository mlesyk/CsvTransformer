package org.mlesyk.server.rules;

import org.mlesyk.gwt.csvdashboard.shared.RegexUtilConstants;
import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.RegexUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maks on 24.08.2017.
 */
public class RegexFilter extends AbstractRule {
    private Pattern pattern;
    private int searchType;
    private ResultColumn column;

    public RegexFilter(int columnPosition, String pattern, List<ResultColumn> columns, int id) {
        this.id = id;
        this.searchType = RegexUtilConstants.REGEX;
        columnIds = new int[]{columnPosition};
        this.columns = columns;
        this.pattern = RegexUtil.search(this.searchType, pattern);
        column = columns.get(columnIds[0]);
    }

    public RegexFilter(int columnPosition, int searchType, String searchData, List<ResultColumn> columns, int id) {
        this.id = id;
        this.searchType = searchType;
        columnIds = new int[]{columnPosition};
        this.columns = columns;
        this.pattern = RegexUtil.search(this.searchType, searchData);
    }

    @Override
    public void apply() {
        if(column == null) {
            column = columns.get(columnIds[0]);
        }
        Matcher matcher = pattern.matcher(column.getData());
        String data = matcher.find() ? matcher.group(): "";
        if(data.equals("")) {
            column.setSkipRow(true);
        }
        column.setData(data);
    }

    @Override
    public void rollback() {
        // nothing to do
    }

    @Override
    public int[] getResultColumnId() {
        return columnIds;
    }
}