package org.mlesyk.gwt.csvdashboard.shared.dto;

/**
 * Created by Maks on 25.09.2017.
 */
public class MathFilterDTO extends AbstractRuleDTO {

    private int columnId;
    private String condition;
    private double conditionValue;

    public MathFilterDTO() {}

    public MathFilterDTO(int columnId, String condition, double conditionValue) {
        this.columnId = columnId;
        this.condition = condition;
        this.conditionValue = conditionValue;
    }

    public int getColumnId() {
        return columnId;
    }

    public String getCondition() {
        return condition;
    }

    public double getConditionValue() {
        return conditionValue;
    }

    @Override
    public String toString() {
        return "MathFilterDTO{" +
                "columnId=" + columnId +
                ", condition='" + condition + '\'' +
                ", conditionValue=" + conditionValue +
                '}';
    }
}
