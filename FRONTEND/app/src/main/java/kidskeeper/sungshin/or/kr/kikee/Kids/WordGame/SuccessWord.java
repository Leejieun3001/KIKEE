package kidskeeper.sungshin.or.kr.kikee.Kids.WordGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kidskeeper.sungshin.or.kr.kikee.Kids.KidsMain;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.OperateRobot;
import kidskeeper.sungshin.or.kr.kikee.R;

public class SuccessWord extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_word);

        Button btnword = (Button) this.findViewById(R.id.btn_go_wordGame);
        btnword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessWord.this, CategorySelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
