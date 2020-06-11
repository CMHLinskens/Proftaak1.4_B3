import javax.json.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataController {

    private static final String SAVE_FILE_NAME = "Resources/Codes.json";
    private ArrayList<Code> Codes;
    private boolean generateRandomly;
    private String Time;

    public DataController() {
        this.Codes = new ArrayList<>();

        try (Reader reader = new FileReader(SAVE_FILE_NAME)) {
            JsonReader jsonReader = Json.createReader(reader);
            JsonObject plannerJsonObject = jsonReader.readObject();
            JsonArray JSONCodes = plannerJsonObject.getJsonArray("Codes");
            Time = plannerJsonObject.getString("DateTimeStamp");
            generateRandomly = plannerJsonObject.getBoolean("GenerateRandomly");
            for (JsonObject code : JSONCodes.getValuesAs(JsonObject.class)) {
                String Name = code.getString("Name");
                String Code = code.getString("Code");
                int StoryID = code.getInt("StoryID");
                Codes.add(new Code(StoryID, Name, Code));
            }
        } catch (Exception e) {
            System.out.println("something went wrong:");
            e.printStackTrace();
        }
    }

public Code getCodeByID(int ID){
    for (Code code:Codes) {
        if (code.getStoryID()==ID){
            return code;
        }
    }
    return null;
}

    public void saveCodes(){
        try {
            JsonWriter writer = Json.createWriter(new FileWriter(SAVE_FILE_NAME));
            JsonObjectBuilder codeFileBuilder = Json.createObjectBuilder();
            JsonArrayBuilder codesBuilder = Json.createArrayBuilder();
            for (Code code : this.getCodes()) {
                JsonObjectBuilder CodeBuilder = Json.createObjectBuilder();
                CodeBuilder.add("Name", code.getName());
                CodeBuilder.add("StoryID", code.getStoryID());
                CodeBuilder.add("Code", code.getCode());

                codesBuilder.add(CodeBuilder);
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            codeFileBuilder.add("DateTimeStamp", dtf.format(now));
            codeFileBuilder.add("GenerateRandomly",generateRandomly);
            codeFileBuilder.add("Codes", codesBuilder);
            writer.writeObject(codeFileBuilder.build());
            writer.close();
            //TODO send json to MQTT broker
        }catch(Exception e){
            System.out.println("something went wrong:");
            e.printStackTrace();
        }
    }

    public ArrayList<Code> getCodes() {
        return this.Codes;
    }

    public void addCode(Code code) {
        this.Codes.add(code);
        this.saveCodes();
    }
    public int getCodeCount(){
        return Codes.size();
    }

    public String getTime() {
        return Time;
    }

    public boolean isGeneratingRandomly(){
        return generateRandomly;
    }

    public void changeToggleGenerateRandomly(){
        generateRandomly = !generateRandomly;
    }

    public void setCode(int CodeID, String newCode){
        for (Code code:Codes) {
            if (code.getStoryID()==CodeID){
                code.setCode(newCode);
            }
        }
    }
}
