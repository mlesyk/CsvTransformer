package org.mlesyk.server.utils;

import java.util.regex.Pattern;

/**
 * Created by Maks on 24.08.2017.
 */
public final class RegexUtil {
    public final static int CONTAINS = 1;
    public final static int BEGINS_WITH = 2;
    public final static int ENDS_WITH = 3;
    public final static int EQUALS = 4;
    public final static int REGEX = 5;

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
