package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-07-11.
 */

public class VerificationCodeResult {
    String message;
    String verificationCode;

    public VerificationCodeResult(String message, String verificationCode) {
        this.message = message;
        this.verificationCode = verificationCode;
    }

    public String getMessage() {
        return message;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

}
