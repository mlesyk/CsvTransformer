package org.mlesyk.server.utils;

/**
 * Created by Maks on 24.08.2017.
 */
public final class MathUtil {

    public final static String DIVIDE = "/";
    public final static String MULTIPLY = "*";
    public final static String SUBTRACT = "-";
    public final static String ADD = "+";
    public final static String MOD = "%";

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

}
