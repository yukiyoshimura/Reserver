package com.reserver.web.util;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.*;
import java.util.List;


public class CsvUtils {

    public static List<String[]> ImportCsv(File file) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "SJIS"));


            for(String[] line : reader.readAll()) {
                for(String val : line) {
                    System.out.println(val);
                }
            }

            return reader.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
