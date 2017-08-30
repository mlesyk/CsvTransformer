package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.MathUtil;

/**
 * Created by Maks on 29.08.2017.
 */
public class CalculateTest extends AbstractRuleTest {

    @Test
    public void testDivideFirstSecondPosition() {
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
                "10,20,0.5,column3,column4",
                "100,500,0.2,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyCalculation(firstColumnId, secondColumnId, MathUtil.DIVIDE, csvDataResult), true);
    }

    @Test
    public void testMultiplyFirstLastPosition() {
        this.csvData = new String[] {
                "10,20,column3,0.4",
                "100,500,data3,0.01"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,column3,0.4,4.0",
                "100,500,data3,0.01,1.0"
        };
        int firstColumnId = 0;
        int secondColumnId = 3;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyCalculation(firstColumnId, secondColumnId, MathUtil.MULTIPLY, csvDataResult), true);
    }

    @Test
    public void testAddSecondThirdPosition() {
        this.csvData = new String[] {
                "10,20,3,0.4",
                "100,500,3,0.01"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,3,23.0,0.4",
                "100,500,3,503.0,0.01"
        };
        int firstColumnId = 1;
        int secondColumnId = 2;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyCalculation(firstColumnId, secondColumnId, MathUtil.ADD, csvDataResult), true);
    }

    @Test
    public void testSubtractLastFirstPosition() {
        this.csvData = new String[] {
                "10,20,3,0.4",
                "100,500,3,0.01"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,3,0.4,-9.6",
                "100,500,3,0.01,-99.99"
        };
        int firstColumnId = 3;
        int secondColumnId = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyCalculation(firstColumnId, secondColumnId, MathUtil.SUBTRACT, csvDataResult), true);
    }

    @Test
    public void testModulusThirdFirstPosition() {
        this.csvData = new String[] {
                "10,20,3,0.4",
                "100,500,3,0.01"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "10,20,3,3.0,0.4",
                "100,500,3,3.0,0.01"
        };
        int firstColumnId = 2;
        int secondColumnId = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyCalculation(firstColumnId, secondColumnId, MathUtil.MOD, csvDataResult), true);
    }


    private boolean applyCalculation(int firstColumnId, int secondColumnId, String operation, String[] expectedResult) {
        ResultColumn column = manager.getOutputColumns().get(firstColumnId);
        return this.testRule(new Calculate(firstColumnId, secondColumnId, operation, manager.getOutputColumns()), column, expectedResult);
    }

}