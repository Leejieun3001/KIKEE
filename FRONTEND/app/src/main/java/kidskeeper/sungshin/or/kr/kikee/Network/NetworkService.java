package kidskeeper.sungshin.or.kr.kikee.Network;

import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardDetailActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.request.BoardDetail;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Join;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardDetailResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardListReult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.CategoryResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.LoginResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.VerificationCodeResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.WordsResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kwonhyeon-a on 2018. 3. 28..
 */

public interface NetworkService {
    @POST("/login")
    Call<LoginResult> getLoginResult(@Body Login login);

    @POST("/join")
    Call<BaseResult> getJoinResult(@Body Join join);

    @GET("/join/check_dupplicate")
    Call<BaseResult> getDuplicatedResult(@Query("email") String email);

    @GET("/join/verificationCode")
    Call<VerificationCodeResult> getVerifiCodeResult(@Query("tempEmail") String id);

    @GET("/join/check_iotNumber")
    Call<BaseResult> getCheckIotNumResult(@Query("iotNumber") String iotNum);

    @GET("/words/category")
    Call<CategoryResult> getCategoryResult();

    @GET("/words")
    Call<WordsResult> getWordsResult(@Query("category") String category);

    @GET("/board/total")
    Call<BoardListReult> getBoardListResult();

    @POST("/board")
    Call<BoardDetailResult> getBoardDetailResult(@Body BoardDetail boardDetail);

//    @POST("/login/find_id")
//    Call<FindInfoResult> getFindIdResult(@Body FindId Info);
//
//    @POST("/login/find_password")
//    Call<FindInfoResult> getFindPwResult(@Body FindPassWord Info);


}
