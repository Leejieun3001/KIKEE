package kidskeeper.sungshin.or.kr.kikee.Model.response;

import java.util.ArrayList;

/**
 * Created by LG on 2018-07-17.
 */

public class WordsResult {
    private String message;
    private ArrayList<word> words;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<word> getWords() {
        return words;
    }

    public void setWords(ArrayList<word> words) {
        this.words = words;
    }

    public WordsResult(String message, ArrayList<word> words) {

        this.message = message;
        this.words = words;
    }
}
