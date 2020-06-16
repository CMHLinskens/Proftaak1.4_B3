public class Code {
    private int StoryID;
    private String Name;
    private String Code;

    public Code(int storyID, String name, String code) {
        StoryID = storyID;
        this.Name = name;
        Code = code;
    }

    public int getStoryID() {
        return StoryID;
    }

    public String getName() {
        return Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
