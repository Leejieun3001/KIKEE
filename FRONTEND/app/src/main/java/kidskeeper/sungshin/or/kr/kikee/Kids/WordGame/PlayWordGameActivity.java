package kidskeeper.sungshin.or.kr.kikee.Kids.WordGame;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.WorkSource;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.RemoteConference;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.KidsMain;
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
    private static final int REQUEST_CODE = 1234;

    String answer;

    Intent intent;
    SpeechRecognizer mRecognizer;

    String English, Korea;

    private PackageManager pm;


    int correctCount=0;

    @BindView(R.id.solveAnswer)
    Button ifCorrect;



        // 구글 음성 인식 intent 생성
        public Intent getVoiceRecognitionIntent(String message) {
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, message);

            return intent;
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_word_game);
        service = ApplicationController.getInstance().getNetworkService();

        ButterKnife.bind(this);
        loadWord();


        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        //mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(intent);



    }


    public boolean recognitionAvailable() {
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        if (activities.size() != 0) {
            return true;
            // 지원할 경우 true 반환
        } else {
            return false;
            // 지원하지 않을 경우 false 반환
        }
    }
    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
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
                            for (int i = 0; i < 10; i++) {
                                English = response.body().getWords().get(i).getEnglish();
                                Korea = response.body().getWords().get(i).getKorea();
                                //Log.d("단어", English + Korea + String.valueOf(i + 1));
                                TextView txtE = (TextView) findViewById(R.id.textEnglish);
                                TextView txtK = (TextView) findViewById(R.id.textKorea);

                                txtE.setText(English);
                                txtK.setText(Korea);



                            ifCorrect.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(isConnected())
                                        {

                                            RecognitionListener listener = new RecognitionListener() {
                                                @Override
                                                public void onReadyForSpeech(Bundle bundle) {

                                                }

                                                @Override
                                                public void onBeginningOfSpeech() {

                                                }

                                                @Override
                                                public void onRmsChanged(float v) {

                                                }

                                                @Override
                                                public void onBufferReceived(byte[] bytes) {

                                                }

                                                @Override
                                                public void onEndOfSpeech() {

                                                }

                                                @Override
                                                public void onError(int i) {

                                                }

                                                @Override
                                                public void onResults(Bundle results) {
                                                    ///
                                                    String key= "";
                                                    key = SpeechRecognizer.RESULTS_RECOGNITION;
                                                    ArrayList<String> mResult = results.getStringArrayList(key);
                                                    String[] rs = new String[mResult.size()];

                                                    answer =rs.toString();


                                                }

                                                @Override
                                                public void onPartialResults(Bundle bundle) {

                                                }

                                                @Override
                                                public void onEvent(int i, Bundle bundle) {

                                                }
                                            };


                                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                                            startActivityForResult(intent,REQUEST_CODE);


                                            Log.i("word", SpeechRecognizer.RESULTS_RECOGNITION);
                                            

                                            if(answer == English){
                                                Toast.makeText(getApplication(),"정답입니다!", Toast.LENGTH_LONG).show();
                                                correctCount++;

                                            }
                                            else Toast.makeText(getApplicationContext(),"틀렸습니다.",Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "인터넷 연결을 해주세요",Toast.LENGTH_LONG).show();
                                            finish();
                                        }

                                    }
                                });



                            }
                    }
                }
            }

            @Override
            public void onFailure(Call<WordsResult> call, Throwable t) {

            }
        });


    }



}
