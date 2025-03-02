import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pepasm {
    private static final Map<String, String> OPCODES = new HashMap<>();

    static {
        OPCODES.put("LDBA", "D0");
        OPCODES.put("STBA", "F1");
        OPCODES.put("LDWA", "C1");
        OPCODES.put("STWA", "E1");
        OPCODES.put("ANDA", "81");
        //OPCODES.put("ASLA", "48");
        //OPCODES.put("ASRA", "47");
        OPCODES.put("STOP", "00");
        OPCODES.put("CPBA", "B1");
        OPCODES.put("BRNE", "1A");
        OPCODES.put(".END", "zz");
    }

    public static void main(String[] args) {
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
            List<String> machineCode = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                line = line.trim();
                if (line.isEmpty() || line.startsWith(".")) continue; // Ignore empty lines and directives

                String[] parts = line.split(" ");
                String instruction = parts[0];

                if (!OPCODES.containsKey(instruction)) {
                    System.err.println("Unknown instruction: " + instruction);
                    continue;
                }

                String opcode = OPCODES.get(instruction);

                // STOP instruction (no operands)
                if (instruction.equals("STOP")) {
                    machineCode.add(opcode);
                    continue;
                }

                if (parts.length != 3) {
                    System.err.println("Invalid instruction format: " + line);
                    continue;
                }

                String operand = parts[1].replace(",", ""); // Remove comma
                String addressingMode = parts[2];

                //
                String addressModeBit = (addressingMode.equals("i")) ? "i" : "d";
                String operandHex = operand.replace("0x", "").toUpperCase();

                // Ensure operand is 4 digits (word-aligned)
                while (operandHex.length() < 4) {
                    operandHex = "0" + operandHex;
                }

                // Object code layout
                machineCode.add(opcode + " " + operandHex + " " + addressModeBit);
            }

            // Print the Object code
            System.out.println("\nObject Code:");
            for (String code : machineCode) {
                System.out.println(code);

            }
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    }


}
