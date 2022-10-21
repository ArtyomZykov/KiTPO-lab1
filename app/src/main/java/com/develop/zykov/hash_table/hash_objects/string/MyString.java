package com.develop.zykov.hash_table.hash_objects.string;

import com.develop.zykov.hash_table.hash_table.IUserType;

import org.json.JSONException;
import org.json.JSONObject;

public class MyString implements IUserType {

    private final String data;

    public String getString() { return this.data; }

    public MyString(String data) { this.data = data; }

    public MyString() { this.data = null; }

    @Override
    public String toString() {
        return "str - " + this.data;
    }

    @Override
    public IUserType copy() { return new MyString(this.data); }

    @Override
    public IUserType create() { return new MyString(); }

    @Override
    public String getClassName() { return this.getClass().getName(); }

    @Override
    public IUserType parseValue(JSONObject json) {
        try {
            return new MyString((String) json.get("string"));
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String getKey() { return data; }

    @Override
    public String packValue() {
        return "{\"string\":\"" + data + "\"}";
    }
}
