package com.example.esstelingapp.json;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.esstelingapp.ActionItem;
import com.example.esstelingapp.GameItem;
import com.example.esstelingapp.ReadingItem;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.StoryPiecesInterface;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.Question;
import com.example.esstelingapp.games.StoryTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class JSonLoader {
    private static boolean isColourBlind;

    public static void readAllJsonFiles() {
        Context mainContext = DataSingleton.getInstance().getMainContext();
        SharedPreferences sharedPreferences = mainContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String language = sharedPreferences.getBoolean("isDutch", true) ? "NL" : "EN";
        String colourblind = sharedPreferences.getBoolean("colour_blind_theme", false) ? "_cb" : "";

        readQuizFile(language);
        readFactFile(language);
        readAchievementsFile(language);
        readStoryFile(language, colourblind);
    }

    private static void readQuizFile(String language) {
        String jsonString = "";
        try (InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("riddle_questions" + language + ".json")) {
            // Opening the file and put everything into a String
            Scanner reader = new Scanner(in);
            while (reader.hasNext()) {
                jsonString += reader.nextLine();
            }

            // Getting the array of the file
            JSONArray jsonFile = new JSONArray(jsonString);
            reader.close();

            HashMap<String, HashMap<Integer, Question>> quizQuestions = new HashMap<>();

            for (int i = 0; i < jsonFile.length(); i++) {
                JSONObject category = jsonFile.getJSONObject(i);
                String categoryName = category.getString("name");
                HashMap<Integer, Question> questionsMap = new HashMap<>();
                for (int j = 0; j < category.length() - 1; j++) {
                    JSONObject question = category.getJSONObject("q" + j);
                    String questionName = question.getString("question");
                    JSONArray answerJArray = question.getJSONArray("answers");
                    String[] answers = new String[4];
                    for (int x = 0; x < answerJArray.length(); x++) {
                        answers[x] = answerJArray.getString(x);
                    }
                    questionsMap.put(j, new Question(StoryTypes.valueOf(categoryName), questionName, answers));
                }
                quizQuestions.put(categoryName, questionsMap);
            }
            DataSingleton.getInstance().setQuizQuestions(quizQuestions);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void readFactFile(String language) {
        String jsonString = "";
        try (InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("random_facts" + language + ".json")) {
            // Opening the file and put everything into a String
            Scanner reader = new Scanner(in);
            while (reader.hasNext()) {
                jsonString += reader.nextLine();
            }

            // Getting the array of the file
            JSONArray facts = new JSONArray(jsonString);
            reader.close();

            ArrayList<String> randomFacts = new ArrayList<>();

            for (int i = 0; i < facts.length(); i++) {
                randomFacts.add(facts.getString(i));
            }
            DataSingleton.getInstance().setRandomFacts(randomFacts);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void readAchievementsFile(String language) {
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("progress", Context.MODE_PRIVATE);

        String jsonString = "";
        try (InputStream in = DataSingleton.getInstance().getMainContext().getAssets().open("achievements" + language + ".json")) {
            // Opening the file and put everything into a String
            Scanner reader = new Scanner(in);
            while (reader.hasNext()) {
                jsonString += reader.nextLine();
            }

            // Getting the array of the file
            JSONArray achievementsFile = new JSONArray(jsonString);
            reader.close();

            ArrayList<Achievement> achievements = new ArrayList<>();

            for (int i = 0; i < achievementsFile.length(); i++) {
                JSONObject achievementInFile = achievementsFile.getJSONObject(i);
                achievements.add(new Achievement(achievementInFile.getString("name"),
                        preferences.getBoolean("a" + i, false),
                        preferences.getInt("a" + i, 0)));
            }
            DataSingleton.getInstance().setAchievements(achievements);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void readStoryFile(String language, String colourblind) {
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("progress", Context.MODE_PRIVATE);

        String jsonParse = "";
//        boolean isColourBlind = preferences.;
        try (InputStream inputStream = DataSingleton.getInstance().getMainContext().getAssets().open("stories" + language + ".json")) {
            Scanner reader = new Scanner(inputStream);
            while (reader.hasNext()) {
                jsonParse += reader.nextLine();
            }

            reader.close();
            JSONArray stories = new JSONArray(jsonParse);
            ArrayList<Story> storyList = new ArrayList<>();

            for (int i = 0; i < stories.length(); i++) {
                JSONObject story = stories.getJSONObject(i);
                String storyName = story.getString("storyName");
                int storyProgress = preferences.getInt("s" + i, 0);
                String imageResource = story.getString("imageUrl");
                final int resId = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(imageResource + colourblind, "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
                boolean storyStatus = preferences.getBoolean("s" + i, false);

                ArrayList<StoryPiecesInterface> piecesList = new ArrayList<>();
                JSONArray storyPieces = story.getJSONArray("storyPieces");

                for (int j = 0; j < storyPieces.length(); j++) {
                    JSONObject storyPiece = storyPieces.getJSONObject(j);
                        int pieceID = storyPiece.getInt("pieceID");
                        if (pieceID==1) {
                            String storyPartOne = storyPiece.getString("storyPartOne");
                            String storyPartTwo = storyPiece.getString("storyPartTwo");
                            final int story_2_image = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(storyPartTwo + colourblind, "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
                            String storyPartThree = storyPiece.getString("storyPartThree");
                            String storyPartFour = storyPiece.getString("storyPartFour");
                            final int story_4_image = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(storyPartFour + colourblind, "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
                            String storyPartFive = storyPiece.getString("storyPartFive");
                            ReadingItem piece = new ReadingItem(storyPartOne, storyPartThree, storyPartFive, story_2_image, story_4_image, 0, false);
                            piecesList.add(piece);
                        }
                        else if (pieceID==3){
                            String preText = storyPiece.getString("preActionText");
                                String postText = storyPiece.getString("postActionText");
                            ActionItem piece = new ActionItem(preText,postText,0,false);
                            piecesList.add(piece);
                        }else{
                            String tieInText = storyPiece.getString("tieInText");
                            GameItem piece = new GameItem(tieInText, 0, false);
                            piecesList.add(piece);
                        }
                }
                storyList.add(new Story(storyName, resId, storyStatus, piecesList, 0, 0, 0, 0));
            }
            DataSingleton.getInstance().setStories(storyList);
        } catch (Error | IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
