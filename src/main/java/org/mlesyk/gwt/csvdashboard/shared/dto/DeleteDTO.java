package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class DeleteDTO extends AbstractRuleDTO {
    private int columnPosition;

    public DeleteDTO(){}

    public DeleteDTO(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    @Override
    public String toString() {
        return "DeleteDTO{" +
                "columnPosition=" + columnPosition +
                '}';
    }
}
