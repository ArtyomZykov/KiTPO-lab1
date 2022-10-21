package com.develop.zykov.hash_table.android;

import com.develop.zykov.hash_table.hash_objects.integer.MyInteger;
import com.develop.zykov.hash_table.hash_objects.smartphone.Smartphone;
import com.develop.zykov.hash_table.hash_objects.string.MyString;
import com.develop.zykov.hash_table.hash_table.HashTable;
import com.develop.zykov.hash_table.hash_table.IUserType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class HashTableFactory {

    private IUserType list[] = { new MyInteger(), new MyString(), new Smartphone() };
    public HashTable hashTable = new HashTable<IUserType>();
    private IUserType hashTableClass = null;

    private IUserType findHashTableClass(String className) {
        for (IUserType classItem : list) {
            if (classItem.getClassName() == className) {
                hashTableClass = classItem;
                return hashTableClass;
            }
        }
        hashTableClass = null;
        return hashTableClass;
    }

    public void parsFromJson(JSONObject jsonObject) {
        try {
            String jsonArrayClass = jsonObject.get("data_class").toString();
            findHashTableClass(jsonArrayClass);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            if (jsonArray.length() == 0) return;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonArrayItem = (JSONObject) jsonArray.get(i);
                hashTable.add(hashTableClass.parseValue(jsonArrayItem));
            }
        } catch (Exception e) {}
    }

    public JSONObject putToJson() {
        try {
            String res = "\"data\": [";
            List<IUserType> list = hashTable.getAll();
            for (int i = 0; i < list.size(); i++) {
                res += list.get(i).packValue();
                if (i != list.size() - 1) res += ",";
            }
            res += "]";
            return new JSONObject(res);
        } catch (Exception e) {
            return null;
        }
    }
}
