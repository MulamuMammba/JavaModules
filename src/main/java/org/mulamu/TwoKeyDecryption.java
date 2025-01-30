package org.mulamu;

public class TwoKeyDecryption {
    public static void main(String[] args) {
        String encryptedMessage = "Top ncmy qkff vi vguv vbg ycpx";
        int key1 = 2;
        int key2 = 20;

        System.out.println(decryptTwoKeys(encryptedMessage, key1, key2));
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
}
