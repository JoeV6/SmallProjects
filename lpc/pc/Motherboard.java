package org.lpc.pc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Motherboard {
    Cpu cpu;
    Ram ram;
    public Motherboard() {
        System.out.println("\nMotherboard created");

        this.ram = new Ram(this, 512);
        this.cpu = new Cpu(this, 4);

        System.out.println("Motherboard connected to CPU and RAM\n");
    }

    public static ArrayList<Cpu.InstructionData> readInstructionsFromFile(String filePath) {
        ArrayList<Cpu.InstructionData> instructions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove comments from the line
                int commentIndex = line.indexOf("//");
                if (commentIndex != -1) {
                    line = line.substring(0, commentIndex).trim();
                }

                if (line.isEmpty()) continue;

                // Split the line into parts based on spaces
                String[] parts = line.split("\\s+");

                if (parts.length == 0) continue;

                String instructionName = parts[0];

                try {
                    Cpu.Instruction instruction = Cpu.Instruction.valueOf(instructionName);

                    int operand1 = parts.length > 1 ? Integer.parseInt(parts[1]) : 0; // Default to 0 if not provided
                    int operand2 = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;

                    // Add the instruction to the list
                    instructions.add(new Cpu.InstructionData(instruction, operand1, operand2));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid instruction found: " + instructionName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }

    public Cpu getCPU() {
        return cpu;
    }
    public Ram getRAM() {
        return ram;
    }
}
