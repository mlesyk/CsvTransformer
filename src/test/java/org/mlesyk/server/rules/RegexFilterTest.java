package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.utils.RegexUtil;


/**
 * Created by Maks on 27.08.2017.
 */
public class RegexFilterTest extends AbstractRuleTest {

    @Test
    public void testRegexNumber() {
        String[] csvDataResult = {
                "1,column2,column3,column4",
                "1,data2,data3,data4"
        };
        int columnPosition = 0;
        String regex = "([0-9]+)";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyRegex(columnPosition, regex, csvDataResult), true);
    }

    @Test
    public void testRegexMail() {
        this.csvData = new String[] {
                "column1,column2,column3,column4",
                "email@address.com,data2,data3,data4"
        };
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] csvDataResult = {
                "email@address.com,data2,data3,data4"
        };
        int columnPosition = 0;
        String regex = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyRegex(columnPosition, regex, csvDataResult), true);
    }


    @Test
    public void testContains() {
        String[] csvDataResult = {
                "data1,data2,data3,data4"
        };
        int columnPosition = 0;
        String searchData = "ata";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyRegex(columnPosition, RegexUtil.CONTAINS, searchData, csvDataResult), true);
    }

    @Test
    public void testBeginsWith() {
        String[] csvDataResult = {
                "column1,column2,column3,column4",
        };
        int columnPosition = 0;
        String searchData = "col";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyRegex(columnPosition, RegexUtil.BEGINS_WITH, searchData, csvDataResult), true);
    }

    @Test
    public void testEndsWith() {
        String[] csvDataResult = {
                "column1,column2,column3,column4",
        };
        int columnPosition = 0;
        String searchData = "mn1";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyRegex(columnPosition, RegexUtil.ENDS_WITH, searchData, csvDataResult), true);
    }


    @Test
    public void testEquals() {
        String[] csvDataResult = {
                "data1,data2,data3,data4"
        };
        int columnPosition = 0;
        String searchData = "data1";

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(applyRegex(columnPosition, RegexUtil.EQUALS, searchData, csvDataResult), true);
    }

    private boolean applyRegex(int columnPosition, String regex, String[] expectedResult) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new RegexFilter(columnPosition, regex, manager.getOutputColumns()), column, expectedResult);
    }

    private boolean applyRegex(int columnPosition,int searchType, String searchData, String[] expectedResult) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new RegexFilter(columnPosition, searchType, searchData, manager.getOutputColumns()), column, expectedResult);
    }
}