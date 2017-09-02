package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.RegexUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maks on 24.08.2017.
 */
public class RegexFilter extends AbstractRule {

    private List<ResultColumn> columns;
    private Pattern pattern;
    private int searchType;

    public RegexFilter(int columnPosition, String pattern, List<ResultColumn> columns) {
        this.searchType = RegexUtil.REGEX;
        columnIds = new int[1];
        this.columnIds[0] = columnPosition;
        this.columns = columns;
        this.pattern = RegexUtil.search(this.searchType, pattern);
    }

    public RegexFilter(int columnPosition, int searchType, String searchData, List<ResultColumn> columns) {
        this.searchType = searchType;
        columnIds = new int[1];
        this.columnIds[0] = columnPosition;
        this.columns = columns;
        this.pattern = RegexUtil.search(this.searchType, searchData);
    }

    @Override
    public void apply() {
        ResultColumn column = columns.get(columnIds[0]);
        Matcher matcher = pattern.matcher(column.getData());
        String data = matcher.find() ? matcher.group(): "";
        if(data.equals("")) {
            column.setSkipRow(true);
        }
        column.setData(data);
    }
}