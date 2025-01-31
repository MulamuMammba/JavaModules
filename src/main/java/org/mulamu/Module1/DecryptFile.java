package org.mulamu.Module1;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DecryptFile {
    public static void main(String[] args) {
        String fileName = "mysteryTwoKeysPractice.txt";
        String outputFileName = "decryptionResults.txt";

        try {
            String encryptedMessage = new String(Files.readAllBytes(Paths.get(fileName)));
            decryptFile(encryptedMessage, outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(String encryptedMessage, String outputFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (int key1 = 0; key1 < 26; key1++) {
                for (int key2 = 0; key2 < 26; key2++) {
                    String decrypted = decryptTwoKeys(encryptedMessage, key1, key2);
                    if (isReadable(decrypted)) {
                        writer.write("Key1: " + key1 + ", Key2: " + key2 + "\n");
                        writer.write("Decrypted Message: " + decrypted + "\n\n");
                        printFirstFiveWords(writer, decrypted);
//                        return;
                    }
                }
            }
            System.out.println("Decryption results written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decryptTwoKeys(String encrypted, int key1, int key2) {
        StringBuilder decrypted = new StringBuilder(encrypted);

        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            if (Character.isLetter(currChar)) {
                char decryptedChar = (i % 2 == 0) ? decryptCharacter(currChar, key1) : decryptCharacter(currChar, key2);
                decrypted.setCharAt(i, decryptedChar);
            }
        }
        return decrypted.toString();
    }

    public static char decryptCharacter(char ch, int key) {
        char base = Character.isLowerCase(ch) ? 'a' : 'A';
        int decryptedIndex = (ch - base - key + 26) % 26;
        return (char) (base + decryptedIndex);
    }

    public static boolean isReadable(String message) {

            if (message.contains("and ") && message.contains("of ")) {
                return true;
            }else {

                return false;
            }
    }

    public static void printFirstFiveWords(BufferedWriter writer, String message) throws IOException {
        String[] words = message.split("\\s+");
        writer.write("First five words: " + String.join(" ", Arrays.copyOfRange(words, 0, 5)) + "\n");
    }
}
