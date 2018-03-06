package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class ChangeColumnPositionDTO extends AbstractRuleDTO {

    private int currentPosition;
    private int newPosition;

    public ChangeColumnPositionDTO() {}

    public ChangeColumnPositionDTO(int currentPosition, int newPosition) {
        this.currentPosition = currentPosition;
        this.newPosition = newPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getNewPosition() {
        return newPosition;
    }

    @Override
    public String toString() {
        return "ChangeColumnPositionDTO{" +
                "currentPosition=" + currentPosition +
                ", newPosition=" + newPosition +
                '}';
    }
}
