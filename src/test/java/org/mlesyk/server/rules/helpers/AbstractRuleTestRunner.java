package org.mlesyk.server.rules.helpers;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.rules.*;

/**
 * Created by Maks on 02.09.2017.
 */
public abstract class AbstractRuleTestRunner extends AbstractRuleTest {

    protected boolean runCalculationTest(int firstColumnId, int secondColumnId, String operation, String[] expectedResult, AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(firstColumnId);
        return this.testRule(new Calculate(firstColumnId, secondColumnId, operation, manager.getOutputColumns()), column, expectedResult, rules);
    }

    protected boolean runChangeColumnPositionTest(int currentPosition, int newPosition, String[] expectedResult, AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(currentPosition);
        return this.testRule(new ChangeColumnPosition(currentPosition, newPosition, manager.getOutputColumns()),
                column,expectedResult, rules);
    }

    protected boolean runCloneTest(int columnPosition, String[] expectedResult, AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new Clone(columnPosition, manager.getOutputColumns()), column, expectedResult, rules);
    }

    protected boolean runDeleteTest(int deletePosition, String[] expectedResult) {
        ResultColumn column = manager.getOutputColumns().get(deletePosition);
        return this.testRule(new Delete(deletePosition, manager.getOutputColumns()), column, expectedResult);
    }

    protected boolean runMathFilterTest(int columnId, String condition, double conditionValue, String[] expectedResult, AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(columnId);
        return this.testRule(new MathFilter(columnId, condition, conditionValue, manager.getOutputColumns()), column, expectedResult, rules);
    }

    protected boolean runMergeTest(int firstColumnId, int secondColumnId, String[] expectedResult,AbstractRule... rules) {
        ResultColumn firstColumn = manager.getOutputColumns().get(firstColumnId);
        return this.testRule(new MergeColumns(firstColumnId, secondColumnId, manager.getOutputColumns()),
                firstColumn, expectedResult, rules);
    }

    protected boolean runRegexTest(int columnPosition, int searchType, String searchData, String[] expectedResult, AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new RegexFilter(columnPosition, searchType, searchData, manager.getOutputColumns()), column, expectedResult, rules);
    }

    protected boolean runRegexTest(int columnPosition, String regex, String[] expectedResult,AbstractRule... rules) {
        ResultColumn column = manager.getOutputColumns().get(columnPosition);
        return this.testRule(new RegexFilter(columnPosition, regex, manager.getOutputColumns()), column, expectedResult, rules);
    }
}
