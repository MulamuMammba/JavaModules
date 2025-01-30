package org.mulamu;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class MostCommonWordLength {
    public static void main(String[] args) {
        String fileName = "errors.txt";
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            String cleanedContent = content.replaceAll("[.,!?;:]", "").toLowerCase();
            String[] words = cleanedContent.split("\\s+");
            
            Map<Integer, Integer> lengthCount = new HashMap<>();
            for (String word : words) {
                int length = word.length();
                lengthCount.put(length, lengthCount.getOrDefault(length, 0) + 1);
            }
            
            int mostCommonLength = Collections.max(lengthCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("The most common word length is: " + mostCommonLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
