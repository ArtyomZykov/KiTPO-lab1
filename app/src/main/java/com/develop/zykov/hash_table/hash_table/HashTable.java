package com.develop.zykov.hash_table.hash_table;

import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// A node of chains
class HashNode<K, V> {
    K key;
    V value;
    final int hashCode;

    // Reference to next node
    HashNode<K, V> next;

    // Constructor
    public HashNode(K key, V value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }
}

// Class to represent entire hash table
public class HashTable<V extends IUserType> {

    // bucketArray is used to store array of chains
    private ArrayList<HashNode<String, V>> bucketArray;
    // Current capacity of array list
    private int numBuckets;
    // Current size of array list
    private int size;

    // Constructor (Initializes capacity, size and
    // empty chains.
    public HashTable() {
        bucketArray = new ArrayList<>();
        numBuckets = 8;
        size = 0;
        // Create empty chains
        for (int i = 0; i < numBuckets; i++) bucketArray.add(null);
    }

    public int size() { return size; }

    public boolean isEmpty() {
        return size() == 0;
    }

    private final int hashCode(String key) {
        // 0x7FFFFFFF - чтобы избежать отрицательного индекса
        // в последствии
        return (Objects.hashCode(key) & 0x7FFFFFFF);
    }

    // This implements hash function to find index
    // for a key
    private int getBucketIndex(String key) {
        int hashCode = hashCode(key);
        return hashCode % numBuckets;
    }

    // Method to remove a given key
    public V remove(String key) {
        // Apply hash function to find index for given key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        // Get head of chain
        HashNode<String, V> head = bucketArray.get(bucketIndex);
        // Search for key in its chain
        HashNode<String, V> prev = null;
        while (head != null) {
            // If Key found
            if (head.key.equals(key) && hashCode == head.hashCode) break;
            // Else keep moving in chain
            prev = head;
            head = head.next;
        }
        // If key was not there
        if (head == null) return null;
        // Reduce size
        size--;
        // Remove key
        if (prev != null) prev.next = head.next;
        else bucketArray.set(bucketIndex, head.next);
        return head.value;
    }

    // Returns value for a key
    public V get(String key) {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<String, V> head = bucketArray.get(bucketIndex);
        // Search key in chain
        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode)
                return head.value;
            head = head.next;
        }
        // If key not found
        return null;
    }

    // Adds a key value pair to hash
    public void add(V value) {
        String key = value.getKey();
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<String, V> head = bucketArray.get(bucketIndex);
        // Check if key is already present
        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        // Insert key in chain
        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<String, V> newNode = new HashNode<String, V>(key, value, hashCode);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);
        // If load factor goes beyond threshold, then
        // double hash table size
        if ((1.0 * size) / numBuckets >= 0.8) {
            ArrayList<HashNode<String, V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++) bucketArray.add(null);
            for (HashNode<String, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }

    public List<IUserType> getAll() {
        List<IUserType> res = null;
        for (int i = 0; i < numBuckets; i++) {
            HashNode<String, V> head = bucketArray.get(i);
            while (head != null) {
                res.add(head.value);
                head = head.next;
            }
        }
        return res;
    }

    public String showFillingUniformity() {
        String res = "";
        for (int i = 0; i < numBuckets; i++) {
            res += "{" + i + "}\t\t";
            HashNode<String, V> head = bucketArray.get(i);
            if (head == null) res += "null -> ";
            // Check if key is already present
            while (head != null) {
                res += "(key=" + head.key.toString() + " val=" +
                        head.value.toString() + ") -> ";
                head = head.next;
            }
            res += "\n";
        }
        return res;
    }
}
