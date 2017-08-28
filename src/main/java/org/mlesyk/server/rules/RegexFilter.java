package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maks on 24.08.2017.
 */
public class RegexFilter extends AbstractRule {

    private int columnPosition;
    List<ResultColumn> columns;
    private Pattern pattern;
    private Matcher matcher;

    public RegexFilter(int columnPosition, String pattern, List<ResultColumn> columns) {
        this.columnPosition = columnPosition;
        this.columns = columns;
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public void apply() {
        ResultColumn column = columns.get(columnPosition);
        matcher = pattern.matcher(column.getData());
        String data = matcher.find() ? matcher.group(): "";
        column.setData(data);
    }
}