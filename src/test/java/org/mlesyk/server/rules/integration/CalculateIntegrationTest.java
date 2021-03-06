package org.mlesyk.server.rules.integration;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.gwt.csvdashboard.shared.MathUtilConstants;
import org.mlesyk.gwt.csvdashboard.shared.RegexUtilConstants;
import org.mlesyk.server.rules.*;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;
import org.mlesyk.server.utils.MathUtil;
import org.mlesyk.server.utils.RegexUtil;

/**
 * Created by Maks on 02.09.2017.
 */
public class CalculateIntegrationTest extends AbstractRuleTestRunner {

    @Test
    public void testDivideThenChangePosition() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,500,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,column3,column4,0.5",
                "100,500,data3,data4,0.2"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        int calculatedCurrentPosition = 2;
        int calculatedNewPosition = 4;
        ChangeColumnPosition positionRule = new ChangeColumnPosition(calculatedCurrentPosition, calculatedNewPosition, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCalculationTest(firstColumnId, secondColumnId, MathUtilConstants.DIVIDE, csvDataResult, positionRule), true);
    }

    @Test
    public void testAddThenClone() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,500,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,30.0,30.0,column3,column4",
                "100,500,600.0,600.0,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        int calculatedCurrentPosition = 2;
        Clone cloneRule = new Clone(calculatedCurrentPosition, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCalculationTest(firstColumnId, secondColumnId, MathUtilConstants.ADD, csvDataResult, cloneRule), true);
    }

    @Test
    public void testMultiplyThenMathFilter() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,500,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "100,500,50000.0,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        int calculatedCurrentPosition = 2;
        int conditionValue = 500;
        MathFilter mathFilterRule = new MathFilter(calculatedCurrentPosition, MathUtilConstants.GREATER, conditionValue, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCalculationTest(firstColumnId, secondColumnId, MathUtilConstants.MULTIPLY, csvDataResult, mathFilterRule), true);
    }

    @Test
    public void testSubtractThenMerge() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,500,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,-10.0 column3,column4",
                "100,500,-400.0 data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        int calculatedCurrentPosition = 2;
        int mergeColumnId = 3;
        MergeColumns mergeRule = new MergeColumns(calculatedCurrentPosition, mergeColumnId, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCalculationTest(firstColumnId, secondColumnId, MathUtilConstants.SUBTRACT, csvDataResult, mergeRule), true);
    }

    @Test
    public void testModThenRegexFilter() {
        this.csvData = new String[] {
                "-10,20,column3,column4",
                "100,500,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "-10,20,-10.0,column3,column4",
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        int calculatedCurrentPosition = 2;
        String searchData = "-";
        RegexFilter regexRule = new RegexFilter(calculatedCurrentPosition, RegexUtilConstants.BEGINS_WITH, searchData, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCalculationTest(firstColumnId, secondColumnId, MathUtilConstants.MOD, csvDataResult, regexRule), true);
    }
}
