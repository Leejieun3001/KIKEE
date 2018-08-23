package kidskeeper.sungshin.or.kr.kikee.Kids.KidsHome;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.HomeWork.HomeWork;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.R;

public class KidsHome extends AppCompatActivity {

    @BindView(R.id.btn_go_bluetoothList)
    Button goBluetooth;
    @BindView(R.id.btn_go_homework)
    Button goHomework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_kids_home);
        ButterKnife.bind(this);
        onClickEvent();
    }
    void onClickEvent(){
        goBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(KidsHome.this,ConnectActivity.class);
                startActivity(intent1);
            }

        });
        goHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(KidsHome.this,HomeWork.class);
                startActivity(intent2);
            }
        });
    }
}
