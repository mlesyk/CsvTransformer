package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;

import static org.junit.Assert.*;

/**
 * Created by Maks on 27.08.2017.
 */
public class MergeColumnsTest extends AbstractRuleTest {

    @Test
    public void testMergeSecondToFirstPosition() {
        String[] csvDataResult = {
                "column1 column2,column3,column4",
                "data1 data2,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(mergeColumn(firstColumnId, secondColumnId, csvDataResult), true);
    }

    @Test
    public void testMergeLastToFirstPosition() {
        String[] csvDataResult = {
                "column1 column4,column2,column3",
                "data1 data4,data2,data3"
        };
        int firstColumnId = 0;
        int secondColumnId = 3;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(mergeColumn(firstColumnId, secondColumnId, csvDataResult), true);
    }

    @Test
    public void testMergeFirstToLastPosition() {
        String[] csvDataResult = {
                "column2,column3,column1 column4",
                "data2,data3,data1 data4"
        };
        int firstColumnId = 3;
        int secondColumnId = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(mergeColumn(firstColumnId, secondColumnId, csvDataResult), true);
    }

    private boolean mergeColumn(int firstColumnId, int secondColumnId, String[] expectedResult) {
        ResultColumn firstColumn = manager.getOutputColumns().get(firstColumnId);
        ResultColumn secondColumn = manager.getOutputColumns().get(secondColumnId);
        return this.testRule(new MergeColumns(firstColumn, secondColumn), firstColumn, expectedResult);
    }



}