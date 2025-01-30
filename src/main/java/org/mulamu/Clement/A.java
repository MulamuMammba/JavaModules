package Clement;

public class A {

    public static void main(String[] args) {
        String text = "";
        int shift = 3;
        String encrypted = encrypt(text, shift);
        System.out.println("Original: " + text);
        System.out.println("Encrypted: " + encrypted);
    }

public static String encrypt(String text, int shift) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encrypted.append((char) ((c - base + shift) % 26 + base));
            } else {
                encrypted.append(c);
            }
        }

        System.out.println(encrypted.toString());
        return encrypted.toString();
    }
}