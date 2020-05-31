package com.example.esstelingapp.json;

import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.data.DataSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class JSonSaver {

    public static void saveUserData(){
        saveAchievementData();
    }

    private static void saveAchievementData(){
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

            ArrayList<Achievement> achievementsInData = DataSingleton.getInstance().getAchievements();

            for(int i = 0; i < achievementsFile.length(); i++){
                JSONObject achievementInFile = achievementsFile.getJSONObject(i);
                if(achievementsInData.get(i).getAchievementStatus() != achievementInFile.getBoolean("status")){
                    achievementInFile.put("status", true);
                }
                if(achievementsInData.get(i).getAchievementProgress() != achievementInFile.getInt("progress")){
                    achievementInFile.put("progress", achievementsInData.get(i).getAchievementProgress());
                }
                achievementsFile.put(i, achievementInFile);
            }

            // Writing to the json file
            Writer output = null;
            File file = new File("///asset/achievements.json");
            output = new BufferedWriter(new FileWriter(file));
            output.write(achievementsFile.toString());
            output.close();

        } catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}
