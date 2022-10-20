package com.develop.zykov.hash_table;

import com.develop.zykov.hash_table.hash_table.HashTable;

import org.json.JSONException;
import org.junit.Test;

import java.util.Hashtable;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws JSONException {
        HashTable<String, Integer> map = new HashTable<>();
        map.add("this", 1);
        map.add("coder", 2);
        map.add("this034", 4);
        map.add("hi", 50);
        map.add("23this", 13);
        map.add("3265f236coder", 24);
        map.add("436this0", 44);
        map.add("3465f34hi", 57);
        map.add("23this", 14);
        map.add("32652345236coder", 276);
        map.add("43f6this0", 45);
        map.add("345", 55);
        map.add("23afsthis", 133);
        map.add("2sdfafd35", 2234);
        map.add("43dsf6235this0", 425);
        map.add("34dfsf6534hi", 55324);
        map.add("this", 155);
        map.add("codfsdfder", 2543);
        map.add("thffbis034", 484);
        map.add("hifd", 56);
        map.add("fdfdsfd23this", 16);
        map.add("3265236coder", 2464);
        map.add("436fftfddfhis0", 445745);
        map.add("346fdhis", 1);
        map.add("326fd52345236coder", 24567);
        map.add("43f6this0", 422);
        map.add("345", 52345);
        map.add("2ff5", 2523);
        map.add("4362f35this0", 42345);
        map.add("34f6534hi", 5235);
        System.out.println(map.size());

        map.fillingUniformity();
    }
}