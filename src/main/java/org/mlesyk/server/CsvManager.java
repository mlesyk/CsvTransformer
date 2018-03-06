package org.mlesyk.server;

import org.mlesyk.Loggers;
import org.mlesyk.server.rules.AbstractRule;
import org.mlesyk.server.rules.Calculate;
import org.mlesyk.server.rules.ChangeColumnPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CsvManager is the main class for CSV file formatting
 * it uses ordered list of ResultColumn entities, which represents CSV columns
 * list of AbstractRule entities are applied one by one to the columns list
 * there are two public methods:
 * <ul>
 * <li> generateTableView method allows to compose list of columns and list of rules to two-dimensional array for table representation on GUI
 * <li> writeOutputFile method allows to apply all rules on input CSV file and return another CSV file as a result
 * </ul>
 *
 * @author Maks Lesyk
 */
public class CsvManager {
    private final Logger log = LoggerFactory.getLogger(Loggers.CSV_CORE);
    private String inputFilePath;
    private File outputFile;
    private String outputFilePath;
    private List<ResultColumn> outputColumns;
    private List<AbstractRule> rules;
    private Map<Integer, List<Integer>> removalMap = new HashMap<Integer, List<Integer>>();

    /**
     * Constructor allows to use output file in the same folder as a source file
     *
     * @param inputFilePath Source CSV file path
     */
    public CsvManager(String inputFilePath) {
        this(inputFilePath, Paths.get(inputFilePath).getParent()
                + "/outputFile_"
                + Paths.get(inputFilePath).getFileName()
                + ".csv");
    }

    /**
     * Constructor allows to specify input and output file locations
     *
     * @param inputFilePath  Source CSV file path
     * @param outputFilePath Target CSV file path
     */
    public CsvManager(String inputFilePath, String outputFilePath) {
        rules = new ArrayList<AbstractRule>();
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.init();
    }

    /**
     * Format CSV file with rules and convert to table view
     *
     * @param size allows to return limited number of rows for better performance
     * @return <code>String[][]</code> which is table representstion of CSV file
     */
    public String[][] generateTableView(int size) {
        log.info("Table view generating started with row count {}", size);
        String[][] tableView = null;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null && i < size) {
                String rowData = this.applyRules(line);
                if (rowData.equals("")) {
                    continue;
                }
                String[] row = rowData.split(",");
                if (null == tableView) {
                    tableView = new String[size][row.length];
                }
                tableView[i] = row;
                i++;
            }
        } catch (IOException e) {
            log.error("File reading failed.", e);
        }
        return tableView;
    }

    public String[][] removeRule(int ruleId) {
        AbstractRule rule = rules.get(ruleId);
        List<Integer> relatedRules = removalMap.get(ruleId);
        for (int i = relatedRules.size() - 1; i > 0; i--) {
            rules.get(relatedRules.get(i).intValue()).rollback();
            rules.remove(relatedRules.get(i).intValue());
        }
        rule.rollback();
        rules.remove(ruleId);
        return generateTableView(10);
    }

    /**
     * Format CSV file with rules and upload result to output file
     */
    public void writeOutputFile() {
        log.info("Write to output file {} started", outputFilePath);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))
        ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = this.applyRules(line);
                if (!line.equals("")) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            log.error("Write to output file failed.", e);
        }
    }

    /**
     * Apply chain of rules for row of CSV file
     *
     * @param line represents row of CSV file
     * @return CSV-formatted <code>String</code>
     */
    private String applyRules(String line) {
        String[] cells = line.split(",");
        outputColumns.forEach(resultColumn -> {
                    try {
                        resultColumn.setData(cells[resultColumn.getSourceFileColumnId()]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        log.error("Line [ {} ] has lesser than {} cells, ignored.", line, cells.length);
                    }
                }
        );
        for (AbstractRule rule : rules) {
            rule.apply();
        }

        if (outputColumns.stream().anyMatch(ResultColumn::isSkipRow)) {
            return ""; // MathFilter, RegexFilter
        } else {
            return outputColumns.stream()
                    .filter(resultColumn -> !resultColumn.isDeleted()) // Delete
                    .sorted() // sorted order of columns by id
                    .map(ResultColumn::getData)
                    .collect(Collectors.joining(","));
        }
    }

    /**
     * Clean outputFile if already exists
     * Initialize ResultColumn list by reading input file first line and split it with separator
     */
    private void init() {
        File outputFile = new File(outputFilePath);
        if (outputFile.length() != 0) {
            log.info("File {} exists, removing and creating new", outputFile.getName());
            outputFile.delete();
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                log.error("Create new file with name {} failed.", outputFile.getName(), e);
            }
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line = br.readLine();
            outputColumns = new ArrayList<ResultColumn>();
            for (int i = 0; i < line.split(",").length; i++) {
                outputColumns.add(new ResultColumn(i));
            }
            log.debug("First line: {}. Calculating columns: {}", line, line.split(",").length);

        } catch (IOException e) {
            log.error("File reading failed.", e);
        }
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public List<ResultColumn> getOutputColumns() {
        return outputColumns;
    }

    public List<AbstractRule> getRules() {
        return rules;
    }

    public void setRules(List<AbstractRule> rules) {
        this.rules = rules;
    }

    public void addRule(AbstractRule rule) {
        rules.add(rule);
    }

    public void addAllRules(AbstractRule[] rules) {
        this.rules.addAll(Arrays.asList(rules));
    }

    private void buildRulesRelationMap() {
        outerLoop:
        for (int i = rules.size() - 1; i >= 0; i--) {
            AbstractRule rule = rules.get(i);
            int ruleId = rule.getId();
            for (int j = i - 1; j >= 0; j++) {
                for (int currentRuleColumn : rule.getResultColumnId()) {
                    for (int prevRuleColumn : rules.get(j).getResultColumnId()) {
                        if (prevRuleColumn == currentRuleColumn) {
                            rules.get(j).getRulesRemovalIds().add(ruleId);
                            rules.get(j).getRulesRemovalIds().addAll(rule.getRulesRemovalIds());
                            continue outerLoop;
                        }
                    }
                }
            }
        }

        for (AbstractRule rule : rules) {
            removalMap.put(rule.getId(), rule.getRulesRemovalIds());
        }
    }

    public Map<Integer, List<Integer>> getRemovalMap () {
        buildRulesRelationMap();
        return removalMap;
    }
}
