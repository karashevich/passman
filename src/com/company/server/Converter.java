package com.company.server;


import com.company.structures.Item;
import org.jetbrains.annotations.NotNull;
import org.json.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by jetbrains on 9/22/14.
 */
//TODO: Change this JSONator with Jackson

public class Converter {

    @NotNull
    public static JSONObject itemToJSON(@NotNull Item item) throws JSONException {
        return new JSONObject()
                .put("link", item.getLink())
                .put("login", item.getLogin())
                .put("pass", item.getPass());
    }

    @NotNull
    public static Item jsonToItem(@NotNull JSONObject jsonObject) throws JSONException {

        return new Item(jsonObject.getString("link"), jsonObject.getString("login"),jsonObject.getString("pass"));
    }

    @NotNull
    public static JSONObject itemsToJSON(@NotNull Set<Item> itemSet) throws JSONException {

        JSONArray jsonArray = new JSONArray();
        for (Item item : itemSet) {
            jsonArray.put(itemToJSON(item));
        }
        
        return new JSONObject().put("items", jsonArray);
    }

    @NotNull
    public static Set<Item> jsonToItems(@NotNull JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("items");
        HashSet<Item> resultSet = new HashSet<Item>();

        for (int i = 0; i < jsonArray.length(); i++) {
            resultSet.add(jsonToItem(jsonArray.getJSONObject(i)));
        }

        return resultSet;
    }

    public static byte[] jsonArrToByteArr(JSONArray jsonArray) throws JSONException {

        final byte[] result = new byte[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {

            result[i] = (Byte) jsonArray.get(i);

        }

        return result;

    }

    public static void main(String[] args) throws JSONException {

        Item item1 = new Item("www.kremlin.ru", "Vladimir Putin", "putinka");
        Item item2 = new Item("www.kremlin.org", "Dmitry Medvedev", "medved");
        
        HashSet<Item> set = new HashSet<Item>();

        set.add(item1);
        set.add(item2);

        JSONObject jsonObject = itemsToJSON(set);

        HashSet<Item> items = new HashSet<Item>(jsonToItems(jsonObject));

        System.out.println(items);
        //System.out.println(jsonObject.toString());
        
    }

}

