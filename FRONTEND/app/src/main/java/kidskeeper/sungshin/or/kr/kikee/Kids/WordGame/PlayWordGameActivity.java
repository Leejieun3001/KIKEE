package kidskeeper.sungshin.or.kr.kikee.Kids.WordGame;

import android.content.Intent;
import android.os.WorkSource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Model.response.WordsResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.word;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayWordGameActivity extends AppCompatActivity {
    private NetworkService service;
    private final String TAG = "PlayWordGameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_word_game);
        service = ApplicationController.getInstance().getNetworkService();

        ButterKnife.bind(this);
        loadWord();
    }

    private void loadWord() {
        Intent intent = getIntent();
        String category = intent.getExtras().getString("category");
        Log.d(TAG, category);
        Call<WordsResult> getWordResult = service.getWordsResult(category);
        getWordResult.enqueue(new Callback<WordsResult>() {
            @Override
            public void onResponse(Call<WordsResult> call, Response<WordsResult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
                            for (int i = 0; i < 10; i++) {
                                String English = response.body().getWords().get(i).getEnglish();
                                String Korea = response.body().getWords().get(i).getKorea();
                                Log.d("단어", English + Korea+ String.valueOf(i+1));
                            }
                    }
                }
            }

        @Override
        public void onFailure (Call < WordsResult > call, Throwable t){

        }
    });

}
}
