package org.mulamu.Module2.assesment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WordFrequenciesBackup {
    private static Map<String, Integer> wordCount = new HashMap<>();

    public static void findUnique() {
        String fileName = "src/main/java/org/mulamu/assesment1/errors.txt";
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            String cleanedContent = content.replaceAll("[.,!?;:]", "").toLowerCase();
            String[] words = cleanedContent.split("\\s+");

            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tester() {
        findUnique();

        // Create a list from the elements of the wordCount map
        ArrayList<Map.Entry<String, Integer>> sortedList = new ArrayList<>(wordCount.entrySet());

        // Sort the list based on word frequency
        Collections.sort(sortedList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        int countTotal = 0;
        int countUniq = 0;
        // Display the words and their frequencies
        for (Map.Entry<String, Integer> entry : sortedList) {

            System.out.println(entry);
            countTotal = countTotal + entry.getValue();
            if (entry.getValue() == 1){
                countUniq++;
            }

        }
        System.out.println("Total words: " + countTotal);
        System.out.println("Uniq words : " + countUniq);
    }

    public static void main(String[] args) {
        tester();
    }
}
