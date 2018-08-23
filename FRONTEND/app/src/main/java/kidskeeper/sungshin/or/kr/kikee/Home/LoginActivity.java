package kidskeeper.sungshin.or.kr.kikee.Home;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
import kidskeeper.sungshin.or.kr.kikee.Model.response.LoginResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.adult_login_edittext_id)
    EditText editTextId;
    @BindView(R.id.adult_login_edittext_pw)
    EditText editTextPw;
    @BindView(R.id.adult_login_button_login)
    Button buttonLogin;
    @BindView(R.id.adult_login_button_join)
    Button buttonJoin;
    @BindView(R.id.login_checkbox_auto_login)
    CheckBox checkBoxAutoLogin;
    private NetworkService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_login);
        service = ApplicationController.getInstance().getNetworkService();
        ButterKnife.bind(this);

        clickEvent();
    }

    void clickEvent() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextId.getText().toString().trim();
                String pw = editTextPw.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (pw.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Login login = new Login(email, pw);

                    Call<LoginResult> getLoginResult = service.getLoginResult(login);

                    getLoginResult.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            if (response.isSuccessful()) {

                                String message = response.body().getMessage();
                                switch (message) {
                                    case "SUCCESS":
                                        Toast.makeText(getApplicationContext(), response.body().getNickname() + "님! 로그인이  완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = userInfo.edit();
                                        editor.putString("user_idx", response.body().getIdx());
                                        editor.putString("nickname", response.body().getNickname());
                                        if (checkBoxAutoLogin.isChecked()) {
                                            editor.putString("AutoLogin", "YES");
                                        }
                                        editor.commit();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    case "NOT_SIGN_UP":
                                        Toast.makeText(getApplicationContext(), "일치하는 회원 정보가 없습니다.이메일과 비밀번호를 다시한번 확인해 주세요", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "INCORRECT_PASSWORD":
                                        Toast.makeText(getApplicationContext(), "이메일과 비밀번호를 다시한번 확인해 주세요", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {

                        }
                    });
                }
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intentBack);
                finish();

            }
        });
    }


}
