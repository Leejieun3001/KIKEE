package kidskeeper.sungshin.or.kr.kikee.Kids.KidsHome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import kidskeeper.sungshin.or.kr.kikee.R;

public class AddToDoList extends Dialog {

    @BindView(R.id.todo_list_content)
    EditText todoContent;
    @BindView(R.id.todo_list_add_cancle)
    Button todoCancle;
    @BindView(R.id.todo_list_add)
    Button todoAdd;


    public AddToDoList(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.dialog_add_to_do_list);     //다이얼로그에서 사용할 레이아웃입니다.

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
                Toast.makeText(getContext(), "추가되었습니다", Toast.LENGTH_LONG).show();
                dismiss();   //다이얼로그를 닫는 메소드입니다.
            }
        });

    }
}