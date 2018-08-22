package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-08-22.
 */

public class CommentWrite {
    private String  board_idx;
    private String user_idx;
    private String content;

    public CommentWrite(String board_idx, String user_idx, String content) {
        this.board_idx = board_idx;
        this.user_idx = user_idx;
        this.content = content;
    }

    public String getBoard_idx() {
        return board_idx;
    }

    public void setBoard_idx(String board_idx) {
        this.board_idx = board_idx;
    }

    public String getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(String user_idx) {
        this.user_idx = user_idx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
