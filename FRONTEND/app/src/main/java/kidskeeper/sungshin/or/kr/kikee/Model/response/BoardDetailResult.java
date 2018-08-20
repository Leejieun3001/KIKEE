package kidskeeper.sungshin.or.kr.kikee.Model.response;

import java.util.ArrayList;

/**
 * Created by LG on 2018-08-21.
 */

public class BoardDetailResult {
    private String message;
    private board board;
    private ArrayList<comments> comments;
    private String isMine;
    private String isPick;

    public BoardDetailResult(String message, kidskeeper.sungshin.or.kr.kikee.Model.response.board board, ArrayList<kidskeeper.sungshin.or.kr.kikee.Model.response.comments> comments, String isMine, String isPick) {
        this.message = message;
        this.board = board;
        this.comments = comments;
        this.isMine = isMine;
        this.isPick = isPick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public kidskeeper.sungshin.or.kr.kikee.Model.response.board getBoard() {
        return board;
    }

    public void setBoard(kidskeeper.sungshin.or.kr.kikee.Model.response.board board) {
        this.board = board;
    }

    public ArrayList<kidskeeper.sungshin.or.kr.kikee.Model.response.comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<kidskeeper.sungshin.or.kr.kikee.Model.response.comments> comments) {
        this.comments = comments;
    }

    public String getIsMine() {
        return isMine;
    }

    public void setIsMine(String isMine) {
        this.isMine = isMine;
    }

    public String getIsPick() {
        return isPick;
    }

    public void setIsPick(String isPick) {
        this.isPick = isPick;
    }
}
