package com.develop.zykov.hash_table.hash_objects.smartphone;

import com.develop.zykov.hash_table.hash_table_first.HashTableItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

public class Smartphone implements HashTableItem {

    private final Double diagonal;
    private final boolean fiveG;

    public Double getDiagonal() { return this.diagonal; }

    public Smartphone(Double diagonal, boolean fiveG) {
        this.diagonal = diagonal;
        this.fiveG = fiveG;
    }

    @Override
    public String toString() {
        return "diagonal - " + this.diagonal + " fiveG - " + this.fiveG;
    }

    @Override
    public HashTableItem copy() {
        return new Smartphone(this.diagonal, this.fiveG);
    }

    @Override
    public HashTableItem create() {
        return null;
    }

    @Override
    public String getClassName() {
        return this.getClass().getName();
    }

    @Override
    public HashTableItem parseValue(JSONObject json) {
        try {
            return new Smartphone(
                    (Double) json.get("diagonal"),
                    (boolean) json.get("five_g")
            );
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public HashTableItem readValue(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return parseValue(jsonObject);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public Comparator getTypeComparator() {
        return new SortSmartphoneByDiagonal();
    }
}
