package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-08-07.
 */

public class CategoryResult {
    private String message;
    private String[] categorys ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getCategorys() {
        return categorys;
    }

    public void setCategorys(String[] categorys) {
        this.categorys = categorys;
    }

    public CategoryResult(String message, String[] categorys) {

        this.message = message;
        this.categorys = categorys;
    }
}
