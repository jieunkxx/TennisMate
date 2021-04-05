package ui;

import java.io.FileNotFoundException;

// Main Class to run TennisMateApp

public class Main {
    public static void main(String[] args) {
        try {
            new TennisMateApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
