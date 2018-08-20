package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-08-14.
 */

public class board {
    private int idx;
    private String title;
    private String content;
    private String date;
    private int hits;
    private String user_idx;
    private String nickname;
    private String pick;

    public board(int idx, String title, String content, String date, int hits, String user_idx, String nickname, String pick) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.date = date;
        this.hits = hits;
        this.user_idx = user_idx;
        this.nickname = nickname;
        this.pick = pick;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
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

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }
}

