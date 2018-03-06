package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class CalculateDTO extends AbstractRuleDTO {
    private int firstColumnId;
    private int secondColumnId;
    private String operation;

    public CalculateDTO() {
    }

    public CalculateDTO(int firstColumnId, int secondColumnId, String operation) {
        this.firstColumnId = firstColumnId;
        this.secondColumnId = secondColumnId;
        this.operation = operation;
    }

    public int getFirstColumnId() {
        return firstColumnId;
    }

    public int getSecondColumnId() {
        return secondColumnId;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "CalculateDTO{" +
                "firstColumnId=" + firstColumnId +
                ", secondColumnId=" + secondColumnId +
                ", operation='" + operation + '\'' +
                '}';
    }
}
