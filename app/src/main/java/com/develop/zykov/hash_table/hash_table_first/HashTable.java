package com.develop.zykov.hash_table.hash_table_first;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class HashTable<K, V extends HashTableItem> implements Map<K, V> {

    Comparator comparatorType;

    public class HashNode {
        K key;
        V value;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return this.key;
        }
        public V getValue() {
            return value;
        }
        public void setKey(K key) {
            this.key = key;
        }
        public void setValue(V value) {
            this.value = value;
        }
    }

    LinkedList<HashNode>[] table = null;
    int arraySize = 0;
    int count = 0;

    public HashTable(int size) {
        this.arraySize = size;
        this.table = new LinkedList[size];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<HashNode>();
        }
    }

    public void stableStruct() {
        LinkedList<HashNode>[] mTable = new LinkedList[arraySize];
        for (int i = 0, j = 0; i < arraySize; i++) {
            try {
                table[i].element();
                mTable[j++] = table[i];
            } catch (Exception e) {}
        }
        table = mTable;
    }

    public int hashFunction(K key) {
        int index;
        int i = key.hashCode();
        index = Math.abs(key.hashCode() % arraySize);
        return index;
    }

    @Override
    public V put(K key, V value) {
        int index = hashFunction(key);
        comparatorType = value.getTypeComparator();
        for (HashNode element : table[index]) {
            if (element.getKey().equals(key)) {
                V alt = element.value;
                element.value = value;
                return alt;
            }
        }
        HashNode hashNode = new HashNode(key, value);
        table[index].add(hashNode);
        count++;
        return null;
    }

    public HashTableItem parsPut(K key, String json) {
        HashTableItem value = getValueType().readValue(json);
//        put(key, (V) value);
        return value;
    }

    @Override
    public V get(K key) {
        int index = hashFunction(key);
        for (HashNode element : table[index]) {
            if (element.getKey().equals(key)) {
                return element.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        for (int i = 0; i < arraySize; i++) {
            try {
                if (table[i].element().key.equals(key)) {
                    HashNode element = table[i].element();
                    table[i].remove(element);
                    count--;
                    return element.value;
                }
            } catch (Exception e) {}
        }
        return null;
    }

    public Integer forEach(Consumer<String> lambda) {
        for (LinkedList<HashNode> hashNodes : table) {
            try {
                HashNode element = hashNodes.element();
                lambda.accept("key: " + element.key.toString() + "\nvalue: " + element.value.toString());
            } catch (Exception e) { // NoSuchElementException -> return
                return null;
            }
        }
        return 0;
    }

    public HashTableItem parsValue(JSONObject json) {
        return getValueType().parseValue(json);
    }

    V getValueType() {
        Arrays.stream(table).findFirst();
        for (LinkedList<HashNode> hashNodes : table) {
            try {
                HashNode element = hashNodes.element();
                return element.getValue();
            } catch (Exception e) { // NoSuchElementException -> return

            }
        }
        return null;
    }

    // Sort
    public void mergeSort() {
//        stableStruct();
        mergeSort(table, count);
    }

    private void mergeSort(LinkedList<HashNode>[] a, int n) {
        if (n < 2) return;
        int mid = n / 2;

        LinkedList<HashNode>[] r = new LinkedList[mid + 1];
        LinkedList<HashNode>[] l = new LinkedList[n - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }

        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    private void merge(
            LinkedList<HashNode>[] a,
            LinkedList<HashNode>[] l,
            LinkedList<HashNode>[] r,
            int left,
            int right
    ) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            try {
                V lElementValue = l[i].get(0).getValue();
                V rElementValue = r[i].get(0).getValue();
                if (comparatorType.compare(lElementValue, rElementValue) < 0) {
                    a[k++] = l[i++];
                } else {
                    a[k++] = r[j++];
                }
            } catch (Exception e) {}
        }
        while (i < left) { a[k++] = l[i++]; }
        while (j < right) { a[k++] = r[j++]; }
    }

    public void stupidSort() {
        for (int j = 0; j < table.length; j++) {
            for (int i = 0; i < table.length - 1; i++) {
                V elementValue = table[i].element().getValue();
                V nextElementValue = table[i + 1].element().getValue();
                Comparator comparatorType = elementValue.getTypeComparator();

                if (comparatorType.compare(elementValue, nextElementValue) > 0) {
                    HashNode glass = table[i].element();
                    table[i] = table[i + 1];
                    table[i + 1].push(glass);
                }
            }
        }
    }
}