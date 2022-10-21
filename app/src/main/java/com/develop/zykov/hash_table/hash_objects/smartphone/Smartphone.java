package com.develop.zykov.hash_table.hash_objects.smartphone;

import com.develop.zykov.hash_table.hash_table.IUserType;

import org.json.JSONException;
import org.json.JSONObject;

public class Smartphone implements IUserType {

    private final Double diagonal;
    private final boolean fiveG;

    public Double getDiagonal() { return this.diagonal; }

    public Smartphone(Double diagonal, boolean fiveG) {
        this.diagonal = diagonal;
        this.fiveG = fiveG;
    }

    public Smartphone() {
        this.diagonal = null;
        this.fiveG = false;
    }

    @Override
    public String toString() {
        return "diagonal - " + this.diagonal + " fiveG - " + this.fiveG;
    }

    @Override
    public IUserType copy() {
        return new Smartphone(this.diagonal, this.fiveG);
    }

    @Override
    public IUserType create() {
        return new Smartphone();
    }

    @Override
    public String getClassName() {
        return this.getClass().getName();
    }

    @Override
    public IUserType parseValue(JSONObject json) {
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
    public String getKey() { return diagonal.toString(); }

    @Override
    public String packValue() {
        return "{\"diagonal\":" + diagonal.toString()
                + ",\"five_g\":" + fiveG + "}";
    }
}
