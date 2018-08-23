package kidskeeper.sungshin.or.kr.kikee.Adult.Mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardAdapter;
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardDetailActivity;
import kidskeeper.sungshin.or.kr.kikee.Home.LoginActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardListReult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.board;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;


public class MyPageFragment extends Fragment {

    @BindView(R.id.mypage_button_logout)
    Button buttonLogout;
    @BindView(R.id.mypage_recyvlerview_recyclerview)
    RecyclerView recyclerView;

    String TAG = "CommunityFragment";
    private NetworkService service;

    private LinearLayoutManager layoutManager;
    private MineBoradAdpater adapter;
    private ArrayList<board> itemList = new ArrayList<board>();


    public MyPageFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApplicationController.getInstance().getNetworkService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        clickEvent();
        initRecyclerView();
        getMyBoard();
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

    private void initRecyclerView() {
        itemList = new ArrayList<>();
        adapter = new MineBoradAdpater(getActivity().getApplicationContext(), itemList, clickEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<board> itemList) {
        adapter = new MineBoradAdpater(getContext(), itemList, clickEvent);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = itemList.get(itemPosition).getIdx();

            SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();
            editor.putString("board_idx", String.valueOf(tempId));
            editor.commit();
            Intent intent = new Intent(getContext(), BoardDetailActivity.class);
            startActivity(intent);
        }
    };


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

    public void getMyBoard() {

        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        String user_idx = userInfo.getString("user_idx", "");
        Call<BoardListReult> getBoardListResult = service.getMineBoardResult(user_idx);
        getBoardListResult.enqueue(new Callback<BoardListReult>() {
            @Override
            public void onResponse(Call<BoardListReult> call, Response<BoardListReult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
                            itemList.addAll(response.body().getBoards());
                            setAdapter(itemList);
                            break;
                    }

                }

            }

            @Override
            public void onFailure(Call<BoardListReult> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        getMyBoard();
    }
}
