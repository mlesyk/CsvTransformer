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
public class ChangeColumnPositionIntegrationTest extends AbstractRuleTestRunner {

    @Test
    public void testChangePositionThenCalculate() {
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
                "20,column3,column4,10,0.5",
                "500,data3,data4,100,0.2"
        };

        int currentPosition = 0;
        int newPosition = 3;
        String operation = MathUtilConstants.DIVIDE;

        int calculateSecondColumnId = 0;
        Calculate calculateRule = new Calculate(newPosition, calculateSecondColumnId, operation, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runChangeColumnPositionTest(currentPosition, newPosition, csvDataResult, calculateRule), true);
    }

    @Test
    public void testChangePositionThenClone() {
        String[] csvDataResult = {
                "column2,column3,column4,column1,column1",
                "data2,data3,data4,data1,data1"
        };

        int currentPosition = 0;
        int newPosition = 3;

        Clone cloneRule = new Clone(newPosition, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runChangeColumnPositionTest(currentPosition, newPosition, csvDataResult, cloneRule), true);
    }

    @Test
    public void testChangePositionThenDelete() {
        String[] csvDataResult = {
                "column2,column3,column4",
                "data2,data3,data4"
        };

        int currentPosition = 0;
        int newPosition = 3;

        Delete deleteRule = new Delete(newPosition, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runChangeColumnPositionTest(currentPosition, newPosition, csvDataResult, deleteRule), true);
    }

    @Test
    public void testChangePositionThenMathFilter() {
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
                "500,data3,data4,100"
        };

        int currentPosition = 0;
        int newPosition = 3;
        int conditionValue = 20;

        MathFilter mathFilterRule = new MathFilter(currentPosition,MathUtilConstants.GREATER,conditionValue, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runChangeColumnPositionTest(currentPosition, newPosition, csvDataResult, mathFilterRule), true);
    }

    @Test
    public void testChangePositionThenMerge() {
        String[] csvDataResult = {
                "column3,column4,column1 column2",
                "data3,data4,data1 data2"
        };

        int currentPosition = 0;
        int newPosition = 3;

        int secondColumnPosition = 0;

        MergeColumns mergeRule = new MergeColumns(newPosition, secondColumnPosition, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runChangeColumnPositionTest(currentPosition, newPosition, csvDataResult, mergeRule), true);
    }

    @Test
    public void testChangePositionThenRegex() {
        String[] csvDataResult = {
                "column2,column3,column4,column1"
        };

        int currentPosition = 0;
        int newPosition = 3;

        String regexData = "colu";

        RegexFilter regexRule = new RegexFilter(currentPosition, RegexUtilConstants.BEGINS_WITH, regexData, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runChangeColumnPositionTest(currentPosition, newPosition, csvDataResult, regexRule), true);
    }

}
