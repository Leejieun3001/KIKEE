package kidskeeper.sungshin.or.kr.kikee.Model.response;

import java.util.ArrayList;

/**
 * Created by LG on 2018-08-14.
 */

public class BoardListReult {
    private String message;
    private ArrayList<board> boards;

    public BoardListReult(String message, ArrayList<board> boards) {
        this.message = message;
        this.boards = boards;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<board> boards) {
        this.boards = boards;
    }
}
