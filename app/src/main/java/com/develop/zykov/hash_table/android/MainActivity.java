package com.develop.zykov.hash_table.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.develop.zykov.hash_table.databinding.ActivityMainBinding;
import com.develop.zykov.hash_table.hash_objects.smartphone.Smartphone;
import com.develop.zykov.hash_table.hash_table_first.HashTable;
import com.develop.zykov.hash_table.hash_table_first.HashTableItem;

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
    ListAdapter adapter;
    HashTable hashTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getStoragePermission();
        initListeners();

        binding.editTextValueAdd.setText("{\"diagonal\": 0.253,\"five_g\": true}");
    }

    private void initListeners() {
        adapter = new ListAdapter(new ArrayList<String>());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.downloadJsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(binding.editTextFileName.getText().toString());
            }
        });
        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashTable.mergeSort();
                displayHashTable();
            }
        });
        binding.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hashTable.remove(Integer.parseInt(binding.editTextKeyRemove.getText().toString()));
                    displayHashTable();
                } catch (Exception e) {}
            }
        });
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashTable.stableStruct();
                Integer key = Integer.parseInt(binding.editTextKeyAdd.getText().toString());
                HashTableItem value = hashTable.parsPut(key, binding.editTextValueAdd.getText().toString());
                hashTable.put(key, value);
                displayHashTable();
                hashTable.forEach( it -> {
                    System.out.println(it);
                });
            }
        });
        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayHashTable();
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
                    hashTable = new HashTable<Integer, Smartphone>(jsonArray.length() * 2);
                    hashTable.put(jsonArray.length() + 1, new Smartphone(0.0009, true));
                    break;
                }
                default: return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonArrayItem = (JSONObject) jsonArray.get(i);
                JSONObject jsonItemObject = (JSONObject) jsonArrayItem.get("object");

                hashTable.put(jsonArrayItem.get("key"), hashTable.parsValue(jsonItemObject));
            }
            hashTable.remove(jsonArray.length() + 1);
            hashTable.stableStruct();

            displayHashTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayHashTable() {
        List<String> list = new ArrayList<>();
        hashTable.forEach(value -> {
            list.add(value.toString());
        });
        adapter.mDataSet = list;
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addition_isCorrect(String i) {

        HashTable<Integer, HashTableItem> hashTable = new HashTable<Integer, HashTableItem>(20);

        hashTable.put(0, new Smartphone(0.12, true));
        hashTable.put(1, new Smartphone(0.124, true));
        hashTable.put(2, new Smartphone(0.32, true));
        hashTable.put(3, new Smartphone(0.142, true));
        hashTable.put(4, new Smartphone(0.92, true));
        hashTable.put(5, new Smartphone(1.12, true));
        hashTable.put(6, new Smartphone(2.1294, true));
        hashTable.put(7, new Smartphone(2.12, true));
        hashTable.put(8, new Smartphone(6.12, true));
        hashTable.put(9, new Smartphone(123.12, true));

        hashTable.forEach(value -> {
            System.out.println(value);
        });

        System.out.println("----------------------");
//        hashTable.mergeSort();

        hashTable.forEach(value -> {
            System.out.println(value);
        });
    }

    private void getStoragePermission() {
        int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
    }
}