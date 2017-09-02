package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;
import org.mlesyk.server.utils.MathUtil;

/**
 * Created by Maks on 30.08.2017.
 */
public class MathFilterTest extends AbstractRuleTestRunner {

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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.GREATER, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.GREATER_EQUAL, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.LESSER, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.LESSER_EQUAL, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.EQUALS, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.EQUALS2, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.NOT_EQUALS, conditionValue, csvDataResult), true);
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
        Assert.assertEquals(runMathFilterTest(columnId, MathUtil.NOT_EQUALS2, conditionValue, csvDataResult), true);
    }
}