package kidskeeper.sungshin.or.kr.kikee.Adult.Community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Model.request.BoardDetail;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardDetailResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity {

    @BindView(R.id.board_detail_title)
    TextView textViewTitle;
    @BindView(R.id.board_detail_content)
    TextView textViewContent;
    @BindView(R.id.board_detail_nickname)
    TextView textViewNickname;
    @BindView(R.id.board_detail_date)
    TextView textViewDate;
    @BindView(R.id.board_detail_hits)
    TextView textViewhits;
    @BindView(R.id.board_detail_picks)
    TextView textViewPicks;

    private NetworkService service;
    private String board_idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        service = ApplicationController.getInstance().getNetworkService();
        ButterKnife.bind(this);
        Intent gettingIntent = getIntent();
        board_idx = String.valueOf(gettingIntent.getIntExtra("idx", 1));

        loadData();
    }


    public void loadData() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String user_idx = userInfo.getString("user_idx", "");
        BoardDetail data = new BoardDetail(board_idx, user_idx);

        Call<BoardDetailResult> getBoardDetailResult = service.getBoardDetailResult(data);
        getBoardDetailResult.enqueue(new Callback<BoardDetailResult>() {
            @Override
            public void onResponse(Call<BoardDetailResult> call, Response<BoardDetailResult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
                            textViewTitle.setText(String.valueOf(response.body().getBoard().getTitle()));
                            textViewContent.setText(String.valueOf(response.body().getBoard().getContent()));
                            textViewNickname.setText(String.valueOf(response.body().getBoard().getNickname()));
                            textViewDate.setText(String.valueOf(response.body().getBoard().getDate()));
                            textViewhits.setText(String.valueOf(response.body().getBoard().getHits()));
                            textViewPicks.setText(String.valueOf(response.body().getBoard().getPick()));


                    }
                }

            }

            @Override
            public void onFailure(Call<BoardDetailResult> call, Throwable t) {

            }
        });

    }

}