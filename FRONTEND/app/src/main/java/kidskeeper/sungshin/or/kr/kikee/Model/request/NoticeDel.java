package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-08-24.
 */

public class NoticeDel {
    private String idx;
    private String user_idx;

    public NoticeDel(String idx, String user_idx) {
        this.idx = idx;
        this.user_idx = user_idx;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(String user_idx) {
        this.user_idx = user_idx;
    }
}
