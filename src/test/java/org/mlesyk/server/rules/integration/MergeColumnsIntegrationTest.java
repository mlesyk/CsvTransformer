package org.mlesyk.server.rules.integration;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.rules.ChangeColumnPosition;
import org.mlesyk.server.rules.Clone;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;

/**
 * Created by Maks on 02.09.2017.
 */
public class MergeColumnsIntegrationTest extends AbstractRuleTestRunner {

    @Test
    public void testMergeThenChangePosition() {
        String[] csvDataResult = {
                "column3,column4,column1 column2",
                "data3,data4,data1 data2"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        int newColumnPosition = 3;

        ChangeColumnPosition changeColumnPositionRule = new ChangeColumnPosition(firstColumnId, newColumnPosition, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMergeTest(firstColumnId, secondColumnId, csvDataResult, changeColumnPositionRule), true);
    }

    @Test
    public void testMergeThenClone() {
        String[] csvDataResult = {
                "column1 column2,column1 column2,column3,column4",
                "data1 data2,data1 data2,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        Clone cloneRule = new Clone(firstColumnId, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMergeTest(firstColumnId, secondColumnId, csvDataResult, cloneRule), true);
    }

}