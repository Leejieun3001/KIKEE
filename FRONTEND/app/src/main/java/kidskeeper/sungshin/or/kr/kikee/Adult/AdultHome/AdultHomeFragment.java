package kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.Camera.CameraActivity;
import kidskeeper.sungshin.or.kr.kikee.Adult.Voice.VoiceActivity;
import kidskeeper.sungshin.or.kr.kikee.R;


public class AdultHomeFragment extends Fragment {

    @BindView(R.id.adultHome_button_camera)
    Button buttonCamera;
    @BindView(R.id.adultHome_button_voice)
    Button buttonVoice;

    public AdultHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adult_home, container, false);
        ButterKnife.bind(this, view);
        clickEvent();
        return view;
    }

    void clickEvent() {
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
}
