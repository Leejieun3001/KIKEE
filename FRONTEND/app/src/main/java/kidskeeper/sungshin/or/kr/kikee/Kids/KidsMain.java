package kidskeeper.sungshin.or.kr.kikee.Kids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.OperateRobot;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.WordGame.CategorySelectActivity;
import kidskeeper.sungshin.or.kr.kikee.R;

public class KidsMain extends AppCompatActivity {

    @BindView(R.id.playWithRobot)
    Button buttonGoOperateRobot;
    @BindView(R.id.wordGame)
    Button buttonGoWordGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_main);
        ButterKnife.bind(this);
        clickEventPlayWithRobot();
        clickEventWordGame();
    }

    void clickEventPlayWithRobot() {
        buttonGoOperateRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KidsMain.this, OperateRobot.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void clickEventWordGame() {
        buttonGoWordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(KidsMain.this, CategorySelectActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }
}
