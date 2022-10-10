package com.develop.zykov.hash_table.hash_table_first;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.util.Comparator;

public interface HashTableItem {

    public Comparator getTypeComparator();
    public HashTableItem copy();
    public HashTableItem create();
    public String getClassName();
    public HashTableItem parseValue(JSONObject json);
    public HashTableItem readValue(String json);
}

