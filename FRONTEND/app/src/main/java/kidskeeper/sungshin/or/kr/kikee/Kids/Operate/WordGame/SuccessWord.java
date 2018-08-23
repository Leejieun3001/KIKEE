package kidskeeper.sungshin.or.kr.kikee.Kids.Operate.WordGame;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.OutputStream;

import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.R;
public class SuccessWord extends AppCompatActivity {

    OutputStream mOutputStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_success_word);


        if(PlayWordGameActivity.dance >= 7)
        {

            sendData("d");
            sendData("s");
        }

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
    public void sendData(String msg) {
        //msg += mStrDelimiter;  // 문자열 종료표시 (\n)
        try {
            //mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.
            // getBytes() : String을 byte로 변환
            // OutputStream.write : 데이터를 쓸때는 write(byte[]) 메소드를 사용함. byte[] 안에 있는 데이터를 한번에 기록해 준다.
            mOutputStream = ConnectActivity.getmSocket().getOutputStream();
            mOutputStream.write(msg.getBytes());  // 문자열 전송.
        } catch (Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            Toast.makeText(getApplicationContext(), "데이터 전송중 오류가 발생", Toast.LENGTH_LONG).show();
            finish();  // App 종료
        }
    }
}
