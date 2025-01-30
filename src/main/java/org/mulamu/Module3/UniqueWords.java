package Module3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniqueWords {
    public static void main(String[] args) {
        String filePath = "Module3/likeit.txt";
        UniqueWords uniqueWords = new UniqueWords();
        String mostFrequentWord = uniqueWords.findMostFrequentWord(filePath);
        System.out.println("The most frequent word is: " + mostFrequentWord);

    }


    public String findMostFrequentWord(String filePath) {
        Map<String, Integer> wordCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Find the most frequent word
        String mostFrequentWord = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentWord = entry.getKey();
            }
        }

        System.out.println("Unieq words " + wordCount.size());
        System.out.println("it appeared this many times: " + maxCount);
        return mostFrequentWord;
    }


}
