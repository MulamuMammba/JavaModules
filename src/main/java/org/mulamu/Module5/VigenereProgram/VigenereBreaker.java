package org.mulamu.Module5.VigenereProgram;

import edu.duke.FileResource;

import java.util.Arrays;
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
        FileResource fr = new FileResource("src/main/java/org/mulamu/Module5/VigenereProgram/messages/secretmessage2.txt");

        String encrypted = fr.asString();

        FileResource dictionary = new FileResource("src/main/java/org/mulamu/Module5/VigenereProgram/dictionaries/English");

        breakForLanguage(encrypted,readDictionary(dictionary));
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

    private void breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxIterations = 100;
        char mostCommon = 'e';
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


//        int key_length = 38;
//            int[] key = tryKeyLength(encrypted, key_length, mostCommon);
//            VigenereCipher cipher = new VigenereCipher(key);
//            String decrypted = cipher.decrypt(encrypted);
//            int realWords = countWords(decrypted, dictionary);
//            if (realWords > highestRealWords) {
//                highestRealWords = realWords;
//                realKey = key;
//                possibleMessage = decrypted;
//                keyLenth = key_length;
//
//            }


        String alph = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder keyWord = new StringBuilder();

        for (int letter: realKey){
            keyWord.append(alph.charAt(letter));
        }

        System.out.println("Possible Message\n"
                + "Key Length : " + keyLenth + "\n"
                + "Key : " + Arrays.toString(realKey) + " or " + keyWord + "\n"
                + "This message has " + highestRealWords + " real words\n"
                + "Message : " + possibleMessage);
    }

}
