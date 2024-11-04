package org.lpc.database.indexing;

import java.util.Arrays;

public class BTree {
    private BTreeNode root;
    private final int minDegree;

    public BTree(int minDegree) {
        this.root = null;
        this.minDegree = minDegree;
    }

    public BTreeNode search(int key) {
        return root == null ? null : root.search(key);
    }

    public void insert(int key) {
        if (root == null) {
            // Create a new root if the tree is empty
            root = new BTreeNode(minDegree, true);
            root.keys[0] = key;
            root.keyCount = 1;
        } else {
            // When root is full, tree grows in height
            if (root.isFull()) {
                BTreeNode newRoot = new BTreeNode(minDegree, false);
                newRoot.children[0] = root;
                newRoot.splitChild(0, root);
                root = newRoot;
            }
            root.insertNonFull(key);
        }
    }

    public void delete(int key) {
        if (root == null) return;
        root.delete(key);
        // Shrink the root if necessary
        if (root.keyCount == 0) {
            if (!root.isLeaf()) {
                root = root.children[0];
            } else {
                root = null;
            }
        }
    }

    @Override
    public String toString() {
        if (root == null) {
            return "Tree is empty";
        }
        return prettyPrint(root, "", true);
    }

    private String prettyPrint(BTreeNode node, String prefix, boolean isLast) {
        StringBuilder sb = new StringBuilder();

        sb.append(prefix);
        sb.append(isLast ? "└── " : "├── ");
        sb.append(node.toString()).append("\n");

        // Adjust the prefix for the children
        String newPrefix = prefix + (isLast ? "    " : "│   ");

        // Recursively print the children
        for (int i = 0; i <= node.keyCount; i++) {
            if (i < node.keyCount + 1 && node.children[i] != null) {
                sb.append(prettyPrint(node.children[i], newPrefix, i == node.keyCount));
            }
        }

        return sb.toString();
    }
}


