package org.lunapark.dev.avionicus.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by znak on 31.03.2017.
 */

public class Parser {

    public Parser() {


    }

    public void parse(String sJSON) {
        JSONObject dataJsonObj;


        try {
            dataJsonObj = new JSONObject(sJSON);
            Iterator<String> stringIterator = dataJsonObj.keys();

            while (stringIterator.hasNext()) {
                String s = stringIterator.next();
                Log.e("JD", s);
            }

            JSONArray aPoints = dataJsonObj.getJSONArray("aPoints");
            Log.e("JD", "size: " + aPoints.length());
            for (int i = 0; i < aPoints.length(); i++) {
                JSONObject point = aPoints.getJSONObject(i);
                Log.e("JD", i + ": " + point.getLong(""));
            }

//            JSONArray friends = dataJsonObj.getJSONArray("friends");
//
//            // 1. достаем инфо о втором друге - индекс 1
//            JSONObject secondFriend = friends.getJSONObject(1);
//            secondName = secondFriend.getString("name");
//            Log.d(LOG_TAG, "Второе имя: " + secondName);
//
//            // 2. перебираем и выводим контакты каждого друга
//            for (int i = 0; i < friends.length(); i++) {
//                JSONObject friend = friends.getJSONObject(i);
//
//                JSONObject contacts = friend.getJSONObject("contacts");
//
//                String phone = contacts.getString("mobile");
//                String email = contacts.getString("email");
//                String skype = contacts.getString("skype");
//
//                Log.d(LOG_TAG, "phone: " + phone);
//                Log.d(LOG_TAG, "email: " + email);
//                Log.d(LOG_TAG, "skype: " + skype);
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
