package org.mlesyk.server.rules;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.server.ResultColumn;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Maks on 26.08.2017.
 */
public class ChangeColumnPositionTest extends AbstractRuleTest {

    @Test
    public void testFirstMoveToLastPosition() {
        String[] csvDataResult = {
                "column2,column3,column4,column1",
                "data2,data3,data4,data1"
        };
        int currentPosition = 0;
        int newPosition = 3;

        //https://stackoverflow.com/questions/442747/getting-the-name-of-the-currently-executing-method
        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(changeColumnOrder(currentPosition, newPosition, csvDataResult), true);
    }

    @Test
    public void testLastMoveToFirstPosition() {
        String[] csvDataResult = {
                "column4,column1,column2,column3",
                "data4,data1,data2,data3"
        };
        int currentPosition = 3;
        int newPosition = 0;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(changeColumnOrder(currentPosition, newPosition, csvDataResult), true);
    }

    @Test
    public void testOnePositionRightMove() {
        String[] csvDataResult = {
                "column1,column3,column2,column4",
                "data1,data3,data2,data4"
        };
        int currentPosition = 1;
        int newPosition = 2;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(changeColumnOrder(currentPosition,newPosition,csvDataResult), true);
    }

    @Test
    public void testOnePositionLeftMove() {
        String[] csvDataResult = {
                "column1,column3,column2,column4",
                "data1,data3,data2,data4"
        };
        int currentPosition = 2;
        int newPosition = 1;

        System.out.println("Test " + new Object(){}.getClass().getEnclosingMethod().getName());
        Assert.assertEquals(changeColumnOrder(currentPosition,newPosition,csvDataResult), true);
    }

    private boolean changeColumnOrder(int currentPosition, int newPosition, String[] expectedResult) {
        boolean result = true;
        ResultColumn column = manager.getOutputColumns().get(currentPosition);
        column.addRule(new ChangeColumnPosition(column, newPosition, manager.getOutputColumns()));
        manager.writeOutputFile();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(manager.getOutputFilePath()))) {
            for(int i = 0; i < expectedResult.length; i++) {
                String[] resultLine = reader.readLine().split(",");
                String[] testLine = expectedResult[i].split(",");
                System.out.println(Arrays.toString(resultLine));
                for(int j = 0; j < testLine.length; j++) {
                    if(!testLine[j].equals(resultLine[j])) {
                        result = false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}