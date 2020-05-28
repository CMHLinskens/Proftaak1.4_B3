package com.example.esstelingapp.json;

import com.example.esstelingapp.data.DataSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class JSonLoader {
    public static void readAllJsonFiles(){
        readQuizFile();
        readFactFile();
    }

    private static void readQuizFile(){
        String jsonString = "";
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("riddle_questions.json")){
            // Opening the file and put everything into a String
            Scanner reader = new Scanner(in);
            while(reader.hasNext()){
                jsonString += reader.nextLine();
            }

            // Getting the array of the file
            JSONArray jsonFile = new JSONArray(jsonString);
            reader.close();

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
                }
                quizQuestions.put(categoryName, questionsMap);
            }
            DataSingleton.getInstance().setQuizQuestions(quizQuestions);
        } catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    private static void readFactFile(){
        String jsonString = "";
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("random_facts.json")) {
            // Opening the file and put everything into a String
            Scanner reader = new Scanner(in);
            while (reader.hasNext()) {
                jsonString += reader.nextLine();
            }

            // Getting the array of the file
            JSONArray facts = new JSONArray(jsonString);
            reader.close();

            ArrayList<String> randomFacts = new ArrayList<>();

            for(int i = 0; i < facts.length(); i++){
                randomFacts.add(facts.getString(i));
            }
            DataSingleton.getInstance().setRandomFacts(randomFacts);
        } catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}
