package org.mulamu.Module3.Assignment3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private HashSet<String> usedWords;
    private Random myRandom;
    
    public GladLibMap() {
        myMap = new HashMap<>();
        usedWords = new HashSet<>();
        myRandom = new Random();
        initializeFromSource("Module3/Assignment3/data");
    }

    private void initializeFromSource(String source) {
        String[] categories = {"adjective","animal", "noun", "color", "country", "name", "timeframe", "verb", "fruit"};

        for (String category : categories) {
            String filename = source + "/" + category + ".txt";
            ArrayList<String> words = readWordsFromFile(filename);
            myMap.put(category, words);
            System.out.println(category + " " + myMap.get(category));
        }

    }

    private ArrayList<String> readWordsFromFile(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                words.addAll(Arrays.asList(line.split("\\s+")));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
        return words;
    }

    private String randomFrom(ArrayList<String> source) {
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
        if (myMap.containsKey(label)) {
            return randomFrom(myMap.get(label));
        }
        return "**UNKNOWN**";
    }

    private String processWord(String word) {
        if (word.startsWith("<") && word.endsWith(">")) {
            String category = word.substring(1, word.length() - 1);
            String replacement;
            do {
                replacement = getSubstitute(category);
            } while (usedWords.contains(replacement));
            usedWords.add(replacement);
            return replacement;
        } else if (word.startsWith("<")) {
            int endIndex = word.indexOf(">");
            if (endIndex != -1) {
                String category = word.substring(1, endIndex);
                String punctuation = word.substring(endIndex + 1);
                String replacement;
                do {
                    replacement = getSubstitute(category);
                } while (usedWords.contains(replacement));
                usedWords.add(replacement);
                return replacement + punctuation;
            }
        }
        return word;
    }


    private String fromTemplate(String filename) {
        usedWords.clear();
        StringBuilder story = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    story.append(processWord(word)).append(" ");
                }
                story.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading template file: " + filename);
        }

        return story.toString();
    }

    public void makeStory() {
        System.out.println("Generating a random story...\n");
        String story = fromTemplate("Module3/Assignment3/data/madtemplate2.txt");
        System.out.println(story);
        System.out.println("Total words that could be picked: " + totalWordsInMap());
        System.out.println("Total words considered in this story: " + totalWordsConsidered());
    }

    public int totalWordsInMap() {
        int total = 0;
        for (ArrayList<String> list : myMap.values()) {
            total += list.size();
        }
        return total;
    }

    public int totalWordsConsidered() {
        int total = 0;
        for (String category : myMap.keySet()) {
            if (usedWords.stream().anyMatch(myMap.get(category)::contains)) {
                total += myMap.get(category).size();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        GladLibMap gladLib = new GladLibMap();
        gladLib.makeStory();
    }
}
