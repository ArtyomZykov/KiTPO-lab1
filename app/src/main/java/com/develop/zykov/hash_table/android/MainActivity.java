package com.develop.zykov.hash_table.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Element;
import android.util.Log;
import android.view.View;

import com.develop.zykov.hash_table.databinding.ActivityMainBinding;
import com.develop.zykov.hash_table.hash_objects.integer.MyInteger;
import com.develop.zykov.hash_table.hash_objects.smartphone.Smartphone;
import com.develop.zykov.hash_table.hash_objects.string.MyString;
import com.develop.zykov.hash_table.hash_table.HashTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String workedFileName = "";
    HashTable hashTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getStoragePermission();
        initListeners();

        binding.editTextFileName.setText("smartphoneSample");
        binding.editTextValueAdd.setText("{\"diagonal\": 0.253,\"five_g\": true}");
    }

    private void initListeners() {

        binding.downloadJsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(binding.editTextFileName.getText().toString());
                displayHashTable();
            }
        });
        binding.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hashTable.remove(Integer.parseInt(binding.editTextKeyRemove.getText().toString()));
                    displayHashTable();
                } catch (Exception e) {
                }
            }
        });
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Integer key = Integer.parseInt(binding.editTextKeyAdd.getText().toString());
                    JSONObject valueJson = new JSONObject(binding.editTextValueAdd.getText().toString());
                    hashTable.parsPut(key, valueJson);
                    displayHashTable();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadFile(String fileName) {
        workedFileName = fileName;
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        try {
            File file = new File(downloadPath, fileName + ".json");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();


            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            if (jsonArray.length() == 0) return;
            String jsonArrayClass = jsonObject.get("data_class").toString();

            switch (jsonArrayClass) {
                case "Smartphone": {
                    hashTable = new HashTable<Integer, Smartphone>();
                    hashTable.add(jsonArray.length() + 1, new Smartphone());
                    break;
                }
                case "Integer": {
                    hashTable = new HashTable<Integer, MyInteger>();
                    hashTable.add(jsonArray.length() + 1, new MyInteger());
                    break;
                }
                case "String": {
                    hashTable = new HashTable<Integer, MyString>();
                    hashTable.add(jsonArray.length() + 1, new MyString());
                    break;
                }
                default:
                    return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonArrayItem = (JSONObject) jsonArray.get(i);
                JSONObject jsonItemObject = (JSONObject) jsonArrayItem.get("object");

                hashTable.parsPut(jsonArrayItem.get("key"), jsonItemObject);
            }
            hashTable.remove(jsonArray.length() + 1);

            displayHashTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayHashTable() {
        String table = hashTable.showFillingUniformity();
        binding.hashtableResultText.setText(table);
        Log.e("displayHashTable", "displayHashTable\n" + table);
    }

    private void getStoragePermission() {
        int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
    }
}