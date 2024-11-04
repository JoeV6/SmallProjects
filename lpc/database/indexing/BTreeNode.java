package org.lpc.database.indexing;

import java.util.Arrays;

public class BTreeNode {
    public int[] keys;
    public BTreeNode[] children;
    public int keyCount;
    public boolean isLeaf;
    private final int minDegree;


    public BTreeNode(int minDegree, boolean isLeaf) {
        this.minDegree = minDegree;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * minDegree - 1]; // Maximum number of keys is 2 * t - 1
        this.children = new BTreeNode[2 * minDegree]; // Maximum number of children is 2 * t
        this.keyCount = 0;
    }

    public BTreeNode search(int key) {
        int i = 0;
        // Find the first key greater than or equal to key
        while (i < keyCount && key > keys[i]) {
            i++;
        }
        // If the found key is equal to the key, return this node
        if (i < keyCount && keys[i] == key) {
            return this;
        }
        // If the key is not found and this is a leaf node, return null
        if (isLeaf) {
            return null;
        }
        // Recur to the appropriate child node
        return children[i].search(key);
    }

    public void insertNonFull(int key) {
        int i = keyCount - 1;

        if (isLeaf) {
            // Shift keys greater than key to make space
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            keyCount++;
        } else {
            // Find the child where the key should go
            while (i >= 0 && keys[i] > key) {
                i--;
            }
            i++;
            // If the child is full, split it
            if (children[i].isFull()) {
                splitChild(i, children[i]);
                if (keys[i] < key) {
                    i++;
                }
            }
            children[i].insertNonFull(key);
        }
    }

    // Split a full child
    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.minDegree, y.isLeaf);
        z.keyCount = minDegree - 1;

        // Transfer keys and children from y to z
        for (int j = 0; j < minDegree - 1; j++) {
            z.keys[j] = y.keys[j + minDegree];
        }

        if (!y.isLeaf) {
            for (int j = 0; j < minDegree; j++) {
                z.children[j] = y.children[j + minDegree];
            }
        }
        y.keyCount = minDegree - 1;

        // Insert the new child into the current node
        for (int j = keyCount; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }
        children[i + 1] = z;

        for (int j = keyCount - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }
        keys[i] = y.keys[minDegree - 1];
        keyCount++;
    }

    // Delete a key from the node
    public void delete(int key) {
        int idx = findKey(key);

        // Case 1: If the key is in this node and this is a leaf node
        if (isLeaf) {
            if (idx < keyCount && keys[idx] == key) {
                // Shift keys to remove the key
                for (int i = idx; i < keyCount - 1; i++) {
                    keys[i] = keys[i + 1];
                }
                keyCount--; // Reduce the number of keys
            }
        } else {
            // Case 2: The key is in an internal node
            if (idx < keyCount && keys[idx] == key) {
                deleteInternalNodeKey(idx);
            } else {
                // Case 3: The key is not in this node, recurse to the appropriate child
                boolean isLastChild = (idx == keyCount);
                if (children[idx].keyCount < minDegree) {
                    fill(idx);
                }
                // After filling, recurse to the appropriate child
                if (isLastChild && idx > keyCount) {
                    children[idx - 1].delete(key);
                } else {
                    children[idx].delete(key);
                }
            }
        }
    }

    // Find the index of the first key that is greater than or equal to the key
    private int findKey(int key) {
        int idx = 0;
        while (idx < keyCount && keys[idx] < key) {
            idx++;
        }
        return idx;
    }

    // Handle the deletion of a key in an internal node
    private void deleteInternalNodeKey(int idx) {
        int key = keys[idx];

        // Case 1: If the child before idx has at least minDegree keys, replace the key with the predecessor
        if (children[idx].keyCount >= minDegree) {
            int predKey = getPredecessor(idx);
            keys[idx] = predKey;
            children[idx].delete(predKey);
        }
        // Case 2: If the child after idx has at least minDegree keys, replace the key with the successor
        else if (children[idx + 1].keyCount >= minDegree) {
            int succKey = getSuccessor(idx);
            keys[idx] = succKey;
            children[idx + 1].delete(succKey);
        }
        // Case 3: If both children have fewer than minDegree keys, merge them and delete the key from the merged node
        else {
            merge(idx);
            children[idx].delete(key);
        }
    }

    // Get the predecessor of the key at index idx
    private int getPredecessor(int idx) {
        BTreeNode current = children[idx];
        while (!current.isLeaf) {
            current = current.children[current.keyCount];
        }
        return current.keys[current.keyCount - 1];
    }

    // Get the successor of the key at index idx
    private int getSuccessor(int idx) {
        BTreeNode current = children[idx + 1];
        while (!current.isLeaf) {
            current = current.children[0];
        }
        return current.keys[0];
    }

    // Fill the child at idx if it has fewer than minDegree keys
    private void fill(int idx) {
        if (idx != 0 && children[idx - 1].keyCount >= minDegree) {
            borrowFromPrev(idx);
        } else if (idx != keyCount && children[idx + 1].keyCount >= minDegree) {
            borrowFromNext(idx);
        } else {
            if (idx != keyCount) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }
    }

    // Borrow a key from the previous sibling
    private void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];

        // Shift the child keys to the right
        for (int i = child.keyCount - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = child.keyCount; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }

        child.keys[0] = keys[idx - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.keyCount];
        }

        keys[idx - 1] = sibling.keys[sibling.keyCount - 1];

        child.keyCount += 1;
        sibling.keyCount -= 1;
    }

    // Borrow a key from the next sibling
    private void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[child.keyCount] = keys[idx];
        if (!child.isLeaf) {
            child.children[child.keyCount + 1] = sibling.children[0];
        }

        keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.keyCount; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.keyCount; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.keyCount += 1;
        sibling.keyCount -= 1;
    }

    // Merge the child at idx with the next sibling
    private void merge(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[minDegree - 1] = keys[idx];

        for (int i = 0; i < sibling.keyCount; i++) {
            child.keys[i + minDegree] = sibling.keys[i];
        }

        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.keyCount; i++) {
                child.children[i + minDegree] = sibling.children[i];
            }
        }

        for (int i = idx + 1; i < keyCount; i++) {
            keys[i - 1] = keys[i];
        }

        for (int i = idx + 2; i <= keyCount; i++) {
            children[i - 1] = children[i];
        }

        child.keyCount += sibling.keyCount + 1;
        keyCount--;

        sibling = null; // Clear sibling reference
    }


    // Check if the node is full
    public boolean isFull() {
        return keyCount == 2 * minDegree - 1;
    }

    // Check if the node is a leaf
    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(keys, 0, keyCount));
    }
}
