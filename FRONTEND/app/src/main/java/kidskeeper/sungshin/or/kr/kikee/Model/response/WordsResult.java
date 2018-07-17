package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-07-17.
 */

public class WordsResult {
    private String message;
    private String[] words;

    public WordsResult(String message, String[] words) {
        this.message = message;
        this.words = words;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }
}
