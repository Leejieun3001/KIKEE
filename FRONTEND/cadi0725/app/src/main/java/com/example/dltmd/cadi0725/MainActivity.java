package com.example.dltmd.cadi0725;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private int mWidthPixels, mHeightPixels = 0;
    private PopupWindow popupWindow;
    Button btnPopupClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //전체창을 제어하는 객체 (가장 상위개념)
        WindowManager windowManager = getWindowManager();
        //화면 제어 객체
        Display display = windowManager.getDefaultDisplay();
        //화면의 해상도를 조절하는 객체
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //since SDK_INT = 1

        mWidthPixels = metrics.widthPixels;
        mHeightPixels = metrics.heightPixels;

        Button btnPopupOpen = (Button) findViewById(R.id.btnPopupOpen);
        btnPopupOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniPopupWindow();
            }
        });

    }

    private void iniPopupWindow() {
        try
        {
            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                                                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //inflater.inflate 는 처음은 xml 자체가(레이아웃 위치) 들어가고 두번째는 실제로 보여줄 애들
            View layout = inflater.inflate(R.layout.popup, (ViewGroup)findViewById(R.id.popup_element));
            popupWindow = new PopupWindow(layout, mWidthPixels-100,mHeightPixels-500,true);
            popupWindow.showAtLocation(layout,Gravity.CENTER,0,0);
            btnPopupClose = (Button)layout.findViewById(R.id.btnPopupClose);
            btnPopupClose.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    popupWindow.dismiss();
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
