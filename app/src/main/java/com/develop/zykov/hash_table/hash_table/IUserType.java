package com.develop.zykov.hash_table.hash_table;

import org.json.JSONObject;

public interface IUserType {

    public IUserType copy();
    public IUserType create();
    public String getClassName();
    public String getKey();
    // Парсинг значения из Json в объект
    public IUserType parseValue(JSONObject json);
    public String packValue();
}
