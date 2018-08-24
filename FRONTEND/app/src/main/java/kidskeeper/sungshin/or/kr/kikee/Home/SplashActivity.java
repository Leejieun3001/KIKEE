package kidskeeper.sungshin.or.kr.kikee.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
import kidskeeper.sungshin.or.kr.kikee.R;

public class SplashActivity extends AppCompatActivity {

    private String isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        try {
            Thread.sleep(2000);
            checkLogin();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkLogin() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        isLogin = userInfo.getString("AutoLogin", "NO");
        if (isLogin.equals("YES")) {
            goHome();
        } else {
            goLogin();
        }

    }

    private void goHome() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void goLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }

}
