package org.mlesyk.server.rules.integration;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.gwt.csvdashboard.shared.MathUtilConstants;
import org.mlesyk.server.rules.*;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;
import org.mlesyk.server.utils.MathUtil;

/**
 * Created by Maks on 02.09.2017.
 */
public class CloneIntegrationTest extends AbstractRuleTestRunner {

    @Test
    public void testCloneThenRegex() {
        String[] csvDataResult = {
                "1,column1,column2,column3,column4",
                "1,data1,data2,data3,data4"
        };
        int columnPosition = 0;
        String regex = "([0-9]+)";
        RegexFilter regexRule = new RegexFilter(columnPosition,regex, manager.getOutputColumns(),1);

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult, regexRule), true);
    }

    @Test
    public void testCloneThenChangePosition() {
        String[] csvDataResult = {
                "column1,column2,column3,column4,column1",
                "data1,data2,data3,data4,data1"
        };
        int columnPosition = 0;
        int newPosition = 4;
        ChangeColumnPosition positionRule = new ChangeColumnPosition(columnPosition, newPosition, manager.getOutputColumns(),1 );
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult, positionRule), true);
    }

    @Test
    public void testCloneThenMerge() {
        String[] csvDataResult = {
                "column1 column1,column2,column3,column4",
                "data1 data1,data2,data3,data4"
        };
        int columnPosition = 0;
        MergeColumns mergeRule = new MergeColumns(columnPosition, columnPosition + 1, manager.getOutputColumns(),1 );
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult, mergeRule), true);
    }

    @Test
    public void testCloneThenDelete() {
        String[] csvDataResult = {
                "column1,column2,column3,column4",
                "data1,data2,data3,data4"
        };
        int columnPosition = 0;
        Delete deleteRule = new Delete(columnPosition, manager.getOutputColumns(),1);
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult, deleteRule), true);

    }

    @Test
    public void testCloneThenClone() {
        String[] csvDataResult = {
                "column1,column1,column1,column2,column3,column4",
                "data1,data1,data1,data2,data3,data4"
        };
        int columnPosition = 0;
        Clone cloneRule = new Clone(columnPosition, manager.getOutputColumns(),1);
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult, cloneRule), true);

    }

    @Test
    public void testCloneThenCalculate() {
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
                "10,10,20.0,column2,column3,column4",
                "100,100,200.0,data2,data3,data4"
        };
        int columnPosition1 = 0;
        int columnPosition2 = 1;
        Calculate calculateRule = new Calculate(columnPosition1, columnPosition2, MathUtilConstants.ADD, manager.getOutputColumns(),1);
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition1, csvDataResult, calculateRule), true);

    }

    @Test
    public void testCloneThenMathFilter() {

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
                "100,100,data2,data3,data4"
        };
        int columnPosition = 0;
        int conditionValue = 20;
        MathFilter mathFilterRule = new MathFilter(columnPosition, MathUtilConstants.GREATER, conditionValue , manager.getOutputColumns(),1);
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult, mathFilterRule), true);

    }
}
