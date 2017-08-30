package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;

/**
 * Created by Maks on 29.08.2017.
 */
public class CloneTest extends AbstractRuleTest {

    @Test
    public void testOnlyClone() {
        String[] csvDataResult = {
                "column1,column1,column2,column3,column4",
                "data1,data1,data2,data3,data4"
        };
        int columnPosition = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyClone(columnPosition, csvDataResult), true);
    }

    @Test
    public void testCloneWithRegex() {
        String[] csvDataResult = {
                "column1,1,column2,column3,column4",
                "data1,1,data2,data3,data4"
        };
        int columnPosition = 0;
        String regex = "([0-9]+)";
        RegexFilter regexRule = new RegexFilter(columnPosition + 1,regex, manager.getOutputColumns());

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyClone(columnPosition, csvDataResult, regexRule), true);
    }

    @Test
    public void testCloneWithRegexWithChangePosition() {
        String[] csvDataResult = {
                "column1,column2,column3,column4,1",
                "data1,data2,data3,data4,1"
        };
        int columnPosition = 0;
        String regex = "([0-9]+)";
        RegexFilter regexRule = new RegexFilter(columnPosition + 1,regex, manager.getOutputColumns());
        ChangeColumnPosition positionRule = new ChangeColumnPosition(columnPosition + 1,manager.getOutputColumns().size(), manager.getOutputColumns() );
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyClone(columnPosition, csvDataResult, regexRule, positionRule), true);
    }

    @Test
    public void testCloneWithRegexWithMerge() {
        String[] csvDataResult = {
                "column1 1,column2,column3,column4",
                "data1 1,data2,data3,data4"
        };
        int columnPosition = 0;
        String regex = "([0-9]+)";
        RegexFilter regexRule = new RegexFilter(columnPosition + 1,regex, manager.getOutputColumns());
        MergeColumns mergeRule = new MergeColumns(columnPosition, columnPosition + 1, manager.getOutputColumns() );
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyClone(columnPosition, csvDataResult, regexRule, mergeRule), true);
    }

    private boolean applyClone(int columnPosition, String[] expectedResult, AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new Clone(columnPosition, manager.getOutputColumns(), rules), column, expectedResult);
    }
}