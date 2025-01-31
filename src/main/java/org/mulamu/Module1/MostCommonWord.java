package org.mulamu.Module1;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MostCommonWord {
    public static void main(String[] args) {
        String fileName = "romeo.txt";
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            String cleanedContent = content.replaceAll("[.,!?;:]", "").toLowerCase();
            String[] words = cleanedContent.split("\\s+");
            
            Map<String, Integer> wordCount = new HashMap<>();
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
            
            String mostCommonWord = Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("The most common word is: " + mostCommonWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
