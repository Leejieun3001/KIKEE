package kidskeeper.sungshin.or.kr.kikee.Kids;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.bluetooth.BluetoothList;
import kidskeeper.sungshin.or.kr.kikee.R;

public class ConnectActivity extends AppCompatActivity {

    private android.os.Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //해당 activity를 작동하기 위해 표준으로 넣어야 하는 메인 함수같은 존재
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect); //xml파일과 java파일을 연결시켜주는 명령
        ButterKnife.bind(this);

        // alertDialog 를 만들기 위해 기본적으로 들어가는 속성들을 정리하는 것이 빌더 객체
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("블루투스 연결")    //제목같은 것
                .setMessage("원활한 사용을 위하여 블루투스 활성화가 진행되어야 합니다.\n블루투스 연결을 진행하시겠습니까?")  //실제 출력되는 메세지
                .setPositiveButton("활성화", new DialogInterface.OnClickListener() { // 버튼 내용(예 위치에 있는 버튼에 내용), 그에 띠른 이벤트를 정의
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {    //클릭했을때 실행되는 내용
                        clickEventGoBluetoothList();

                    }
                });

        final AlertDialog msgDialog = msgBuilder.create();  //실제 출력된 다이얼로그 위에 설정하는 내용을 뿌리기 위해 빌더 객체를 생성하고 값을 할당

        Button btnbluetoothlist = (Button) findViewById(R.id.btn_go_bluetoothList);
        btnbluetoothlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgDialog.show();

            }
        });
    }

    private void clickEventGoBluetoothList() {
                Intent intent = new Intent(ConnectActivity.this,BluetoothList.class);
                startActivity(intent);
                finish();

    }
}


