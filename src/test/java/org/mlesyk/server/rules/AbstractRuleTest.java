package org.mlesyk.server.rules;

import org.junit.After;
import org.junit.Before;
import org.mlesyk.server.CsvManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Maks on 27.08.2017.
 */
public abstract class AbstractRuleTest {

    CsvManager manager;
    File tempInput;
    File tempOutput;

    String[] csvData = {
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

}