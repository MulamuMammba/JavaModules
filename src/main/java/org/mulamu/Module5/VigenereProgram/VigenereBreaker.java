package org.mulamu.Module5.VigenereProgram;

import edu.duke.FileResource;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder result = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            result.append(message.charAt(i));
        }
        return result.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);

        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            int shift = cc.getKey(slice);
            key[i] = shift;
        }

        return key;
    }


    public void breakVigenere() {
        FileResource fr = new FileResource("src/main/java/org/mulamu/Module5/VigenereProgram/messages/secretmessage4.txt");

        HashMap<String, HashSet<String>> languages = new HashMap<>();
        String encrypted = fr.asString();

        File dictionary = new File("src/main/java/org/mulamu/Module5/VigenereProgram/dictionaries/");

        File[] files = dictionary.listFiles((d, name) -> true);
        if (files == null || files.length == 0) {
            System.out.println("No text files found in the directory.");
            return;
        }

        for (File file : files) {
            languages.put(file.getName(), readDictionary(new FileResource(file)));
        }


        breakForAllLangs(encrypted, languages);
    }


    private HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<String>();
        for (String word : fr.lines()) {
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }

    private int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        String[] words = message.split("\\W+");

        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())) {
                count++;
            }
        }

        return count;
    }

    private void breakForLanguage(String encrypted, char mostCommon, HashSet<String> dictionary) {
        int maxIterations = 100;
        int highestRealWords = 0;
        int[] realKey = new int[0];
        String possibleMessage = "";
        int keyLenth = 0;

        for (int i = 1; i < maxIterations; i++) {
            int[] key = tryKeyLength(encrypted, i, mostCommon);
            VigenereCipher cipher = new VigenereCipher(key);
            String decrypted = cipher.decrypt(encrypted);
            int realWords = countWords(decrypted, dictionary);
            if (realWords > highestRealWords) {
                highestRealWords = realWords;
                realKey = key;
                possibleMessage = decrypted;
                keyLenth = i;

            }
        }

        String alph = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder keyWord = new StringBuilder();

        for (int letter : realKey) {
            keyWord.append(alph.charAt(letter));
        }

        String[] lines = possibleMessage.split("\\r?\\n");
        int maxLines = 3;

        System.out.println("Possible Message\n" + "Key Length : " + keyLenth + "\n" + "Key : " + Arrays.toString(realKey) + " or " + keyWord + "\n" + "This message has " + highestRealWords + " real words\n" + "Message : " );
        for (int i = 0; i < Math.min(maxLines, lines.length); i++) {
            System.out.println(lines[i]);
        }
        System.out.println("\n\n");

    }

    private char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> charCount = new HashMap<>();
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                if (!charCount.containsKey(word.charAt(i))) {
                    charCount.put(word.charAt(i), 1);
                } else {
                    charCount.put(word.charAt(i), charCount.get(word.charAt(i)) + 1);
                }
            }
        }

        char maxKey = ' ';
        int maxValue = 0;

        for (char ct : charCount.keySet()) {
            if (charCount.get(ct) > maxValue) {
                maxValue = charCount.get(ct);
                maxKey = ct;
            }

        }

        return maxKey;

    }

    private void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {

        for (String language : languages.keySet()) {
            System.out.println("Language : " + language);
            breakForLanguage(encrypted, mostCommonCharIn(languages.get(language)), languages.get(language));
        }
    }

}
