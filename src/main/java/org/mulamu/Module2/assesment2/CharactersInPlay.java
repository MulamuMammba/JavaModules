package org.mulamu.Module2.assesment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CharactersInPlay {
    private final ArrayList<String> characters;
    private final ArrayList<Integer> counts;

    public CharactersInPlay() {
        characters = new ArrayList<>();
        counts = new ArrayList<>();
    }

    private void update(String person) {
        int index = characters.indexOf(person);
        if (index == -1) {
            characters.add(person);
            counts.add(1);
        } else {
            int freq = counts.get(index);
            counts.set(index, freq + 1);
        }
    }

    private void findAllCharacters() {
        characters.clear();
        counts.clear();

        String fileName = "src/main/java/org/mulamu/Module2/assesment2/errors.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(".")) {
                    int index = line.indexOf(".");
                    if (index != -1) {
                        String possibleName = line.substring(0, index).trim();
                        update(possibleName);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public void tester() {
        findAllCharacters();
        for (int i = 0; i < characters.size(); i++) {
            if (counts.get(i) > 1) { // Adjust the threshold as needed
                System.out.println(characters.get(i) + " " + counts.get(i));
            }
        }
    }

    public void charactersWithNumParts(int num1, int num2) {
        for (int i = 0; i < characters.size(); i++) {
            int count = counts.get(i);
            if (count >= num1 && count <= num2) {
                System.out.println(characters.get(i) + " " + count);
            }
        }
    }

    public static void main(String[] args) {
        CharactersInPlay cip = new CharactersInPlay();
        cip.tester();
        cip.charactersWithNumParts(10, 15); // Example usage
    }
}
