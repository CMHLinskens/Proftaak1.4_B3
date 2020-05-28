package com.example.esstelingapp.json;

import android.renderscript.ScriptGroup;
import android.util.JsonReader;

import com.example.esstelingapp.data.DataSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class JSonLoader {
    public static void readAllJsonFiles(){
        readQuizFile();
    }

    private static void readQuizFile(){
        String jsonString;
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("riddle_questions.json")){
            // Opening the file
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read();

            // Getting the array of the file
//            jsonString = new String(buffer, StandardCharsets.UTF_8);
//            JSONArray jsonFile = new JSONArray(jsonString);
            JSONArray jsonFile = new JSONArray();

            HashMap<String, HashMap<Integer, HashMap<String, ArrayList<String>>>> quizQuestions = new HashMap<>();

            for(int i = 0; i < jsonFile.length(); i++){
                JSONObject category = jsonFile.getJSONObject(i);
                String categoryName = category.getString("name");
                HashMap<Integer, HashMap<String, ArrayList<String>>> questionsMap = new HashMap<>();
                for(int j = 0; j < category.length() - 1; j++){
                    JSONObject question = category.getJSONObject("q" + j);
                    String questionName = question.getString("question");
                    JSONArray answerJArray = question.getJSONArray("answers");
                    ArrayList<String> answers = new ArrayList<>();
                    for(int x = 0; x < answerJArray.length(); x++){
                        answers.add((String)answerJArray.get(x));
                    }
                    HashMap<String, ArrayList<String>> questionAnswer = new HashMap<>();
                    questionAnswer.put(questionName, answers);
                    questionsMap.put(j, questionAnswer);
                    if(j == 5) {
                        System.out.println("top");
                    }
                }
                quizQuestions.put(categoryName, questionsMap);
            }
            System.out.println("Beetje redundant");
            DataSingleton.getInstance().setQuizQuestions(quizQuestions);
        } catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }


}
