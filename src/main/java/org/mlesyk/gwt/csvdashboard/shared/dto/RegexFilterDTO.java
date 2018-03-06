package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class RegexFilterDTO extends AbstractRuleDTO {

    private int columnPosition;
    private int searchType;
    private String searchData;

    public RegexFilterDTO(){}

    public RegexFilterDTO(int columnPosition, int searchType, String searchData) {
        this.columnPosition = columnPosition;
        this.searchData = searchData;
        this.searchType = searchType;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public int getSearchType() {
        return searchType;
    }

    public String getSearchData() {
        return searchData;
    }

    @Override
    public String toString() {
        return "RegexFilterDTO{" +
                "columnPosition=" + columnPosition +
                ", searchType=" + searchType +
                ", searchData='" + searchData + '\'' +
                '}';
    }
}
