package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-08-24.
 */

public class NoticeWrite {
    private String todo;
    private String user_idx;

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(String user_idx) {
        this.user_idx = user_idx;
    }

    public NoticeWrite(String todo, String user_idx) {

        this.todo = todo;
        this.user_idx = user_idx;
    }
}
