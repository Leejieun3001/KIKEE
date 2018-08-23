package kidskeeper.sungshin.or.kr.kikee.Kids;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.OperateRobot;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.WordGame.CategorySelectActivity;
import kidskeeper.sungshin.or.kr.kikee.R;

public class PlayKidsMain extends AppCompatActivity {

    @BindView(R.id.playWithRobot)
    ImageButton buttonGoOperateRobot;
    @BindView(R.id.wordGame)
    ImageButton buttonGoWordGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_play_kids_main);
        ButterKnife.bind(this);
        clickEventPlayWithRobot();
        clickEventWordGame();
    }

    void clickEventPlayWithRobot() {
        buttonGoOperateRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayKidsMain.this, OperateRobot.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void clickEventWordGame() {
        buttonGoWordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PlayKidsMain.this, CategorySelectActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }
}
