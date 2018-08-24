package kidskeeper.sungshin.or.kr.kikee.Kids.KidsHome;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Model.request.NoticeWrite;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LG on 2018-08-24.
 */

public class AddDialog extends Dialog {
    @BindView(R.id.dialog_todo_list_edittext_content)
    EditText todoContent;
    @BindView(R.id.dialog_todo_list_button_cancle)
    Button todoCancle;
    @BindView(R.id.dialog_todo_list_button_ok)
    Button todoAdd;

    private NetworkService service;

    public AddDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 지저분한(?) 다이얼 로그 제목을 날림
        setContentView(R.layout.dialog_add_todo);
        ButterKnife.bind(this);
        service = ApplicationController.getInstance().getNetworkService();
        clickEvent();
    }


    public void clickEvent() {

        todoCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "취소되었습니다", Toast.LENGTH_LONG).show();
                dismiss();   //다이얼로그를 닫는 메소드입니다.
            }
        });

        todoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (todoContent.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "할일을 입력해 주세요!", Toast.LENGTH_SHORT).show();

                } else {

                    SharedPreferences userInfo = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                    String user_idx = userInfo.getString("user_idx", "");
                    NoticeWrite noticeWrite = new NoticeWrite(todoContent.getText().toString(), user_idx);
                    Call<BaseResult> getWriteResult = service.getNoticeWirteRsault(noticeWrite);

                    getWriteResult.enqueue(new Callback<BaseResult>() {
                        @Override
                        public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                switch (message) {
                                    case "SUCCESS":
                                        Toast.makeText(getContext(),"입력 되었습니다", Toast.LENGTH_SHORT).show();
                                        dismiss();
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
