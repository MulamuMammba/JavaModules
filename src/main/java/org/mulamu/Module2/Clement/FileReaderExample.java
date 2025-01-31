package org.mulamu.Module2.Clement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReaderExample {
    public static void main(String[] args) {
        // Specify the file path
        File file = new File("Clement/example.txt");
        StringBuilder line = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
              line.append(scanner.nextLine());

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        System.out.println(line);
    }
}
