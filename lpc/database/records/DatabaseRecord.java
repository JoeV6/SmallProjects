package org.lpc.database.records;

import java.util.HashMap;
import java.util.Map;

public class DatabaseRecord {
    private final int key;
    public final Map<String, Object> fields;

    public DatabaseRecord(int key) {
        this.key = key;
        this.fields = new HashMap<>();
    }

    public void addField(String fieldName, Object value) {
        fields.put(fieldName, value);
    }

    public Object getField(String fieldName) {
        return fields.get(fieldName);
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "DatabaseRecord{" +
                "key=" + key +
                ", fields=" + fields +
                '}';
    }
}

