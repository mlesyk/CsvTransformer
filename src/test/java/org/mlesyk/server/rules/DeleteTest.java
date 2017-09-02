package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;

/**
 * Created by Maks on 27.08.2017.
 */
public class DeleteTest extends AbstractRuleTestRunner {

    @Test
    public void testDeleteFirstPosition() {
        String[] csvDataResult = {
                "column2,column3,column4",
                "data2,data3,data4"
        };
        int deletePosition = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runDeleteTest(deletePosition, csvDataResult), true);
    }

    @Test
    public void testDeleteLastPosition() {
        String[] csvDataResult = {
                "column1,column2,column3",
                "data1,data2,data3"
        };
        int deletePosition = 3;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runDeleteTest(deletePosition, csvDataResult), true);
    }

    @Test
    public void testDeleteMiddlePosition() {
        String[] csvDataResult = {
                "column1,column2,column4",
                "data1,data2,data4"
        };
        int deletePosition = 2;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runDeleteTest(deletePosition, csvDataResult), true);
    }
}