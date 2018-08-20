package kidskeeper.sungshin.or.kr.kikee.Model.response;

import java.util.ArrayList;

import kidskeeper.sungshin.or.kr.kikee.Adult.Community.comments;

/**
 * Created by LG on 2018-08-21.
 */

public class BoardDetailResult {
    private String message;
    private board board;
    private ArrayList<comments> comments;
    private String isMine;

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

    public ArrayList<kidskeeper.sungshin.or.kr.kikee.Adult.Community.comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<kidskeeper.sungshin.or.kr.kikee.Adult.Community.comments> comments) {
        this.comments = comments;
    }

    public String getIsMine() {
        return isMine;
    }

    public void setIsMine(String isMine) {
        this.isMine = isMine;
    }

    public BoardDetailResult(String message, kidskeeper.sungshin.or.kr.kikee.Model.response.board board, ArrayList<kidskeeper.sungshin.or.kr.kikee.Adult.Community.comments> comments, String isMine) {
        this.message = message;
        this.board = board;
        this.comments = comments;
        this.isMine = isMine;
    }




}

