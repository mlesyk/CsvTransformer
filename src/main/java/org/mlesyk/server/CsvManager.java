package org.mlesyk.server;

import org.mlesyk.server.rules.Delete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            for(int i = 0; i < line.split(",").length; i++) {
                outputColumns.add(new ResultColumn());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOutputFile() {
        try (Stream<String> stream = Files.lines(Paths.get(inputFilePath));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))
        ) {
            stream.limit(5).forEach(line -> {
                line = this.applyRules(line);
                //https://stackoverflow.com/questions/29856100/how-to-handle-ioexception-in-iterable-foreach
                try {
                    writer.write(line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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
        outputColumns.forEach(ResultColumn::applyAllRules);
        return outputColumns.stream()
                .filter(resultColumn -> resultColumn.getId() != Delete.DELETED) // Delete
                .collect(Collectors.groupingBy(ResultColumn::getId, //MergeColumns
                        Collectors.collectingAndThen(Collectors.reducing((a,b) ->
                                a.getSourceFileColumnId() > b.getSourceFileColumnId() ? b.joinData(a.getData()): a.joinData(b.getData())
                        ), Optional::get)))
                .values()
                .stream()
                .sorted() // sorted ofder of colimns by id
                .map(ResultColumn::getData)
                .collect(Collectors.joining(","));
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public List<ResultColumn> getOutputColumns() {
        return outputColumns;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
