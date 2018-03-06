package org.mlesyk.server.rules.helpers;

import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.rules.*;

/**
 * Created by Maks on 02.09.2017.
 */
public abstract class AbstractRuleTestRunner extends AbstractRuleTest {

    protected boolean runCalculationTest(int firstColumnId, int secondColumnId, String operation, String[] expectedResult, AbstractRule... rules) {
        return this.testRule(new Calculate(firstColumnId, secondColumnId, operation, manager.getOutputColumns(),0), expectedResult, rules);
    }

    protected boolean runChangeColumnPositionTest(int currentPosition, int newPosition, String[] expectedResult, AbstractRule... rules) {
        return this.testRule(new ChangeColumnPosition(currentPosition, newPosition, manager.getOutputColumns(),0),
                expectedResult, rules);
    }

    protected boolean runCloneTest(int columnPosition, String[] expectedResult, AbstractRule... rules) {
        return this.testRule(new Clone(columnPosition, manager.getOutputColumns(),0), expectedResult, rules);
    }

    protected boolean runDeleteTest(int deletePosition, String[] expectedResult) {
        return this.testRule(new Delete(deletePosition, manager.getOutputColumns(),0), expectedResult);
    }

    protected boolean runMathFilterTest(int columnId, String condition, double conditionValue, String[] expectedResult, AbstractRule... rules) {
        return this.testRule(new MathFilter(columnId, condition, conditionValue, manager.getOutputColumns(),0), expectedResult, rules);
    }

    protected boolean runMergeTest(int firstColumnId, int secondColumnId, String[] expectedResult,AbstractRule... rules) {
        return this.testRule(new MergeColumns(firstColumnId, secondColumnId, manager.getOutputColumns(),0), expectedResult, rules);
    }

    protected boolean runRegexTest(int columnPosition, int searchType, String searchData, String[] expectedResult, AbstractRule... rules) {
        return this.testRule(new RegexFilter(columnPosition, searchType, searchData, manager.getOutputColumns(),0), expectedResult, rules);
    }

    protected boolean runRegexTest(int columnPosition, String regex, String[] expectedResult,AbstractRule... rules) {
        return this.testRule(new RegexFilter(columnPosition, regex, manager.getOutputColumns(),0), expectedResult, rules);
    }
}
