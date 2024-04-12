package pl.coderslab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        initialView();
    }

    public static void out() {
        System.out.println(ConsoleColors.BLUE + "Please select an option :" + ConsoleColors.RESET);

        String[] option = {"add", "remove", "list", "exit"};
        for (String s : option) {
            System.out.println(s);
        }
    }

    public static String[][] readFile(String filename) {
        DynamicStringArray fileInArray = new DynamicStringArray();
        Path path = Paths.get(filename);
        String taskline = "";
        if (!Files.exists(path)) {
            System.out.println("Plik nie istnieje");
        }
        try {
            for (String line : Files.readAllLines(path)) {
                taskline = line;
                fileInArray.add(taskline);
            }
        } catch (IOException e) {
            System.out.println("Bład wczytywania zawartosci pliku");
            System.out.println(e);
        }

        return toArray2d(fileInArray.array);
    }


    public static String[][] toArray2d(String[] readFile) {
        String[][] finalArray = new String[readFile.length][3];
        int i = 0;
        for (int row = 0; row < readFile.length; row++) {
            for (int col = 0; col < 3; col++) {
                    String[] lineArray = readFile[row].split(", ");
                    finalArray[row][col] = lineArray[col];
                    i++;

            }

        }
        return finalArray;
    }


    public static void list(String [][] array2d){
        for(int row = 0; row < array2d.length; row++) {
            System.out.print(row);
            for (int col = 0; col < array2d[row].length; col++) {
                System.out.print(" : " + array2d[row][col] + " ");
            }
            System.out.println();


        }
    }
    public static void initialView(){

        boolean running = true;
        while (running) {
            out();
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            switch (choice) {
                case "add":
                    System.out.println("Please add task desription ");
                    String input = scanner.nextLine();
                    System.out.println("Please add task due data");
                    String input2 = scanner.nextLine();
                    System.out.println("Is your task is important: true/false");
                    String input3 = scanner.nextLine();
                    add(readFile("tasks.csv"),input,input2,input3);
                    break;
                case "remove":
                    System.out.println("Please select number to remove");
                    while(!scanner.hasNextInt()){
                        System.out.println("Please select number to remove");
                        scanner.nextLine();
                        System.out.println("podaj liczbę całkowitą");
                    }
                    int removeIndex = scanner.nextInt();
                    remove(readFile("tasks.csv"),removeIndex);
                    break;
                case "list":
                    System.out.println(choice);
                 list(readFile("tasks.csv"));
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    running = false;
                    break;
                default:
                    System.out.println("Please select a correct option..");
            }
        }

    }
    public static void add(String[][] array2d,String input, String input2,String input3){
        Path path = Paths.get("tasks.csv");
        array2d = Arrays.copyOf(array2d, array2d.length + 1);
        array2d[array2d.length -1] = new String[3];
        array2d [array2d.length -1 ][0] = input;
        array2d [array2d.length -1 ][1] = input2;
        array2d [array2d.length -1 ][2] = input3;
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (String[] row : array2d) {
            for (String col : row) {

                sb.append(col).append(", ");
            }
            sb.append(lineSeparator);
        }


        try {
            Files.writeString(path,sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void remove(String[][]array2d, int removeIndex ) {
        Path path = Paths.get("tasks.csv");
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for(int row = 0; row < array2d.length; row++) {

            if(row != removeIndex ){
                for (int col = 0; col < array2d[row].length; col++) {
                    sb.append(array2d[row][col]).append(", ");
                }
                sb.append(lineSeparator);
            }
        }
        try {
            Files.writeString(path,sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    }
