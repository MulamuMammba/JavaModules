package org.mulamu.Module3.Assignment1;

import java.util.*;
import java.io.*;

public class CodonCount {
    private final HashMap<String, Integer> codonMap;

    public CodonCount() {
        codonMap = new HashMap<>();
    }

    // Builds a codon frequency map from the given DNA string, starting at index 'start'
    public void buildCodonMap(int start, String dna) {
        codonMap.clear();
        dna = dna.toUpperCase();

        for (int i = start; i + 2 < dna.length(); i += 3) {
            String codon = dna.substring(i, i + 3);
            codonMap.put(codon, codonMap.getOrDefault(codon, 0) + 1);
        }
    }

    // Returns the most common codon found in the last buildCodonMap() call
    public String getMostCommonCodon() {
        int maxCount = 0;
        String mostCommon = "";

        for (Map.Entry<String, Integer> entry : codonMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        return mostCommon;
    }

    // Prints codons that occur within the given range
    public void printCodonCounts(int start, int end) {
        System.out.println("Codons appearing between " + start + " and " + end + " times:");
        for (Map.Entry<String, Integer> entry : codonMap.entrySet()) {
            if (entry.getValue() >= start && entry.getValue() <= end) {
                System.out.printf("%s\t%d%n", entry.getKey(), entry.getValue());
            }
        }
    }

    // Reads DNA from file and tests the codon counting functionality
    public void tester() {
        try {
            // Ensure the file path is correct
            File file = new File("src/main/java/org/mulamu/Module3/Assignment1/dnaMystery2.txt");
            String filePath = file.getAbsolutePath();

            // Read DNA from the file
            StringBuilder dna = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                dna.append(line.replaceAll("[^ATGC]", "")); // Ensures only valid DNA characters
            }
            br.close();

            // Loop through three reading frames (0, 1, 2)
            for (int frame = 0; frame < 3; frame++) {
                buildCodonMap(frame, dna.toString());

                System.out.println("\nReading frame " + frame + " has " + codonMap.size() + " unique codons.");

                // Get most common codon safely
                String mostCommon = getMostCommonCodon();
                if (!mostCommon.isEmpty()) {
                    System.out.println("Most common codon: " + mostCommon + " - " + codonMap.get(mostCommon));
                } else {
                    System.out.println("No codons found.");
                }

                // Print codons occurring between 1 and 5 times
                printCodonCounts(1, 40);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Main method to execute the program
    public static void main(String[] args) {
        CodonCount cc = new CodonCount();
        cc.tester();
    }
}
