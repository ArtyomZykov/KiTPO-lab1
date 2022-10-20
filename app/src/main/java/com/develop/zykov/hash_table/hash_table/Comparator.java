package com.develop.zykov.hash_table.hash_table;

import java.util.Comparator;

class NaturalComparator<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T a, T b) {
        return a.compareTo(b);
    }
}
