package com.develop.zykov.hash_table.hash_objects.smartphone;

import java.util.Comparator;

public class SortSmartphoneByDiagonal implements Comparator<Smartphone> {
    @Override
    public int compare(Smartphone o1, Smartphone o2) {
        return o1.getDiagonal().compareTo(o2.getDiagonal());
    }
}
