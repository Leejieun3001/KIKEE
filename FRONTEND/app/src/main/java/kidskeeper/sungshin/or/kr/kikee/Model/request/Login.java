package kidskeeper.sungshin.or.kr.kikee.Model.request;

/**
 * Created by LG on 2018-07-11.
 */

public class Login {
    private String iotNumber;
    private String password;

    public void setIotNumber(String iotNumber) {
        this.iotNumber = iotNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIotNumber() {

        return iotNumber;
    }

    public String getPassword() {
        return password;
    }

    public Login(String iotNumber, String password) {

        this.iotNumber = iotNumber;
        this.password = password;
    }
}
