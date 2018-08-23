package kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.Voice;
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
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardAdapter;
import kidskeeper.sungshin.or.kr.kikee.Adult.Voice.VoiceActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.response.board;
import kidskeeper.sungshin.or.kr.kikee.Model.response.todolist;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.http.Body;


public class AdultHomeFragment extends Fragment {

    @BindView(R.id.adultHome_button_camera)
    Button buttonCamera;
    @BindView(R.id.adultHome_button_voice)
    Button buttonVoice;
    @BindView(R.id.adultHome__recyclerview_recyclerview)
    RecyclerView recyclerView;


    private boolean flag = true;
    String TAG = "AdultHomeFragment";
    private NetworkService service;

    private LinearLayoutManager layoutManager;
    private ToDoAdapter adapter;
    private ArrayList<todolist> itemList = new ArrayList<todolist>();

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
        buttonVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), VoiceActivity.class);
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

    public void getTodoList() {


    }
}
