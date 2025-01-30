package org.mulamu.assesment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencies {
   private static Map<String, Integer> wordCount = new HashMap<>();

    public WordFrequencies() {

    }
    public static void findUnique() {
        String fileName = "src/main/java/org/mulamu/assesment1/errors.txt";
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            String cleanedContent = content.replaceAll("[.,!?;:]", "").toLowerCase();
            String[] words = cleanedContent.split("\\s+");


            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }

            String mostCommonWord = Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("The most common word is: " + mostCommonWord + " : " + wordCount.get(mostCommonWord));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void tester() {
        findUnique();
    }

    public static void main(String[] args) {
        tester();
    }

}
