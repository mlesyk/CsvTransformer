package org.mlesyk.server.rules;

import org.mlesyk.server.ResultColumn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maks on 24.08.2017.
 */
public class RegexFilter extends AbstractRule {

    private ResultColumn column;
    private Pattern pattern;
    private Matcher matcher;

    public RegexFilter(ResultColumn column, String pattern) {
        this.column = column;
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public void apply() {
        matcher = pattern.matcher(column.getData());
        String data = matcher.find() ? matcher.group(): "";
        column.setData(data);
    }
}