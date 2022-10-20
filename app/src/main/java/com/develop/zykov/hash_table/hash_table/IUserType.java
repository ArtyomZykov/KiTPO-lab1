package com.develop.zykov.hash_table.hash_table;

import org.json.JSONObject;

public interface IUserType {

    public IUserType copy();
    public IUserType create();
    public String getClassName();
    public IUserType parseValue(JSONObject json); // Парсинг значения из Json в объект
}
