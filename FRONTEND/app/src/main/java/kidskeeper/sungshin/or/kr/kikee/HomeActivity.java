package kidskeeper.sungshin.or.kr.kikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.LoginJoin.AdultLoginActivity;
import kidskeeper.sungshin.or.kr.kikee.Kids.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.Kids.WordGame.CategorySelectActivity;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_button_go_adult)
    Button buttonGoAdult;

    @BindView(R.id.home_button_go_kids)
    Button buttonGoConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        clickEvent();

    }

    void clickEvent() {
        buttonGoAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdultLoginActivity.class);
                startActivity(intent);
            }
        });
        buttonGoConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConnectActivity.class);
                startActivity(intent);
            }
        });


    }


}
