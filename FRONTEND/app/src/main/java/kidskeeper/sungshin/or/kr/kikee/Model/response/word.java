package kidskeeper.sungshin.or.kr.kikee.Model.response;

/**
 * Created by LG on 2018-08-07.
 */

public class word {
    private String korea;
    private String English;

    public String getKorea() {
        return korea;
    }

    public void setKorea(String korea) {
        this.korea = korea;
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public word(String korea, String english) {

        this.korea = korea;
        English = english;
    }
}
