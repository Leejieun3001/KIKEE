package kidskeeper.sungshin.or.kr.kikee.Kids.WordGame;

import android.icu.util.ULocale;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Model.request.Login;
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
                if(response.isSuccessful()){
                    String message = response.body().getMessage();
                    switch (message){
                        case "SUCCESS":
                            String[] category = response.body().getCategorys();

                            for(int i=0; i<category.length;i++){
                                LinearLayout linearLayout = new LinearLayout(getBaseContext());
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                //Button 생성
                                final Button btn = new Button(getBaseContext());

                                btn.setText(category[i]);
                                final String position = category[i];
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("log", "position :" + position);

                                        Toast.makeText(getApplicationContext(), "클릭한 position:" + position, Toast.LENGTH_LONG).show();


                                    }
                                });

                                linearLayout.addView(btn);

                                linearLayoutCategory.addView(linearLayout);


                            }


                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {

            }
        });
    }
}
