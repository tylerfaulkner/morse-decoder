/*
 * Course: CS2852-071
 * Spring 2020
 * Lab 7
 * Name: Sean Jones
 * Created: 04/30/20
 */
package faulknert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Used to decode messages written in morse code.
 */
public class MorseDecoder {
    private static MorseTree<String> tree;

    public static void main(String[] args) {
        tree = new MorseTree<String>();
        try (Scanner in = new Scanner(System.in)) {
            loadDecoder(Paths.get("morsecode.txt"));
            System.out.print("Please enter the name of an encoded file in the project directory: ");
            String encoded = in.next();
            System.out.print("Please enter the name for the file to save the decoded message: ");
            String output = in.next();
            decode(Paths.get(encoded).toFile(), Paths.get(output).toFile());
        } catch (FileNotFoundException e) {
            System.out.println("Decoder file not found.");
        }
    }

    private static void loadDecoder(Path path) throws FileNotFoundException {
        Scanner in = new Scanner(path.toFile());
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.charAt(0) == '\\') {
                String symbol = "\n";
                String code = line.substring(2);
                tree.add(symbol, code);
            } else {
                String symbol = line.substring(0, 1);
                String code = line.substring(1);
                tree.add(symbol, code);
            }
        }
    }

    private static void decode(File encoded, File output) {
        StringBuilder sb = new StringBuilder();
        String codeSkip = "";
        try (Scanner in = new Scanner(encoded)) {
            while (in.hasNext()) {
                try {
                    String code = in.next();
                    codeSkip = code;
                    sb.append(tree.decode(code));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping unknown symbol: " + codeSkip);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file could not be found.");
        }
        try (PrintWriter out = new PrintWriter(output)) {
            out.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println("The file could not be found.");
        }
    }
}
