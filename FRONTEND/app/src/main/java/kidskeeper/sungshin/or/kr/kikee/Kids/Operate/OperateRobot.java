package kidskeeper.sungshin.or.kr.kikee.Kids.Operate;

import java.io.OutputStream;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.Connect.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.R;

public class OperateRobot extends AppCompatActivity{

    @BindView(R.id.btn_go_left)
    ImageButton btnLeft;
    @BindView(R.id.btn_go_right)
    ImageButton btnRight;
    @BindView(R.id.btn_go_top)
    ImageButton btnTop;
    @BindView(R.id.btn_go_back)
    ImageButton btnBack;
    @BindView(R.id.btn_go_stop)
    ImageButton btnStop;
    @BindView(R.id.go_to_list)
    Button btnList;

    String message = null;

    OutputStream mOutputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_operate_robot);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ButterKnife.bind(this);

        onClickButton();
//        beginListenForData();

    }

    void onClickButton(){
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "r";
                sendData(message);
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "l";
                sendData(message);
            }
        });
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "g";
                sendData(message);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_LONG).show();

                message = "b";
                sendData(message);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = "s";
                sendData(message);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(message ="s");
                Intent intent = new Intent(OperateRobot.this, PlayKidsMain.class);
                startActivity(intent);
                finish();
            }
        });

    }
    // 문자열 전송하는 함수(쓰레드 사용 x)
    public void sendData(String msg) {
        //msg += mStrDelimiter;  // 문자열 종료표시 (\n)
        try {
            //mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.
            // getBytes() : String을 byte로 변환
            // OutputStream.write : 데이터를 쓸때는 write(byte[]) 메소드를 사용함. byte[] 안에 있는 데이터를 한번에 기록해 준다.
            mOutputStream = ConnectActivity.mSocket.getOutputStream();
            mOutputStream.write(msg.getBytes());  // 문자열 전송.
        } catch (Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            Toast.makeText(getApplicationContext(), "데이터 전송중 오류가 발생", Toast.LENGTH_LONG).show();
            finish();  // App 종료
        }
    }
}
