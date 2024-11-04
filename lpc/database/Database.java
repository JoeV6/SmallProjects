package org.lpc.database;

import org.lpc.database.indexing.BTree;
import org.lpc.database.records.DatabaseRecord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Database {
    private final BTree index;
    private final Map<Integer, DatabaseRecord> records;

    public Database(int minDegree) {
        this.index = new BTree(minDegree);
        this.records = new HashMap<>();
    }

    public void insert(DatabaseRecord record) {
        int key = record.getKey();
        records.put(key, record);
        index.insert(key);
    }

    public DatabaseRecord retrieve(int key) {
        if (index.search(key) != null) {
            return records.get(key);
        }
        return null; // If key is not in the B-tree
    }

    public void delete(int key) {
        if (records.containsKey(key)) {
            records.remove(key);
            index.delete(key);
        } else {
            System.out.println("Record not found");
        }
    }

    public Set<DatabaseRecord> filterByField(String fieldName, Object value) {
        Set<DatabaseRecord> results = new HashSet<>();
        for (DatabaseRecord record : records.values()) {
            if (record.getField(fieldName) != null && record.getField(fieldName).equals(value)) {
                results.add(record);
            }
        }
        return results;
    }


    public void prettyPrintIndex() {
        System.out.println("B-tree structure:");
        System.out.println(index.toString());
    }

    public void prettyPrintDatabase() {
        System.out.println("Database records:");
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (DatabaseRecord record : records.values()) {
            System.out.println("Record Key: " + record.getKey());
            System.out.println("Fields:");


            for (Map.Entry<String, Object> entry : record.fields.entrySet()) {
                System.out.printf("    %s: %s%n", entry.getKey(), entry.getValue());
            }
            System.out.println();
        }
    }
}
