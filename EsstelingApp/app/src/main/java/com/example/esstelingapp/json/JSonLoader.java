package com.example.esstelingapp.json;

import com.example.esstelingapp.R;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.Achievement;
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
        readAchievementsFile();
        readStoryFile();
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

    private static void readAchievementsFile(){
        String jsonString = "";
        try(InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("achievements.json")) {
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
                                achievementInFile.getBoolean("status"),
                                achievementInFile.getInt("progress")));
            }
            DataSingleton.getInstance().setAchievements(achievements);
        } catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    private static void readStoryFile(){
        String jsonParse = "";
        try(InputStream inputStream = DataSingleton.getInstance().getMainContext().getResources().openRawResource(R.raw.stories)){
            Scanner scanner = new Scanner(inputStream);
            while(scanner.hasNext()){
                jsonParse += scanner.nextLine();
            }

            scanner.close();
            JSONArray stories = new JSONArray(jsonParse);

            for(int i = 0; i < stories.length(); i++){
                JSONObject story = stories.getJSONObject(i);
                String storyName = story.getString("storyName");
                int storyProgress = story.getInt("storyProgress");
                String imageResource = story.getString("imageUrl");
                final int resId = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(imageResource, "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
                boolean storyStatus = story.getBoolean("storyStatus");
                DataSingleton.getInstance().addStory(new Story(storyName, resId, storyStatus, storyProgress));
            }

            for(Story story : DataSingleton.getInstance().getStories()){
                System.out.println(story.toString());
            }
        } catch (Error | IOException | JSONException e){
            e.printStackTrace();
        }
    }
}
