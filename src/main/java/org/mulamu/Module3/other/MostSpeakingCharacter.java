package org.mulamu.Module3.other;

import java.io.*;
import java.util.*;

import java.util.regex.*;

public class MostSpeakingCharacter {
    public static void main(String[] args) {
        String filename = "src/main/java/org/mulamu/Module3/likeit.txt";
        Map<String, Integer> characterCounts = new HashMap<>();
        Pattern pattern = Pattern.compile("^([A-Z]+)\\.\\s"); // Capturing the character's name

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String character = matcher.group(1);
                    characterCounts.put(character, characterCounts.getOrDefault(character, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        String mostSpeakingCharacter = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : characterCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostSpeakingCharacter = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (!mostSpeakingCharacter.isEmpty()) {
            System.out.println("The character with the most speaking parts is: " + mostSpeakingCharacter + " with " + maxCount + " lines.");
        } else {
            System.out.println("No character found.");
        }


        // Print all characters and their number of speaking parts
        for (Map.Entry<String, Integer> entry : characterCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
