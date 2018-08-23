package kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.Camera.CameraActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.request.TodoList;
import kidskeeper.sungshin.or.kr.kikee.Model.response.TodoListResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AdultHomeFragment extends Fragment {

    @BindView(R.id.adultHome_button_camera)
    Button buttonCamera;
    @BindView(R.id.adultHome__recyclerview_recyclerview)
    RecyclerView recyclerView;

    String user_idx;

    private boolean flag = true;
    String TAG = "AdultHomeFragment";
    private NetworkService service;

    private LinearLayoutManager layoutManager;
    private ToDoAdapter adapter;
    private ArrayList<TodoList> itemList = new ArrayList<TodoList>();

    public AdultHomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_adult_home, container, false);
        ButterKnife.bind(this, view);
        clickEvent();
        initRecyclerView();
        getTodoList();
        return view;
    }

    public void clickEvent() {
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initRecyclerView() {
        itemList = new ArrayList<>();
        adapter = new ToDoAdapter(getActivity().getApplicationContext(), itemList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<TodoList> itemList) {
        adapter = new ToDoAdapter(getContext(), itemList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void getTodoList() {

        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        user_idx = userInfo.getString("user_idx", "");
        Call<TodoListResult> getNoticeListResult = service.getNoticeListResult(user_idx);
        getNoticeListResult.enqueue(new Callback<TodoListResult>() {
            @Override
            public void onResponse(Call<TodoListResult> call, Response<TodoListResult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
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
}
