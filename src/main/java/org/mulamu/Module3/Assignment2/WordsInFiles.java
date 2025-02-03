package org.mulamu.Module3.Assignment2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordsInFiles {
    private final HashMap<String, HashSet<String>> wordMap;

    public WordsInFiles() {
        wordMap = new HashMap<>();
    }

    private void addWordsFromFile(File file) throws IOException {
        String filename = file.getName();
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));

        for (String line : lines) {
            String[] words = line.toLowerCase().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordMap.computeIfAbsent(word, k -> new HashSet<>()).add(filename);
                }
            }
        }
    }

    public void buildWordFileMap(String directoryPath) throws IOException {
        wordMap.clear();
        File dir = new File(directoryPath);
        if (!dir.isDirectory()) {
            System.out.println("Invalid directory!");
            return;
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No text files found in the directory.");
            return;
        }

        for (File file : files) {
            addWordsFromFile(file);
        }
    }

    public int countWordsInNumFiles(int number) {
        int count = 0;
        for (HashSet<String> files : wordMap.values()) {
            if (files.size() == number) {
                count++;
            }
        }
        return count;
    }

    public void printFilesContainingWord(String word) {
        if (wordMap.containsKey(word)) {
            System.out.println("The word '" + word + "' appears in files: " + String.join(", ", wordMap.get(word)));
        } else {
            System.out.println("The word '" + word + "' does not appear in any file.");
        }
    }

    public void printFilesNotContainingWord(String word) {
        HashSet<String> allFiles = new HashSet<>();
        for (HashSet<String> files : wordMap.values()) {
            allFiles.addAll(files);
        }

        if (wordMap.containsKey(word)) {
            allFiles.removeAll(wordMap.get(word));
        }

        if (allFiles.isEmpty()) {
            System.out.println("All files contain the word '" + word + "'.");
        } else {
            System.out.println("The word '" + word + "' does NOT appear in files: " + String.join(", ", allFiles));
        }
    }

    public void tester() throws IOException {
        String directoryPath = "src/main/java/org/mulamu/Module3/Assignment2/Q12";
        buildWordFileMap(directoryPath);

        System.out.println("Number of words appearing in 4 files: " + countWordsInNumFiles(4));
        System.out.println("Number of words appearing in 7 files: " + countWordsInNumFiles(7));

        System.out.println("Number of unique words in map: " + wordMap.size());

        System.out.println("Total number of words: " + countTotalWords());

        printFilesContainingWord("tree");
        printFilesNotContainingWord("sea");
    }

    private int countTotalWords() {
        int totalWords = 0;
        for (String key : wordMap.keySet()) {
            totalWords++;
        }
        return totalWords;
    }

    public static void main(String[] args) {
        WordsInFiles wf = new WordsInFiles();
        try {
            wf.tester();
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }
}
