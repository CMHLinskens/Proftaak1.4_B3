package com.example.esstelingapp.games;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class RiddleController {

    public static void main(String[] args) throws JSONException {
        JSONObject obj = new JSONObject("riddle_questions.json");


        JSONObject biggetje = obj.getJSONObject("biggetjes");
        int amount = biggetje.getInt("amount");

        System.out.println(amount);
    }

//    public RiddleController() throws JSONException {
//
//    }
}
