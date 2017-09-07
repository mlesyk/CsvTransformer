package org.mlesyk.server.rules.integration;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.rules.*;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;
import org.mlesyk.server.utils.MathUtil;
import org.mlesyk.server.utils.RegexUtil;

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

    @Test
    public void testMergeThenDelete() {
        String[] csvDataResult = {
                "column3,column4",
                "data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;

        Delete deleteRule = new Delete(firstColumnId, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMergeTest(firstColumnId, secondColumnId, csvDataResult, deleteRule), true);
    }

    @Test
    public void testMergeThenMerge() {
        String[] csvDataResult = {
                "column1 column2 column3,column4",
                "data1 data2 data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;
        int thirdColumnId = 2;

        MergeColumns mergeRule = new MergeColumns(firstColumnId, thirdColumnId, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMergeTest(firstColumnId, secondColumnId, csvDataResult, mergeRule), true);
    }

    @Test
    public void testMergeThenRegex() {
        String[] csvDataResult = {
                "data1 data2,data3,data4"
        };
        int firstColumnId = 0;
        int secondColumnId = 1;
        int condition = RegexUtil.CONTAINS;
        String searchData = "data";

        RegexFilter regexRule = new RegexFilter(firstColumnId, condition, searchData, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runMergeTest(firstColumnId, secondColumnId, csvDataResult, regexRule), true);
    }

}
