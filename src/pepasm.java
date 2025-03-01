import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class pepasm {
    public static void main(String[] args) {
        System.out.println("Hi");


        if (args.length != 1) {
            System.out.println("Usage: java pepasm.java file.pep");
            return;
        }
        String filePath = args[0];
        readFile(filePath);
    }


    public static void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Reading file: " + filePath + "\n");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
