package com.example.esstelingapp.json;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.esstelingapp.Story;
import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.StoryPiecesInterface;
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
       Context mainContext = DataSingleton.getInstance().getMainContext();
       SharedPreferences languagePref = mainContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
       String language = languagePref.getBoolean("isDutch", true)? "NL" : "EN";

        readQuizFile(language);
        readFactFile(language);
        readAchievementsFile(language);
        readStoryFile(language);
    }

    private static void readQuizFile(String language){
        String jsonString = "";
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("riddle_questions" + language + ".json")){
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

    private static void readFactFile(String language){
        String jsonString = "";
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("random_facts" + language + ".json")) {
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

    private static void readAchievementsFile(String language){
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("progress", Context.MODE_PRIVATE);

        String jsonString = "";
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("achievements" + language + ".json")) {
            // Opening the file and put everything into a String
            Scanner reader = new Scanner(in);
            while (reader.hasNext()) {
                jsonString += reader.nextLine();
            }

            // Getting the array of the file
            JSONArray achievementsFile = new JSONArray(jsonString);
            reader.close();

            ArrayList<Achievement> achievements = new ArrayList<>();

            for(int i = 0; i < achievementsFile.length(); i++){
                JSONObject achievementInFile = achievementsFile.getJSONObject(i);
                achievements.add(new Achievement(achievementInFile.getString("name"),
                                preferences.getBoolean("a"+i, false),
                                preferences.getInt("a"+i, 0)));
            }
            DataSingleton.getInstance().setAchievements(achievements);
        } catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    private static void readStoryFile(String language){
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("progress", Context.MODE_PRIVATE);

        String jsonParse = "";
        try(InputStream inputStream = DataSingleton.getInstance().getMainContext().getAssets().open("stories" + language + ".json")){
            Scanner reader = new Scanner(inputStream);
            while(reader.hasNext()){
                jsonParse += reader.nextLine();
            }

            reader.close();
            JSONArray stories = new JSONArray(jsonParse);

            for(int i = 0; i < stories.length(); i++){
                JSONObject story = stories.getJSONObject(i);
                String storyName = story.getString("storyName");
                int storyProgress = preferences.getInt("s"+i, 0);
                String imageResource = story.getString("imageUrl");
                final int resId = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(imageResource, "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
                boolean storyStatus = preferences.getBoolean("s"+i, true);
                DataSingleton.getInstance().addStory(new Story(storyName, resId, storyStatus, new ArrayList<StoryPiecesInterface>(), 0,0,0,0));
            }
            for(Story story : DataSingleton.getInstance().getStories()){
                System.out.println(story.toString());
            }
        } catch (Error | IOException | JSONException e){
            e.printStackTrace();
        }
    }
}
