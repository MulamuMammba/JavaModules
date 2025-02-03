package org.mulamu.Module3.Assignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordFr {
    private static Map<String, Integer> wordCount = new HashMap<>();

    public static void findUnique() {
        String fileName = "src/main/java/org/mulamu/Module3/Assignment2/Q12/errors.txt";
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            String cleanedContent = content.toLowerCase(); // Keep punctuation
            String[] words = cleanedContent.split("\\s+");

            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void mostUsedWord() {
        if (wordCount.isEmpty()) {
            System.out.println("No words found.");
            return;
        }

        Map.Entry<String, Integer> mostUsed = Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue());
        System.out.println("Most used word: \"" + mostUsed.getKey() + "\" with count: " + mostUsed.getValue());
    }

    public static void tester() {
        findUnique();

        ArrayList<Map.Entry<String, Integer>> sortedList = new ArrayList<>(wordCount.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        int countTotal = wordCount.values().stream().mapToInt(Integer::intValue).sum();
        long countUniq = wordCount.values().stream().filter(count -> count == 1).count();

        System.out.println("Total words: " + countTotal);
        System.out.println("Unique words: " + countUniq);

        mostUsedWord();
    }

    public static void main(String[] args) {
        tester();
    }
}
