package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-08-23.
 */

public class todolist {
    private String idx;
    private String todo;
    private String isdo;

    public todolist(String idx, String todo, String isdo) {
        this.idx = idx;
        this.todo = todo;
        this.isdo = isdo;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getIsdo() {
        return isdo;
    }

    public void setIsdo(String isdo) {
        this.isdo = isdo;
    }
}
