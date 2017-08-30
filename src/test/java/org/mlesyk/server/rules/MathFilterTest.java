package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

/**
 * Created by Maks on 30.08.2017.
 */
public class MathFilterTest extends AbstractRuleTest {

    @Test
    public void testGreaterCondition() {
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
                "100,500,data3,data4"
        };
        int columnId = 1;
        double conditionValue = 50;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.GREATER, conditionValue, csvDataResult), true);
    }

    @Test
    public void testGreaterEqualsCondition() {
        this.csvData = new String[] {
                "10,20,column3,column4",
                "100,50,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "100,50,data3,data4"
        };
        int columnId = 1;
        double conditionValue = 50;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.GREATER_EQUAL, conditionValue, csvDataResult), true);
    }

    @Test
    public void testLesserCondition() {
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
                "10,20,column3,column4"
        };
        int columnId = 1;
        double conditionValue = 50;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.LESSER, conditionValue, csvDataResult), true);
    }

    @Test
    public void testLesserEqualsCondition() {
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
                "10,20,column3,column4"
        };
        int columnId = 1;
        double conditionValue = 20;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.LESSER_EQUAL, conditionValue, csvDataResult), true);
    }

    @Test
    public void testEqualsCondition() {
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
                "10,20,column3,column4"
        };
        int columnId = 1;
        double conditionValue = 20;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.EQUALS, conditionValue, csvDataResult), true);
    }

    @Test
    public void testEquals2Condition() {
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
                "10,20,column3,column4"
        };
        int columnId = 1;
        double conditionValue = 20;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.EQUALS2, conditionValue, csvDataResult), true);
    }

    @Test
    public void testNotEqualsCondition() {
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
                "100,500,data3,data4"
        };
        int columnId = 1;
        double conditionValue = 20;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.NOT_EQUALS, conditionValue, csvDataResult), true);
    }

    @Test
    public void testNotEquals2Condition() {
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
                "10,20,column3,column4"
        };
        int columnId = 1;
        double conditionValue = 500;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyMathFilter(columnId, MathUtil.NOT_EQUALS2, conditionValue, csvDataResult), true);
    }


    private boolean applyMathFilter(int columnId, String condition, double conditionValue, String[] expectedResult) {
        ResultColumn column = manager.getOutputColumns().get(columnId);
        return this.testRule(new MathFilter(columnId, condition, conditionValue, manager.getOutputColumns()), column, expectedResult);
    }

}