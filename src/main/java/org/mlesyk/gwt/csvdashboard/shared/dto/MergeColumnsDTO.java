package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class MergeColumnsDTO extends AbstractRuleDTO {

    private int firstColumnId;
    private int secondColumnId;

    public MergeColumnsDTO() {}
    public MergeColumnsDTO(int firstColumnId, int secondColumnId) {
        this.firstColumnId = firstColumnId;
        this.secondColumnId = secondColumnId;
    }

    public int getFirstColumnId() {
        return firstColumnId;
    }

    public int getSecondColumnId() {
        return secondColumnId;
    }

    @Override
    public String toString() {
        return "MergeColumnsDTO{" +
                "firstColumnId=" + firstColumnId +
                ", secondColumnId=" + secondColumnId +
                '}';
    }
}
