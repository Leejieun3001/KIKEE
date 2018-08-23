package kidskeeper.sungshin.or.kr.kikee.Kids.Operate.WordGame;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Kids.Operate.ConnectActivity;
import kidskeeper.sungshin.or.kr.kikee.Model.response.CategoryResult;
import kidskeeper.sungshin.or.kr.kikee.Network.ApplicationController;
import kidskeeper.sungshin.or.kr.kikee.Network.NetworkService;
import kidskeeper.sungshin.or.kr.kikee.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySelectActivity extends AppCompatActivity {
    private NetworkService service;
    private final String TAG = "CategorySelectActivity";
    @BindView(R.id.categoryselect_linear_category)
    LinearLayout linearLayoutCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_catagory_select);
        service = ApplicationController.getInstance().getNetworkService();

        ButterKnife.bind(this);
        loadCategory();
    }


    private void loadCategory() {
        Call<CategoryResult> getCategoryList = service.getCategoryResult();
        getCategoryList.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    switch (message) {
                        case "SUCCESS":
                            String[] category = response.body().getCategorys();

                            for (int i = 0; i < category.length; i++) {
                                LinearLayout linearLayout = new LinearLayout(getBaseContext());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.topMargin = 10;
                                params.bottomMargin = 10;

                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                linearLayout.setLayoutParams(params);
                                final Button btn = new Button(getBaseContext());
                                btn.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                btn.setText(category[i]);
                                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                final String position = category[i];
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(), "클릭한 position:" + position, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), PlayWordGameActivity.class);
                                        intent.putExtra("category", position);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                                linearLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                btn.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                linearLayout.addView(btn);
                                linearLayoutCategory.addView(linearLayout);
                            }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "서버가 불안정 합니다! 빠른 시일 내에 개선하겠습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {

            }
        });
    }
}
