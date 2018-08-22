package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-08-22.
 */

public class Pick {
    private String board_idx;
    private String user_idx;

    public Pick(String board_idx, String user_idx) {
        this.board_idx = board_idx;
        this.user_idx = user_idx;
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
}
