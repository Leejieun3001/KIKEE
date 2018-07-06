package kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.Camera.CameraActivity;
import kidskeeper.sungshin.or.kr.kikee.R;

public class AdultHomeActivity extends AppCompatActivity {

    @BindView(R.id.adultHome_button_camera)
    Button buttonCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adult_home);
        ButterKnife.bind(this);

        clickEvent();
    }

    void clickEvent() {

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
