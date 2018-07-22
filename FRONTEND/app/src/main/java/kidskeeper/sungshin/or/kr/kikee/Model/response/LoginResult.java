package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-07-22.
 */

public class LoginResult {
    private String message;
    private String idx;
    private String nickname;

    public LoginResult(String message, String idx, String nickname) {
        this.message = message;
        this.idx = idx;
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
