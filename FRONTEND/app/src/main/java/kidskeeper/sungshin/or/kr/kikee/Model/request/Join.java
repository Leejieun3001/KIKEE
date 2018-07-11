package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-07-11.
 */

public class Join {
    private int idx;
    private String iotNumber;
    private String password;
    private String email;
    private String nickname;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getIotNumber() {
        return iotNumber;
    }

    public void setIotNumber(String iotNumber) {
        this.iotNumber = iotNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Join(int idx, String iotNumber, String password, String email, String nickname) {
        this.idx = idx;
        this.iotNumber = iotNumber;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }
}
