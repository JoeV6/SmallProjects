package org.lpc.pc;

import java.util.Arrays;

public class Ram {
    Motherboard m;

    int[] memory;
    Ram(Motherboard m, int sizeBytes) {
        this.m = m;
        memory = new int[sizeBytes];

        System.out.println("RAM created with size " + sizeBytes + " bytes");
    }

    public void encodeInstructionData(int address, Cpu.InstructionData instructionData) {
        // Instruction is stored in the upper 8 bits
        // Operand1 is stored in the next 12 bits
        // Operand2 is stored in the lower 12 bits

        // format: [iiiiiiii][oooooooooooooooo][oooooooooooooooo]
        // & operation with 0xFFF (1111 1111 1111) to make sure it doesn't exceed 12 bits
        memory[address] =
                (instructionData.instruction.ordinal() << 24) |  // Instruction in upper 8 bits
                ((instructionData.operand1 & 0xFFF) << 12) |     // Operand1 in the middle 12 bits
                (instructionData.operand2 & 0xFFF);              // Operand2 in the lower 12 bits


    }
    public void dump() {
        // Dump memory with format [000] for index and 32-bit binary for the value
        // if memory is empty don't print anything
        System.out.println("\nMemory dump:");
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == 0) {
                continue;
            }
            System.out.printf("[%03d] %32s", i, String.format("%32s", Integer.toBinaryString(memory[i])).replace(' ', '0'));
            Cpu.InstructionData instructionData = decodeInstructionData(i);
            System.out.printf("\t" + instructionData.instruction + "\t\t" + instructionData.operand1 + "\t" + instructionData.operand2 + "\n");
        }
    }
    public Cpu.InstructionData decodeInstructionData(int address) {
        // format: [iiiiiiii][oooooooooooooooo][oooooooooooooooo]

        int data = memory[address];
        Cpu.Instruction i = Cpu.Instruction.values()[(data >> 24) & 0xFF];
        int operand1 = (data >> 12) & 0xFFF;
        int operand2 = data & 0xFFF;

        return new Cpu.InstructionData(i, operand1, operand2);
    }
    public void clear() {
        Arrays.fill(memory, 0);
    }
    public int getSize() {
        return memory.length;
    }
}
