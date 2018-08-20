package kidskeeper.sungshin.or.kr.kikee.Adult.Community;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardActivity;
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardAdapter;
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.BoardWriteActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.response.BoardListReult;
import kidskeeper.sungshin.or.kr.kikee.Model.response.board;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommunityFragment extends Fragment {

    @BindView(R.id.community_recyclerview_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.reviewlist_floatingbutton_fab)
    FloatingActionButton addNotice;

    private boolean flag = true;
    String TAG = "CommunityFragment";
    private NetworkService service;

    private LinearLayoutManager layoutManager;
    private BoardAdapter adapter;
    private ArrayList<board> itemList = new ArrayList<board>();

    public CommunityFragment() {

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
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, view);
        clickEvent();
        initRecyclerView();
        getBoardList();
        return view;
    }

    public void clickEvent()
    {
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BoardWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        itemList = new ArrayList<>();
        adapter = new BoardAdapter(getActivity().getApplicationContext(), itemList, clickEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<board> itemList) {
        adapter = new BoardAdapter(getContext(), itemList, clickEvent);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void getBoardList() {
        Call<BoardListReult> getBoardListResult = service.getBoardListResult();

        getBoardListResult.enqueue(new Callback<BoardListReult>() {
            @Override
            public void onResponse(Call<BoardListReult> call, Response<BoardListReult> response) {
                String message = response.body().getMessage();
                switch (message) {
                    case "SUCCESS":
                        itemList.addAll(response.body().getBoards());
                        setAdapter(itemList);
                        break;
                }

            }

            @Override
            public void onFailure(Call<BoardListReult> call, Throwable t) {

            }
        });

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = itemList.get(itemPosition).getIdx();
            Intent intent = new Intent(getActivity().getApplicationContext(), BoardActivity.class);

            intent.putExtra("idx", tempId);
            startActivity(intent);
        }
    };



}
