package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-08-22.
 */

public class BoardWrite {
    private String title;
    private String content;
    private String user_idx;
    private String nickname;

    public BoardWrite(String title, String content, String user_idx, String nickname) {
        this.title = title;
        this.content = content;
        this.user_idx = user_idx;
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(String user_idx) {
        this.user_idx = user_idx;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
