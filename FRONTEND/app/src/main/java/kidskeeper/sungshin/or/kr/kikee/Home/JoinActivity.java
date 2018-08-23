package kidskeeper.sungshin.or.kr.kikee.Home;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Join;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.VerificationCodeResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    @BindView(R.id.join_linearlayout_container)
    LinearLayout containerLayout;
    @BindView(R.id.join_edittext_id)
    EditText editTextId;
    @BindView(R.id.join_button_duplication)
    Button buttonDuplication;
    @BindView(R.id.join_button_requestcode)
    Button buttonRequestCode;
    @BindView(R.id.join_edittext_checkcode)
    EditText editTextcheckCode;
    @BindView(R.id.join_button_checkcode)
    Button buttonCheckCode;
    @BindView(R.id.join_edittext_password)
    EditText editTextPassword;
    @BindView(R.id.join_edittext_repassword)
    EditText editTextRepassword;
    @BindView(R.id.join_edittext_nikname)
    EditText editTextNikname;
    @BindView(R.id.join_button_join)
    Button buttonJoin;
    @BindView(R.id.join_progressbar_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.join_edittext_checkiotNumber)
    EditText editTextIotNumber;
    @BindView(R.id.join_button_checkiotNumber)
    Button buttonCheckIotNum;

    private NetworkService service;
    private final String TAG = "JoinActivity";

    private boolean isDuplicate = false;
    private boolean isCheckEmail = false;
    private boolean isCheckIotNum = false;
    private String verificationCode = "not_valid_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        ButterKnife.bind(this);

        service = ApplicationController.getInstance().getNetworkService();
        bindClickListener();
    }


    //클릭 이벤트 바인딩
    public void bindClickListener() {
        //email 중복 체크
        buttonDuplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextId.getText().toString();
                if (email.equals("") || !email.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$")) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 맞지 않습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Call<BaseResult> checkId = service.getDuplicatedResult(email);
                    checkId.enqueue(new Callback<BaseResult>() {
                        @Override
                        public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getMessage().equals("SUCCESS")) {
                                    Toast.makeText(getApplicationContext(), "사용가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                    isDuplicate = true;
                                }
                                if (response.body().getMessage().equals("ALREADY_JOIN")) {
                                    Toast.makeText(getApplicationContext(), "이미 사용중인 이메일이 존재합니다. 다른 이메일로 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                    isDuplicate = false;
                                }
                                if (response.body().getMessage().equals("NOT_MATCH_REGULATION")) {
                                    Toast.makeText(getApplicationContext(), "정규식이 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    isDuplicate = false;
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

        //이메일 인증번호 요청
        buttonRequestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();
                if (!isDuplicate) {
                    Toast.makeText(getApplicationContext(), "이메일 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    final Call<VerificationCodeResult> checkNumber = service.getVerifiCodeResult(id);
                    checkNumber.enqueue(new Callback<VerificationCodeResult>() {
                        @Override
                        public void onResponse(Call<VerificationCodeResult> call, Response<VerificationCodeResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getMessage().equals("ALREADY_JOIN")) {
                                    Toast.makeText(getApplicationContext(), "이미 사용중인 이메일이 존재합니다. 다른 이메일로 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                                if (response.body().getMessage().equals("failuire")) {
                                    Toast.makeText(getApplicationContext(), "알수 없는 오류 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                if (response.body().getMessage().equals("SUCCESS")) {
                                    verificationCode = response.body().getVerificationCode();
                                    Toast.makeText(getApplicationContext(), "인증번호가 메일로 발송되었습니다. 확인후 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<VerificationCodeResult> call, Throwable t) {

                        }
                    });
                }
            }
        });

        //인증번호 확인
        buttonCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextcheckCode.getText().toString();
                if (code.equals("")) {
                    Toast.makeText(getApplicationContext(), "인증 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (code.equals(verificationCode)) {
                        isCheckEmail = true;
                        Toast.makeText(getApplicationContext(), "인증 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //iotNumber 체크
        buttonCheckIotNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iotNumber = editTextIotNumber.getText().toString();
                if (iotNumber.equals("")) {
                    Toast.makeText(getApplicationContext(), "로봇 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Call<BaseResult> checkIotNum = service.getCheckIotNumResult(iotNumber);
                    checkIotNum.enqueue(new Callback<BaseResult>() {
                        @Override
                        public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getMessage().equals("SUCCESS")) {
                                    Toast.makeText(getApplicationContext(), "로봇 번호를 확인했습니다. 감사합니다. ", Toast.LENGTH_SHORT).show();
                                    isCheckIotNum = true;
                                }
                                if (response.body().getMessage().equals("NOT_VAILD")) {
                                    Toast.makeText(getApplicationContext(), "유효한 번호가 아닙니다. 다시 한번 확인해 주세요. ", Toast.LENGTH_SHORT).show();
                                    isCheckIotNum = false;
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

        //회원가입 완료
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkValid(editTextId.getText().toString(), editTextPassword.getText().toString(), editTextRepassword.getText().toString(), editTextNikname.getText().toString())) {
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                int idx = 0;
                String iotNumber = editTextIotNumber.getText().toString().trim();
                String email = editTextId.getText().toString().trim();
                String password = editTextPassword.getText().toString();
                String nickname = editTextNikname.getText().toString().trim();
                Join join = new Join(idx, iotNumber, password, email, nickname);
                Call<BaseResult> getJoinResult = service.getJoinResult(join);

                getJoinResult.enqueue(new Callback<BaseResult>() {
                    @Override
                    public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            switch (message) {
                                case "SUCCESS":
                                    Toast.makeText(getApplicationContext(), "회원가입이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                case "ALREADY_JOIN":
                                    Toast.makeText(getApplicationContext(), "이미 가입된 유저 입니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                case "EMPTY_VALUE":
                                    Toast.makeText(getApplicationContext(), "파라미터 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                case "NULL_VALUE":
                                    Toast.makeText(getApplicationContext(), "받아야 할 파라미터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                case "NOT_CORRESPOND":
                                    Toast.makeText(getApplicationContext(), "두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                case "NOT_MATCH_REGULATION":
                                    Toast.makeText(getApplicationContext(), "입력 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "죄송합니다. 서버에 오류가 발생하였습니다. 빠른 시일내에 개선하겠습니다", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextId.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextPassword.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextcheckCode.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextNikname.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextRepassword.getWindowToken(), 0);
            }
        });
    }

    //  유효성 체크
    public boolean checkValid(String id, String password, String repassword, String name) {
        // 빈칸 체크
        if (id.equals("")) {
            Toast.makeText(getBaseContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.equals("") || repassword.equals("")) {
            Toast.makeText(getBaseContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.equals("")) {
            Toast.makeText(getBaseContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        //비밀번호 일치 체크
        if (!password.equals(repassword)) {
            Toast.makeText(getBaseContext(), "비밀번호와 비밀번호확인이 일치하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        //이메일 유효성 체크
        if (!id.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$")) {
            Toast.makeText(getBaseContext(), "이메일 형식이 맞지 않습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        //비밀번호 체크
        if (!password.matches("^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$")) {
            Toast.makeText(getBaseContext(), "비밀번호는 8자리이상 영문 숫자 조합입니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isDuplicate) {
            Toast.makeText(getBaseContext(), "이메일 중복 체크를 하지 않으셨습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isCheckEmail) {
            Toast.makeText(getBaseContext(), "이메일 인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
