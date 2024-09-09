package org.lpc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
     Calculator();
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
                System.out.println("Invalid input");
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
        BinaryTree tree = new BinaryTree();
        int n = 20;
        BinaryTree.TreeNode root = tree.generateFibonacciTree(n);

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
