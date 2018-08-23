package kidskeeper.sungshin.or.kr.kikee.Adult.Community;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.channels.NetworkChannel;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome.AdultHomeActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.request.BoardWrite;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardWriteActivity extends AppCompatActivity {
    @BindView(R.id.board_write_button_write)
    Button buttonWrite;
    @BindView(R.id.board_write_button_back)
    Button buttonBack;
    @BindView(R.id.board_write_edittext_title)
    EditText editTextTitle;
    @BindView(R.id.board_write_edittext_content)
    EditText editTextContent;

    private NetworkService service;
    private String user_idx;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_board_write);
        ButterKnife.bind(this);
        service = ApplicationController.getInstance().getNetworkService();
        clickEvent();
    }

    private void clickEvent() {
        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                user_idx = userInfo.getString("user_idx", "");
                nickname = userInfo.getString("nickname", "");
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                BoardWrite data = new BoardWrite(title, content, user_idx, nickname);
                if (title.equals("") || content.equals("")) {
                    Toast.makeText(getApplicationContext(), "제목과 내용을 모두 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Call<BaseResult> getBoardWriteResult = service.getBoardWriteResult(data);
                    getBoardWriteResult.enqueue(new Callback<BaseResult>() {
                        @Override
                        public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                switch (message) {
                                    case "SUCCESS":
                                        Toast.makeText(getApplicationContext(), "글 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), AdultHomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResult> call, Throwable t) {

                        }
                    });
                }


            }
        });
    }
}
