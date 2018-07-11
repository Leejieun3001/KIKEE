package kidskeeper.sungshin.or.kr.kikee.Network;

import kidskeeper.sungshin.or.kr.kikee.Model.request.Join;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.VerificationCodeResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by LG on 2018-07-11.
 */

public interface NetworkService {
    @POST("/login")
    Call<BaseResult> getLoginResult(@Body Login login);

    @POST("/join")
    Call<BaseResult> getJoinResult(@Body Join join);

    @GET("/join/check_dupplicate")
    Call<BaseResult> getDuplicatedResult(@Query("id") String id);

    @GET("/join/verificationCode")
    Call<VerificationCodeResult> getVerifiCodeResult(@Query("tempEmail") String id);

}
