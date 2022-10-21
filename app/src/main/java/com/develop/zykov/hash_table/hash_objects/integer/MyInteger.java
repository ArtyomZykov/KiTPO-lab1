package com.develop.zykov.hash_table.hash_objects.integer;

import com.develop.zykov.hash_table.hash_table.IUserType;

import org.json.JSONException;
import org.json.JSONObject;

public class MyInteger implements IUserType {

    private final Integer data;

    public Integer getInteger() { return this.data; }

    public MyInteger(Integer data) { this.data = data; }

    public MyInteger() { this.data = null; }

    @Override
    public String toString() {
        return "integer - " + this.data;
    }

    @Override
    public IUserType copy() { return new MyInteger(this.data); }

    @Override
    public IUserType create() { return new MyInteger(); }

    @Override
    public String getClassName() { return this.getClass().getName(); }

    @Override
    public IUserType parseValue(JSONObject json) {
        try {
            return new MyInteger((Integer) json.get("integer"));
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String getKey() { return data.toString(); }

    @Override
    public String packValue() {
        return "{\"integer\":" + data.toString() + "}";
    }
}

