package org.mlesyk.server.utils;

import java.util.regex.Pattern;

import static org.mlesyk.gwt.csvdashboard.shared.RegexUtilConstants.*;

/**
 * Created by Maks on 24.08.2017.
 */
public final class RegexUtil {
    private RegexUtil(){}

    public static Pattern search(int searchType, String searchData) {
        switch (searchType) {
            case CONTAINS:
                return Pattern.compile(".*" + searchData + ".*");
            case BEGINS_WITH:
                return Pattern.compile("^" + searchData + ".*");
            case ENDS_WITH:
                return Pattern.compile(".*" + searchData + "$");
            case EQUALS:
            case REGEX:
                return Pattern.compile(searchData);
            default:
                throw new UnsupportedOperationException("Unknown search type");
        }
    }


}
