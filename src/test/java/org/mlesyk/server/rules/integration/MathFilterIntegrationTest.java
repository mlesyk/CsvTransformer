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
public class MathFilterIntegrationTest extends AbstractRuleTestRunner {

    @Test
    public void testMathFilterThenCalculate() {
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
                "100,500,600.0,data3,data4"
        };
        int filterColumnId = 0;
        double conditionValue = 50;
        int columnPosition2 = 1;

        Calculate calculateRule = new Calculate(filterColumnId, columnPosition2, MathUtilConstants.ADD, manager.getOutputColumns(),1);


        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId, MathUtilConstants.GREATER, conditionValue, csvDataResult, calculateRule), true);
    }

    @Test
    public void testMathFilterThenChangePosition() {
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
                "500,100,data3,data4"
        };
        int filterColumnId = 0;
        double conditionValue = 50;
        int newColumnPosition = 1;

        ChangeColumnPosition changePositionRule = new ChangeColumnPosition(filterColumnId, newColumnPosition, manager.getOutputColumns(),1);


        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId, MathUtilConstants.GREATER, conditionValue, csvDataResult, changePositionRule), true);
    }

    @Test
    public void testMathFilterThenClone() {
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
                "100,100,500,data3,data4"
        };
        int filterColumnId = 0;
        double conditionValue = 50;

        Clone cloneRule = new Clone(filterColumnId, manager.getOutputColumns(),1);


        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId, MathUtilConstants.GREATER, conditionValue, csvDataResult, cloneRule), true);
    }

    @Test
    public void testMathFilterThenDelete() {
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
                "500,data3,data4"
        };
        int filterColumnId = 0;
        double conditionValue = 50;

        Delete deleteRule = new Delete(filterColumnId, manager.getOutputColumns(),1);


        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId, MathUtilConstants.GREATER, conditionValue, csvDataResult, deleteRule), true);
    }

    @Test
    public void testMathFilterThenMathFilter() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,500,data3,data4",
                "100,1000,data5,data6"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "100,500,data3,data4"
        };
        int filterColumnId1 = 0;
        int filterColumnId2 = 1;
        double conditionValue1 = 50;
        double conditionValue2 = 600;


        MathFilter mathFilterRule = new MathFilter(filterColumnId2, MathUtilConstants.LESSER, conditionValue2, manager.getOutputColumns(),1);


        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId1, MathUtilConstants.GREATER, conditionValue1, csvDataResult, mathFilterRule), true);
    }

    @Test
    public void testMathFilterThenMerge() {
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
                "100 500,data3,data4"
        };
        int filterColumnId = 0;
        double conditionValue = 50;
        int mergeColumnId = 1;

        MergeColumns mergeRule = new MergeColumns(filterColumnId, mergeColumnId, manager.getOutputColumns(),1);


        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId, MathUtilConstants.GREATER, conditionValue, csvDataResult, mergeRule), true);
    }

    @Test
    public void testMathFilterThenRegex() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,500,data3,data4",
                "100,1000,data5,data6"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "100,1000,data5,data6"
        };
        int filterColumnId = 0;
        double conditionValue = 50;
        int regexColumnId = 2;
        int regexCondition = RegexUtilConstants.ENDS_WITH;
        String regexData = "5";

        RegexFilter regexRule = new RegexFilter(regexColumnId, regexCondition, regexData, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMathFilterTest(filterColumnId, MathUtilConstants.GREATER, conditionValue, csvDataResult, regexRule), true);
    }
}
