package kidskeeper.sungshin.or.kr.kikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome.AdultHomeActivity;

public class AdultLoginActivity extends AppCompatActivity {

    @BindView(R.id.adultlogin_edittext_id)
    EditText editTextId;
    @BindView(R.id.adultlogin_edittext_pw)
    EditText editTextPw;
    @BindView(R.id.adultlogin_button_login)
    Button buttonLogin;
    @BindView(R.id.adultlogin_button_back)
    Button buttonback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adult_login);
        ButterKnife.bind(this);
        clickEvent();
    }

    void clickEvent() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdultHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentBack);
                finish();

            }
        });
    }
}
