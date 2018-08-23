package kidskeeper.sungshin.or.kr.kikee.Home;
;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome.AdultHomeActivity;
import kidskeeper.sungshin.or.kr.kikee.Kids.KidsHome.KidsHome;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.R;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.home_button_go_adult)
    ImageButton buttonGoAdult;

    @BindView(R.id.home_button_go_kids)
    ImageButton buttonGoKids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        clickEvent();


    }

    void clickEvent() {
        buttonGoAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdultHomeActivity.class);
                startActivity(intent);
            }
        });
        buttonGoKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), KidsHome.class);
                startActivity(intent);
            }
        });


    }


}
