package org.mlesyk.gwt.csvdashboard.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.mlesyk.Loggers;
import org.mlesyk.gwt.csvdashboard.client.TableLoadingService;
import org.mlesyk.gwt.csvdashboard.shared.dto.*;
import org.mlesyk.server.CsvManager;
import org.mlesyk.server.rules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by Maks on 23.09.2017.
 */
@SuppressWarnings("serial")
public class TableLoadingServiceImpl extends RemoteServiceServlet implements TableLoadingService {
    private final int ROWS_COUNT = 10;
    private CsvManager manager;
    private final Logger log = LoggerFactory.getLogger(Loggers.CSV_WEB);

    @Override
    public String[][] getFilteredTableData(AbstractRuleDTO ruleDTO) {
        List<AbstractRule> serverRules = manager.getRules();
        int nextRuleId = 0;
        if(serverRules.size() > 0) {
            nextRuleId = serverRules.get(serverRules.size() - 1).getId();
            nextRuleId++;
        }
        if(ruleDTO != null) {
            if (ruleDTO instanceof CalculateDTO) {
                Calculate rule = new Calculate(((CalculateDTO) ruleDTO).getFirstColumnId(),
                        ((CalculateDTO) ruleDTO).getSecondColumnId(),
                        ((CalculateDTO) ruleDTO).getOperation(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);
            } else if (ruleDTO instanceof ChangeColumnPositionDTO) {
                ChangeColumnPosition rule = new ChangeColumnPosition(((ChangeColumnPositionDTO) ruleDTO).getCurrentPosition(),
                        ((ChangeColumnPositionDTO) ruleDTO).getNewPosition(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);

            } else if (ruleDTO instanceof CloneDTO) {
                Clone rule = new Clone(((CloneDTO) ruleDTO).getColumnId(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);
            } else if (ruleDTO instanceof DeleteDTO) {
                Delete rule = new Delete(((DeleteDTO) ruleDTO).getColumnPosition(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);
            } else if (ruleDTO instanceof MathFilterDTO) {
                MathFilter rule = new MathFilter(((MathFilterDTO) ruleDTO).getColumnId(),
                        ((MathFilterDTO) ruleDTO).getCondition(),
                        ((MathFilterDTO) ruleDTO).getConditionValue(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);
            } else if (ruleDTO instanceof MergeColumnsDTO) {
                MergeColumns rule = new MergeColumns(((MergeColumnsDTO) ruleDTO).getFirstColumnId(),
                        ((MergeColumnsDTO) ruleDTO).getSecondColumnId(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);
            } else if (ruleDTO instanceof RegexFilterDTO) {
                RegexFilter rule = new RegexFilter(((RegexFilterDTO) ruleDTO).getColumnPosition(),
                        ((RegexFilterDTO) ruleDTO).getSearchType(),
                        ((RegexFilterDTO) ruleDTO).getSearchData(),
                        manager.getOutputColumns(),
                        nextRuleId);
                manager.addRule(rule);
            }
        }
        if(log.isDebugEnabled()) {
            log.debug("Added rule: {}", manager.getRules().get(manager.getRules().size() - 1));
        }
        return manager.generateTableView(ROWS_COUNT);
    }

    @Override
    public String[][] getTableData(String filePath) {
        String[][] result = null;
        log.info("CSV file {} passed to CsvManager", filePath);
        manager = new CsvManager(filePath);
        result = manager.generateTableView(ROWS_COUNT);
        if(log.isDebugEnabled()) {
            log.debug("First {} rows:", ROWS_COUNT);
            for(String[] row: result) {
                log.debug(Arrays.toString(row));
            }
        }
        return result;
    }

    @Override
    public String[][] removeRule(int ruleId) {
        log.info("Rule id {} removed", ruleId);

        return manager.removeRule(ruleId);

    }

    public Map<Integer, List<Integer>> updateRemovalMap() {
        return manager.getRemovalMap();
    }
}
