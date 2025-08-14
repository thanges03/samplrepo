import java.io.*;
import java.util.*;

public class FileOperations {

    // Method to write content to a file
    public static void writeFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println("✅ File written successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing file: " + e.getMessage());
        }
    }

    // Method to read content from a file
    public static void readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("📖 File Contents:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // Method to modify content in a file by replacing a target word
    public static void modifyFile(String filename, String target, String replacement) {
        File file = new File(filename);
        StringBuilder content = new StringBuilder();

        // Step 1: Read the original file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Replace the target word with the replacement
                content.append(line.replaceAll(target, replacement)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file during modification: " + e.getMessage());
            return;
        }

        // Step 2: Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
            System.out.println("🔁 File modified successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing file during modification: " + e.getMessage());
        }
    }

    // Main method to demonstrate all operations
    public static void main(String[] args) {
        String filename = "example.txt";
        String initialContent = "Hello, this is a sample text file.\nThis file contains text for testing.";

        // 1. Write content to file
        writeFile(filename, initialContent);

        // 2. Read and display content
        readFile(filename);

        // 3. Modify the file by replacing "text" with "Java"
        modifyFile(filename, "text", "Java");

        // 4. Read modified content
        readFile(filename);
    }
}
