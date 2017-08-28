package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;

import static org.junit.Assert.*;

/**
 * Created by Maks on 29.08.2017.
 */
public class CloneTest extends AbstractRuleTest {

    @Test
    public void testCloneWithRegex() {
        String[] csvDataResult = {
                "column1,1,column2,column3,column4",
                "data1,1,data2,data3,data4"
        };
        int columnPosition = 0;
        String regex = "([0-9]+)";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyClone(columnPosition, regex, csvDataResult), true);
    }

    private boolean applyClone(int columnPosition, String regex, String[] expectedResult) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new Clone(column,
                manager.getOutputColumns(),
                new RegexFilter(column.getId() + 1,regex, manager.getOutputColumns())), column, expectedResult);
    }
}