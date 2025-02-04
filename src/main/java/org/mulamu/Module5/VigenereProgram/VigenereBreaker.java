package org.mulamu.Module5.VigenereProgram;

import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder result = new StringBuilder();
        for (int i = whichSlice; i < message.length();i+=totalSlices) {
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
        FileResource fr = new FileResource("src/main/java/org/mulamu/Module5/VigenereProgram/messages/secretmessage1.txt");
        String encrypted = fr.asString();

        char mostCommon = 'e';

        int klength = 4;
        int[] key = tryKeyLength(encrypted, klength, mostCommon);

        System.out.println("Keys used\n" +
                Arrays.toString(key));


        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);

        System.out.println("Decrypted message: " + decrypted);
    }


    private HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<String>();
        for (String word : fr.lines()) {
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }


}
