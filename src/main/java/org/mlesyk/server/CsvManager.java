package org.mlesyk.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Maks on 26.08.2017.
 */
public class CsvManager {
    private File inputFile;
    private String inputFilePath;
    private File outputFile;
    private String outputFilePath;
    private List<ResultColumn> outputColumns;

    public CsvManager(File inputFile) {
        this.inputFile = inputFile;
        this.inputFilePath = inputFile.getPath();
        this.outputFile = new File(inputFile.getParent() + "/outputFile.csv");
        this.outputFilePath = outputFile.getPath();
        this.init();
    }

    public CsvManager(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.inputFilePath = inputFile.getPath();
        this.outputFile = outputFile;
        this.outputFilePath = outputFile.getPath();
        this.init();
    }

    public void init() {
        if (outputFile.length() != 0) {
            outputFile.delete();
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line = br.readLine();
            outputColumns = new ArrayList<ResultColumn>();
            // counter should be cleared on each new file processing
            // TODO: implement better approach
            ResultColumn.setCounter(0);
            for (int i = 0; i < line.split(",").length; i++) {
                outputColumns.add(new ResultColumn());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOutputFile() {
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
            e.printStackTrace();
        }
    }

    public String applyRules(String line) {
        String[] cells = line.split(",");
        outputColumns.forEach(resultColumn -> {
                    try {
                        resultColumn.setData(cells[resultColumn.getSourceFileColumnId()]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Line [" + line + "] has lesser than " + cells.length + " cells, ignored.");
                    }
                }
        );
        for (int i = 0; i < outputColumns.size(); i++) {  //used simple iteration to be able add new columns without ConcurrentModificationException
            ResultColumn column = outputColumns.get(i);
            if(column.isIterated()) {
                continue;
            }
            column.applyAllRules();
            column.setIterated(true);
        }
        for(ResultColumn column: outputColumns) {
            column.setIterated(false);
        }
        if (outputColumns.stream().anyMatch(ResultColumn::isSkipRow)) {
            return ""; // MathFilter
        } else {
            return outputColumns.stream()
                    .filter(resultColumn -> !resultColumn.isDeleted()) // Delete
                    .sorted() // sorted order of columns by id
                    .map(ResultColumn::getData)
                    .collect(Collectors.joining(","));
        }
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public List<ResultColumn> getOutputColumns() {
        return outputColumns;
    }
}
