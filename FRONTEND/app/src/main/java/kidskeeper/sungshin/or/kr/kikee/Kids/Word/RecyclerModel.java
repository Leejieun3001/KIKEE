package kidskeeper.sungshin.or.kr.kikee.Kids.Word;

public class RecyclerModel {
    private String category;
    private String level;

    public RecyclerModel(String category, String level){
        this.category = category;
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
