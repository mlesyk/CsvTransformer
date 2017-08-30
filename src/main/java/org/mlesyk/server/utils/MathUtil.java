package org.mlesyk.server.utils;

/**
 * Created by Maks on 24.08.2017.
 */
public final class MathUtil {

    // calculation operations
    public final static String DIVIDE = "/";
    public final static String MULTIPLY = "*";
    public final static String SUBTRACT = "-";
    public final static String ADD = "+";
    public final static String MOD = "%";

    // filter conditions
    public final static String GREATER = ">";
    public final static String LESSER = "<";
    public final static String GREATER_EQUAL = ">=";
    public final static String LESSER_EQUAL = "<=";
    public final static String EQUALS = "==";
    public final static String EQUALS2 = "=";
    public final static String NOT_EQUALS = "!=";
    public final static String NOT_EQUALS2 = "<>";


    private MathUtil() {
    }

    public static double calculate(double number1, double number2, String operator) {
        switch (operator) {
            case ADD:
                return (number1 + number2);
            case SUBTRACT:
                return (number1 - number2);
            case MULTIPLY:
                return (number1 * number2);
            case DIVIDE:
                return (number1 / number2);
            case MOD:
                return (number1 % number2);
            default:
                return 0;
        }
    }

    public static boolean filter(double columnValue, double expectedValue, String condition) {
        switch (condition) {
            case GREATER:
                return (columnValue > expectedValue);
            case GREATER_EQUAL:
                return (columnValue >= expectedValue);
            case LESSER:
                return (columnValue < expectedValue);
            case LESSER_EQUAL:
                return (columnValue <= expectedValue);
            case EQUALS:
            case EQUALS2:
                return (columnValue == expectedValue);
            case NOT_EQUALS:
            case NOT_EQUALS2:
                return (columnValue != expectedValue);
            default:
                return false;
        }

    }

}
