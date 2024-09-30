package org.lpc.pc;

public class Cpu {
    Motherboard motherboard;
    boolean isRunning;
    int[] registers;
    int programCounter;

    Cpu(Motherboard m, int registerCount) {
        this.motherboard = m;
        this.isRunning = false;
        this.programCounter = 0;

        registers = new int[registerCount];
        for(int i = 0; i < registerCount; i++) {
            registers[i] = 0;
        }

        System.out.println("CPU created with " + registerCount + " registers");
    }

    public void run(){
        Ram ram = motherboard.getRAM();
        isRunning = true;

        while (isRunning && programCounter < ram.memory.length) {
            InstructionData instructionData = ram.decodeInstructionData(programCounter);
            execute(instructionData.instruction, instructionData.operand1, instructionData.operand2);
            programCounter++;
        }
    }
    /*
        * This method executes the given instruction with the provided operands
        * The method reads from and writes to the CPU registers and RAM
        * 1. LOA: Load value from memory to register
        * 2. STO: Store value from register to memory
        * 3. SET: Set register to value
        * 4. ADD: Add two registers and store the result in the first register
        * 5. SUB: Subtract two registers and store the result in the first register
        * 6. JMP: Jump to an address
        * 7. HLT: Halt the program
        * 8. OUT: Output the value in a register
        * 9. JMZ: Jump to an address if the value in a register is zero
     */
    private void execute(Instruction instruction, int operand1, int operand2) {
        Ram ram = motherboard.getRAM();
        switch (instruction) {
            case LOA -> registers[operand1] = ram.memory[operand2];
            case STO -> ram.memory[operand2] = registers[operand1];
            case SET -> registers[operand1] = operand2;
            case ADD -> registers[operand1] += registers[operand2];
            case SUB -> registers[operand1] -= registers[operand2];
            case JMP -> programCounter = operand1 - 1; // -1 to offset the increment in the main loop
            case HLT -> isRunning = false;
            case OUT -> {
                String output = String.format("\033[1;33m[PROGRAM OUTPUT] %d\033[0m", registers[operand1]);
                System.out.println(output);
            }
            case JMZ -> {
                if (registers[operand1] == 0) {
                    programCounter = operand2 - 1; // -1 to offset the increment in the main loop
                }
            }
        }
    }

    public enum Instruction {
        LOA, STO, SET,
        ADD, SUB,
        JMP, HLT, OUT,
        // operational instructions
        JMZ
    }

    public static class InstructionData {
        Instruction instruction;
        int operand1;
        int operand2;

        public InstructionData(Instruction instruction, int... operands) {
            this.instruction = instruction;
            this.operand1 = operands.length > 0 ? operands[0] : 0;
            this.operand2 = operands.length > 1 ? operands[1] : 0;
        }
    }
}


