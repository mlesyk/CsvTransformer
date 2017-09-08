package org.mlesyk.server.rules.integration;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.rules.*;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;
import org.mlesyk.server.utils.MathUtil;
import org.mlesyk.server.utils.RegexUtil;

/**
 * Created by Maks on 07.09.2017.
 */
public class RegexFilterIntegrationTest extends AbstractRuleTestRunner {

    @Test
    public void testRegexThenCalculate() {
        this.csvData = new String[] {
                "10,column2,column3,column4",
                "100,data2,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,2,12.0,column3,column4",
                "100,2,102.0,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        String condition = "\\d$";

        Calculate calculateRule = new Calculate(firstColumnId, secondColumnId, MathUtil.ADD, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runRegexTest(secondColumnId,condition,csvDataResult, calculateRule), true);
    }

    @Test
    public void testRegexThenChangePosition() {
        String[] csvDataResult = {
                "column2,column3,column4,1",
                "data2,data3,data4,1"
        };
        int firstColumnId = 0;
        int newColumnId = 3;

        String condition = "\\d$";

        ChangeColumnPosition positionRule = new ChangeColumnPosition(firstColumnId, newColumnId, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runRegexTest(firstColumnId,condition,csvDataResult, positionRule), true);
    }

    @Test
    public void testRegexThenClone() {
        String[] csvDataResult = {
                "1,1,column2,column3,column4",
                "1,1,data2,data3,data4"
        };
        int firstColumnId = 0;

        String condition = "\\d$";

        Clone cloneRule = new Clone(firstColumnId, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runRegexTest(firstColumnId,condition,csvDataResult, cloneRule), true);
    }

    @Test
    public void testRegexThenMathFilter() {
        this.csvData = new String[] {
                "asdas10,column2,column3,column4",
                "asdadas100,data2,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "100,data2,data3,data4"
        };

        int firstColumnId = 0;

        String condition = "\\d+$";
        int mathConditionValue = 50;
        String mathCondition = MathUtil.GREATER;

        MathFilter mathFilterRule = new MathFilter(firstColumnId, mathCondition, mathConditionValue, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runRegexTest(firstColumnId,condition,csvDataResult, mathFilterRule), true);
    }

    @Test
    public void testRegexThenMerge() {

        String[] csvDataResult = {
                "1 column3,column2,column4",
                "1 data3,data2,data4"
        };

        int firstColumnId = 0;
        int secondColumnId = 2;

        String condition = "\\d+$";

        MergeColumns mergeRule = new MergeColumns(firstColumnId, secondColumnId, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runRegexTest(firstColumnId,condition,csvDataResult, mergeRule), true);
    }


    @Test
    public void testRegexThenRegex() {

        String[] csvDataResult = {
                "column1,column2,column3,column4",
        };

        int firstColumnId = 0;

        int condition1 = RegexUtil.BEGINS_WITH;
        String conditionValue1 = "col";
        int condition2 = RegexUtil.ENDS_WITH;
        String conditionValue2 = "1";


        RegexFilter regexRule = new RegexFilter(firstColumnId, condition1, conditionValue1, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runRegexTest(firstColumnId,condition2, conditionValue2,csvDataResult, regexRule), true);
    }
}
