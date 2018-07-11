package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018. 7. 11
 */

public class BaseResult {
    private String message;
    private String detail;

    public BaseResult(String message , String detail) {
        this.message = message;
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }
    public String getDetail() {return  detail;}
}
