package kidskeeper.sungshin.or.kr.kikee.Kids.Word;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kidskeeper.sungshin.or.kr.kikee.R;

public class WordGame extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private RecyclerAdapter recyclerAdapter = null;
    private List<RecyclerModel>dataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game);
        init();
        addDummy();
        setRecyclerView();
    }
    private void init(){
        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<RecyclerModel>();
    }
    private void addDummy(){
        dataList.add(new RecyclerModel("감정","쉬움"));
        dataList.add(new RecyclerModel("동물","쉬움"));
        dataList.add(new RecyclerModel("과일","쉬움"));
        dataList.add(new RecyclerModel("ect","쉬움"));

    }
    private void setRecyclerView(){
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),R.layout.item_word_recycler,dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
        //horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);



    }
}
