package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.rules.helpers.AbstractRuleTestRunner;

/**
 * Created by Maks on 29.08.2017.
 */
public class CloneTest extends AbstractRuleTestRunner {

    @Test
    public void testClone() {
        String[] csvDataResult = {
                "column1,column1,column2,column3,column4",
                "data1,data1,data2,data3,data4"
        };
        int columnPosition = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(runCloneTest(columnPosition, csvDataResult), true);
    }
}