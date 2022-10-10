package com.develop.zykov.hash_table;

import com.develop.zykov.hash_table.hash_objects.smartphone.Smartphone;
import com.develop.zykov.hash_table.hash_table_first.HashTable;
import com.develop.zykov.hash_table.hash_table_first.HashTableItem;
import org.json.JSONException;
import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws JSONException {
        HashTable<Integer, HashTableItem> hashTable = new HashTable<Integer, HashTableItem>(20);

        hashTable.put(0, new Smartphone(670.12, true));
        hashTable.put(1, new Smartphone(0.124, true));
        hashTable.put(2, new Smartphone(0.32, true));
        hashTable.put(3, new Smartphone(0.142, true));
        hashTable.put(4, new Smartphone(0.92, true));
        hashTable.put(5, new Smartphone(1.12, true));
        hashTable.put(6, new Smartphone(2.1294, true));
        hashTable.put(7, new Smartphone(2.12, true));
        hashTable.put(8, new Smartphone(6.12, true));
        hashTable.put(9, new Smartphone(123.12, true));

        hashTable.remove(2);
        hashTable.remove(3);
        hashTable.remove(0);

        hashTable.stableStruct();
        hashTable.mergeSort();

        hashTable.forEach(value -> {
            System.out.println(value);
        });

//        System.out.println("----------------------");
//        hashTable.mergeSort();
//
//        hashTable.forEach(value -> {
//            System.out.println(value);
//        });
    }
}