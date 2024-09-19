package org.lpc.bintree;

import java.util.Stack;

public class Binarytree {
    public void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");

            // Push right child first so that left is processed first
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }
    public void inOrderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            // Reach the leftmost node of the current node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Current must be null at this point
            current = stack.pop();
            System.out.print(current.val + " ");

            // Visit the right subtree
            current = current.right;
        }
    }
    public void postOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> output = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.push(node);

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        while (!output.isEmpty()) {
            TreeNode node = output.pop();
            System.out.print(node.val + " ");
        }
    }
    public void printTree(TreeNode root) {
        printTreeHelper(root, 0, "Root");
    }
    private void printTreeHelper(TreeNode node, int level, String childType) {
        if (node == null) {
            return;
        }

        // Indentation for visual hierarchy
        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }

        // Print node value and type (Root, Left, Right)
        System.out.println(childType + ": " + node.val);

        // Recursively print left and right children
        printTreeHelper(node.left, level + 1, "Left");
        printTreeHelper(node.right, level + 1, "Right");
    }

    public TreeNode generateFibonacciTree(int n) {
        if (n <= 0) {
            return new TreeNode(0);
        } else if (n == 1) {
            return new TreeNode(1);
        } else {
            TreeNode leftChild = generateFibonacciTree(n - 1);
            TreeNode rightChild = generateFibonacciTree(n - 2);
            TreeNode root = new TreeNode(leftChild.val + rightChild.val);
            root.left = leftChild;
            root.right = rightChild;
            return root;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
}

