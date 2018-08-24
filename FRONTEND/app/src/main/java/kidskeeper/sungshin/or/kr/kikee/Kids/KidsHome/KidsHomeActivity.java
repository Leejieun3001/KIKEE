package kidskeeper.sungshin.or.kr.kikee.Kids.KidsHome;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardDetailActivity;
import kidskeeper.sungshin.or.kr.kikee.Kids.Connect.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.request.TodoList;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BaseResult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.TodoListResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KidsHomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_go_Bluetooth)
    Button goBluetooth;
    @BindView(R.id.kidsHome_recyclerview_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.todolist_floatingbutton_fab)
    FloatingActionButton floatingActionButtonAdd;

    String user_idx;

    private boolean isDo;
    private NetworkService service;
    private LinearLayoutManager layoutManager;
    private ToDoKidsAdapter adapter;
    private ArrayList<TodoList> itemList = new ArrayList<TodoList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_kids_home);
        ButterKnife.bind(this);
        service = ApplicationController.getInstance().getNetworkService();

        onClickEvent();
        initRecyclerView();
        getTodoList();

    }

    void onClickEvent() {
        goBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(KidsHomeActivity.this, ConnectActivity.class);
                startActivity(intent1);
            }

        });

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addToDoListDialog = new AddDialog(KidsHomeActivity.this);
                addToDoListDialog.show();

                addToDoListDialog.setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                initRecyclerView();
                                getTodoList();
                            }
                        }
                );


            }
        });
    }

    public void initRecyclerView() {
        itemList = new ArrayList<>();
        adapter = new ToDoKidsAdapter(getApplicationContext(), itemList, clickEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<TodoList> itemList) {
        adapter = new ToDoKidsAdapter(this, itemList, clickEvent);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void getTodoList() {

        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        user_idx = userInfo.getString("user_idx", "");
        Call<TodoListResult> getNoticeListResult = service.getNoticeListResult(user_idx);
        getNoticeListResult.enqueue(new Callback<TodoListResult>() {
            @Override
            public void onResponse(Call<TodoListResult> call, Response<TodoListResult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
                            itemList.clear();
                            itemList.addAll(response.body().getTodos());
                            setAdapter(itemList);
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoListResult> call, Throwable t) {

            }
        });
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String tempId = itemList.get(itemPosition).getIdx();
            if (itemList.get(itemPosition).getIsdo().equals("1")) {
                isDo = true;
            } else {
                isDo = false;
            }
            if (!isDo) {
                Call<BaseResult> gerNoticeDoResult = service.getNoticDoResult(tempId);
                gerNoticeDoResult.enqueue(new Callback<BaseResult>() {
                    @Override
                    public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            switch (message) {
                                case "SUCCESS":
                                    getTodoList();
                                    isDo = true;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResult> call, Throwable t) {
                    }
                });

            } else {
                Call<BaseResult> gerNoticeUnDoResult = service.getNoticUnDoResult(tempId);
                gerNoticeUnDoResult.enqueue(new Callback<BaseResult>() {
                    @Override
                    public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            switch (message) {
                                case "SUCCESS":
                                    getTodoList();
                                    isDo = false;
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResult> call, Throwable t) {

                    }
                });

            }

        }
    };

    @Override
    public void onRestart() {
        super.onRestart();
        initRecyclerView();
        getTodoList();
    }

}
