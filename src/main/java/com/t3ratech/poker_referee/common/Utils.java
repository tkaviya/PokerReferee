package com.t3ratech.poker_referee.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public class Utils {

    private Utils() {}

    public static boolean isNumeric(Object testObject) {
        if (testObject == null) return false;
        if (testObject instanceof Number) return true;
        try {
            new BigDecimal(String.valueOf(testObject));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<String> readFileContents(String filename) {
        File pokerFile = new File(filename);
        ArrayList<String> lines = new ArrayList<>();
        try (var fileReader = new FileReader(pokerFile);
             var bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
