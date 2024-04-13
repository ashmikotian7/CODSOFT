import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Enter\n'1' to input text\n'2' to provide a file\n'3' to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    countWordsFromInput(scanner);
                    break;
                case 2:
                    countWordsFromFile(scanner);
                    break;
                case 3:
                    System.exit(0); 
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("Exiting the program.");
    }

    public static void countWordsFromInput(Scanner scanner) {
        System.out.println("Enter the text:");
        String text = scanner.nextLine();
        processText(text);
    }

    public static void countWordsFromFile(Scanner scanner) {
        System.out.println("Enter the file path:");
        String filePath = scanner.nextLine();
        try {
            String text = readFromFile(filePath);
            processText(text);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check the file path.");
        }
    }

    public static void processText(String text) {
        String[] words = text.split("[\\s\\p{Punct}]+");
        int wordCount = 0;
        HashMap<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase(); 
            if (!word.isEmpty()) {
                wordCount++;
                if (wordFrequency.containsKey(word)) {
                    wordFrequency.put(word, wordFrequency.get(word) + 1);
                } else {
                    wordFrequency.put(word, 1);
                }
            }
        } 
        System.out.println("Total words: " + wordCount);
        System.out.println("Word frequency:");
        for (String word : wordFrequency.keySet()) {
            System.out.println(word + ": " + wordFrequency.get(word));
        }
    }
    public static String readFromFile(String filePath) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Scanner fileScanner = new Scanner(new File(filePath));
        while (fileScanner.hasNextLine()) {
            sb.append(fileScanner.nextLine()).append("\n");
        }
        fileScanner.close();
        return sb.toString();
    }
}
