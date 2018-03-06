package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class CloneDTO extends AbstractRuleDTO {

    private int columnId;

    public CloneDTO(){}

    public CloneDTO(int columnId) {
        this.columnId = columnId;
    }

    public int getColumnId() {
        return columnId;
    }

    @Override
    public String toString() {
        return "CloneDTO{" +
                "columnId=" + columnId +
                '}';
    }
}
