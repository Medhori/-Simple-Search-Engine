package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, List<Integer>> map = new HashMap<>();
    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        String filename = "";
        if (args[0].equals("--data")) {
            filename = args[1];
        }

        String[] lines = readFile(filename);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Menu ===\n" +
                    "1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit");
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    searchPeople(lines);
                    break;
                case 2:
                    printAllPeople(lines);
                    break;
                case 0:
                    System.out.println("\nBye!");
                    exit = true;
                    break;
                default:
                    System.out.println("\nIncorrect option! Try again.");
                    break;
            }
        }


    }

    private static void printAllPeople(String[] lines) {
        System.out.println("\n=== List of people ===");
        for (String s :
                lines) {
            System.out.println(s);
        }
    }

    private static void searchPeople(String[] lines) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String option = scanner.nextLine();
        switch (option) {
            case "ALL":
                searchALL();
                break;
            case "ANY":
                searchAny();
                break;
            case "NONE":
                searchNONe();
                break;
            default:
                System.out.println("Invalid operation!");
                break;

        }


    }

    private static void searchNONe() {
        ArrayList<String> anyList = new ArrayList<>();
        var noneList = new ArrayList<String>(list);
        System.out.println();
        System.out.println("Enter a name or email to search all suitable people.");
        String data = scanner.nextLine().trim().toLowerCase();
        StringTokenizer st = new StringTokenizer(data);
        while (st.hasMoreTokens()) {
            var word = st.nextToken();
            if (map.containsKey(word)) {
                for (int index : map.get(word)) {
                    anyList.add(list.get(index));
                }
            }
        }


        noneList.removeAll(anyList);

        if (!noneList.isEmpty()) {
            System.out.printf("%d persons found:%n", noneList.size());
            for (String s :
                    noneList) {
                System.out.println(s);
            }
        } else {
            System.out.println("No matching people found.");
        }

    }

    private static void searchAny() {
        ArrayList<String> anyList = new ArrayList<>();
        System.out.println();
        System.out.println("Enter a name or email to search all suitable people.");
        String data = scanner.nextLine().trim().toLowerCase();
        StringTokenizer st = new StringTokenizer(data);
        while (st.hasMoreTokens()) {
            var word = st.nextToken();
            if (map.containsKey(word)) {
                for (int index : map.get(word)) {
                    anyList.add(list.get(index));
                }
            }
        }

        if (!anyList.isEmpty()) {
            System.out.printf("%d persons found:%n", anyList.size());
            for (String s :
                    anyList) {
                System.out.println(s);
            }
        } else {
            System.out.println("No matching people found.");
        }
    }

    private static void searchALL() {
        ArrayList<String> allList = new ArrayList<>();
        System.out.println();
        System.out.println("Enter a name or email to search all suitable people.");
        String data = scanner.nextLine().trim().toLowerCase();


        for (String line : list) {
            boolean found = true;
            StringTokenizer st = new StringTokenizer(data);
            while (st.hasMoreTokens()) {
                var word = st.nextToken();
                if (!line.trim().toLowerCase().contains(word)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                allList.add(line);
            }
        }

        if (!allList.isEmpty()) {
            System.out.printf("%d persons found:%n", allList.size());
            for (String s :
                    allList) {
                System.out.println(s);
            }
        } else {
            System.out.println("No matching people found.");
        }

    }


    private static String[] readFile(String fileName) {
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            int i = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                list.add(line);
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    String word = st.nextToken().trim().toLowerCase();
                    map.computeIfAbsent(word, k -> new ArrayList<Integer>());
                    map.get(word).add(i);

                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + fileName);
        }

        return list.toArray(new String[0]);
    }


}
