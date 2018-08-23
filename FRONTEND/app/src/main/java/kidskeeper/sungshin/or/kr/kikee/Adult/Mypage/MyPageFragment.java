package kidskeeper.sungshin.or.kr.kikee.Adult.Mypage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Home.LoginActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
import kidskeeper.sungshin.or.kr.kikee.R;

import static android.content.Context.MODE_PRIVATE;


public class MyPageFragment extends Fragment {

    @BindView(R.id.mypage_button_logout)
    Button buttonLogout;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        clickEvent();
        return view;
    }

    private void clickEvent() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialog();
            }
        });


    }

    private void LogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("로그아웃");
        builder.setMessage("정말 로그아웃 하실 것 입니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = userInfo.edit();
                        editor.putString("AutoLogin", "NO");
                        editor.commit();
                        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

}
