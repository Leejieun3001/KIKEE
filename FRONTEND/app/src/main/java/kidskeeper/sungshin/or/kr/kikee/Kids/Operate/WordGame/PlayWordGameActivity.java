package kidskeeper.sungshin.or.kr.kikee.Kids.Operate.WordGame;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
//import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.telecom.RemoteConference;
import android.util.Log;
import android.view.View;

import android.widget.Button;
//import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.PlayKidsMain;
import kidskeeper.sungshin.or.kr.kikee.Model.response.WordsResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.word;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.ConnectActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayWordGameActivity extends AppCompatActivity {

    @BindView(R.id.btn_go_category)
    Button Category;
    @BindView(R.id.btn_go_kidsMain)
    Button Home;

    private NetworkService service;
    private final String TAG = "PlayWordGameActivity";
    private static final int REQUEST_CODE = 100;
    private int INDEX = 0;


    static int dance = 0;
    private int wrongIndex = 0;
    private String English, Korea;
    ArrayList<String> results;
    ArrayList<word> answers = null;


    OutputStream mOutputStream = null;

    @BindView(R.id.solveAnswer)
    Button ifCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_word_game);
        service = ApplicationController.getInstance().getNetworkService();
        ButterKnife.bind(this);
        loadWord();
        clickCategory();
        clickHome();
    }

    void clickHome() {
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PlayWordGameActivity.this, PlayKidsMain.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    void clickCategory() {
        Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PlayWordGameActivity.this, CategorySelectActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.d(TAG, String.valueOf("사이즈는 : " + results.size()));
            Toast.makeText(PlayWordGameActivity.this, results.get(0), Toast.LENGTH_SHORT).show();


            String tmp = results.get(0).toLowerCase();

            if (English.equals(tmp)) {

                Toast.makeText(PlayWordGameActivity.this, tmp + " 정답입니다.", Toast.LENGTH_SHORT).show();
                sendData("y");
                sendData("e");
                INDEX++;
                dance++;
                wrongIndex = 0;
                if (INDEX == 9) {
                    Toast.makeText(PlayWordGameActivity.this, "참 잘했어요!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SuccessWord.class);
                    startActivity(intent);
                } else {
                    checkWord();
                }

            } else {
                Toast.makeText(PlayWordGameActivity.this, tmp + " 오답입니다.", Toast.LENGTH_SHORT).show();
                sendData("n");

                sendData("e");
                wrongIndex++;

                checkWord();
                if (wrongIndex == 2) {
                    wrongIndex = 0;
                    INDEX++;
                    checkWord();
                }
            }


        }
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net != null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    public void find_voice() {
        // 인텐트를 만들고 액티비티를 시작한다.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "주소록 음성 검색");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void loadWord() {

        Intent intent = getIntent();
        String category = intent.getExtras().getString("category");
        Log.d(TAG, category);
        final Call<WordsResult> getWordResult = service.getWordsResult(category);
        getWordResult.enqueue(new Callback<WordsResult>() {
            @Override
            public void onResponse(Call<WordsResult> call, Response<WordsResult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
                            answers = response.body().getWords();
                    }
                    checkWord();
                }
            }

            @Override
            public void onFailure(Call<WordsResult> call, Throwable t) {

            }
        });

    }

    private void checkWord() {
        English = answers.get(INDEX).getEnglish();
        Korea = answers.get(INDEX).getKorea();
        TextView txtE = (TextView) findViewById(R.id.textEnglish);
        TextView txtK = (TextView) findViewById(R.id.textKorea);

        txtK.setText(Korea);
        txtE.setText(English);

        ifCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
//                                            Log.i("word", SpeechRecognizer.RESULTS_RECOGNITION);

                } else {
                    Toast.makeText(PlayWordGameActivity.this, "인터넷 연결을 해주세요", Toast.LENGTH_LONG).show();
                    finish();
                }
                find_voice();

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



