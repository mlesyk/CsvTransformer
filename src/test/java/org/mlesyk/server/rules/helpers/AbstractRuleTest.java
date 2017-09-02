package org.mlesyk.server.rules.helpers;

import org.junit.After;
import org.junit.Before;
import org.mlesyk.server.CsvManager;
import org.mlesyk.server.ResultColumn;
import org.mlesyk.server.rules.AbstractRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Maks on 27.08.2017.
 */
public abstract class AbstractRuleTest {

    protected CsvManager manager;
    protected File tempInput;
    protected File tempOutput;

    protected String[] csvData = {
            "column1,column2,column3,column4",
            "data1,data2,data3,data4"
    };

    @Before
    public void setUp() throws Exception {
        tempInput = File.createTempFile("ChangeColumnFileInput", ".tmp");
        tempOutput = File.createTempFile("ChangeColumnFileOutput", ".tmp");
        try(FileWriter writer = new FileWriter(tempInput)) {
            Arrays.stream(csvData).forEachOrdered(line -> {
                try {
                    writer.write(line);
                    writer.write("\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        manager = new CsvManager(tempInput, tempOutput);
    }

    @After
    public void tearDown() throws Exception {
        tempInput.delete();
        tempOutput.delete();
    }

    protected boolean testRule(AbstractRule rule, ResultColumn column, String[] expectedResult, AbstractRule... rules) {
        boolean result = true;
        column.addRule(rule);
        column.addAllRules(rules);
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