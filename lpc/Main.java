package org.lpc;

import org.lpc.bintree.Binarytree;
import org.lpc.calc.Calculator;
import org.lpc.database.Database;
import org.lpc.database.records.DatabaseRecord;
import org.lpc.pc.Cpu;
import org.lpc.pc.Motherboard;
import org.lpc.pc.Ram;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Database();
    }
    /*
     * This method demonstrates the Database class
     * A database is implemented using a B-tree for indexing
     * The B-tree is a self-balancing tree data structure that maintains sorted data and allows
     * searches, sequential access, insertions, and deletions in logarithmic time
     *
     * The Database class has the following methods:
     *  1. insert(DatabaseRecord record): Insert a record into the database
     *  2. retrieve(int key): Retrieve a record by its key
     *  3. delete(int key): Delete a record by its key
     *  4. filterByField(String fieldName, Object value): Filter records by a field value
     *  5. printIndex(): Print the B-tree structure
     */
    public static void Database(){
        Database database = new Database(3);

        // Create a record with multiple fields
        DatabaseRecord record1 = new DatabaseRecord(1);
        record1.addField("name", "Alice");
        record1.addField("age", 30);
        record1.addField("city", "New York");

        DatabaseRecord record2 = new DatabaseRecord(2);
        record2.addField("name", "Bob");
        record2.addField("age", 25);
        record2.addField("height", 5.9);
        record2.addField("isStudent", true);

        database.insert(record1);
        database.insert(record2);

        for (int i = 0; i < 30; i++) {
            DatabaseRecord record = new DatabaseRecord(i);
            record.addField("name", "Person " + i);
            record.addField("age", 20 + i);
            database.insert(record);
        }

        // Retrieve and print a record
        DatabaseRecord retrievedRecord = database.retrieve(1);
        System.out.println("Retrieving key 1: " + retrievedRecord);

        // Get specific field values
        System.out.println("Name: " + retrievedRecord.getField("name"));
        System.out.println("Age: " + retrievedRecord.getField("age"));

        // Delete a record
        database.delete(2);
        System.out.println("After deleting key 2: " + database.retrieve(2));

        Set<DatabaseRecord> filteredRecords = database.filterByField("age", 30);
        for (DatabaseRecord rec : filteredRecords) {
            System.out.println("Filtered Record: " + rec);
        }
    }

    /*
     * This method demonstrates an emulated computer using the CPU, RAM, and Motherboard classes
     * The CPU reads instructions from RAM and executes them
     * The CPU has 4 registers and supports the following instructions:
     *  1. LOA: Load value from memory to register
     *  2. STO: Store value from register to memory
     *  3. SET: Set register to value
     *  4. ADD: Add two registers and store the result in the first register
     *  5. SUB: Subtract two registers and store the result in the first register
     *  6. JMP: Jump to an address
     *  7. HLT: Halt the program
     *  8. OUT: Output the value in a register
     * The program is hardcoded in the Computer method
     * The program adds two numbers and outputs the result
     */
    public static void Computer() {
        Motherboard motherboard = new Motherboard();
        Ram ram = motherboard.getRAM();
        Cpu cpu = motherboard.getCPU();

        ArrayList<Cpu.InstructionData> instructions = Motherboard.readInstructionsFromFile("lpc/pc/_program2.txt");

        for (int i = 0; i < instructions.size(); i++) {
            ram.encodeInstructionData(i, instructions.get(i));
        }

        System.out.println("Program loaded into RAM\n");

        ram.dump();
        cpu.run();

    }
    /*
     * This method demonstrates the Calculator class
     * It reads input from the user and evaluates the expression
     *
     * The expression is converted to postfix using the Shunting Yard algorithm
     *     (infix = operator between operands, postfix = operator after operands)
     *
     * Shunting Yard Algorithm:
     *    1. Tokenize the expression
     *    2. For each token:
     *      - If it is a number, add it to the output queue
     *      - If it is an operator:
     *          - While there is an operator at the top of the operator stack with greater or equal precedence:
     *             - Pop the operator from the operator stack and add it to the output queue
     *          - Push the current operator to the operator stack
     *      - If it is a left parenthesis, push it to the operator stack
     *      - If it is a right parenthesis:
     *         - Pop operators from the operator stack and add them to the output queue until a left parenthesis is encountered
     *         - Pop the left parenthesis from the operator stack
     *     3. Pop any remaining operators from the operator stack and add them to the output queue
     *     4. The output queue is the postfix expression
     *
     * The postfix expression is then evaluated using a stack:
     *     1. For each token in the postfix expression:
     *        - If it is a number, push it to the stack
     *        - If it is an operator, pop the required number of operands from the stack, apply the operator, and push the result back to the stack
     *    2. The result is the only element left in the stack
     *
     * Example:
     *   Input: 2 + (3 * 4 - 5) * 5
     *   Infix expression: 2 + (3 * 4 - 5) * 5
     *   Postfix expression: 2 3 4 * 5 - 5 * +
     *   Result: 45
     */
    public static void Calculator() {
        Calculator calculator = new Calculator();

        double result = calculator.evaluate("2 + (3 * 4 - 5) * 5");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }

            try {
                result = calculator.evaluate(input);
            } catch (Exception e) {
                System.out.println("Invalid input\n");
            }
        }
    }

    /*
     * This method demonstrates the BinaryTree class
     * A Binary Tree is a data structure where each node has at most two children: left and right
     * There are three types of tree traversals: pre-order, in-order, and post-order
     *   1. Pre-order traversal: root, left, right
     *   2. In-order traversal: left, root, right
     *   3. Post-order traversal: left, right, root
     */
    public static void BinaryTree() {
        Binarytree tree = new Binarytree();
        int n = 20;
        Binarytree.TreeNode root = tree.generateFibonacciTree(n);

        System.out.println("Pre-order traversal:");
        tree.preOrderTraversal(root);
        System.out.println();

        System.out.println("In-order traversal:");
        tree.inOrderTraversal(root);
        System.out.println();

        System.out.println("Post-order traversal:");
        tree.postOrderTraversal(root);
        System.out.println();

        tree.printTree(root);
    }
}
