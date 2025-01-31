package org.mulamu.Module1;

public class TwoKeyBruteForceDecryption {
    public static void main(String[] args) {
        String encryptedMessage = "Akag tjw hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";

        for (int key1 = 0; key1 < 26; key1++) {
            for (int key2 = 0; key2 < 26; key2++) {
                String decrypted = decryptTwoKeys(encryptedMessage, key1, key2);
                if (isReadable(decrypted)) {
                    System.out.println("Key1: " + key1 + ", Key2: " + key2);
                    System.out.println("Decrypted Message: " + decrypted);
                }
            }
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
        String[] commonWords = {"the ", "and "};
        for (String word : commonWords) {
            if (message.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
