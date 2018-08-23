package kidskeeper.sungshin.or.kr.kikee.Network;

import kidskeeper.sungshin.or.kr.kikee.Model.request.BoardDetail;
import kidskeeper.sungshin.or.kr.kikee.Model.request.BoardWrite;
import kidskeeper.sungshin.or.kr.kikee.Model.request.CommentWrite;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Join;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Pick;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardDetailResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardListReult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.CategoryResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.LoginResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.TodoListResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.VerificationCodeResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.WordsResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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


    @GET("/board/total/mine")
    Call<BoardListReult> getMineBoardResult(@Query("user_idx") String user_idx);

    @POST("/board")
    Call<BoardDetailResult> getBoardDetailResult(@Body BoardDetail boardDetail);

    @POST("/board//write/comment")
    Call<BaseResult> getCommentWriteResult(@Body CommentWrite comment);

    @POST("/board/pick")
    Call<BaseResult> getPickResult(@Body Pick pick);

    @POST("/board/unpick")
    Call<BaseResult> getUnPickResult(@Body Pick pick);

    @POST("/board/write")
    Call<BaseResult> getBoardWriteResult(@Body BoardWrite boardWrite);

    @GET("/notice")
    Call<TodoListResult> getNoticeListResult(@Query("user_idx") String user_idx);

    @PUT("/notice/do")
    Call<BaseResult> getNoticDoResult(@Query("idx") String idx);

    @PUT("/notice/undo")
    Call<BaseResult> getNoticUnDoResult(@Query("idx") String idx);

}
