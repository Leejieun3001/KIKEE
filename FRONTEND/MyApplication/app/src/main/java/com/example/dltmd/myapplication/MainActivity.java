package com.example.dltmd.myapplication;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.security.Permission;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter  = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAllowCheck = (Button)findViewById(R.id.btnAllowCheck);
        btnAllowCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMyPermission();
            }
        });
    }
    private void checkMyPermission()
    {
        TedPermission.with(this)
                .setRationaleMessage("정상적인 앱 사용을 위해서 블루투스 권한 허가를 해주셔야 합니다")
                .setDeniedMessage("설정에 가서 권한 허가를 해주세요")
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN)
                .check();
    }
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted()
        {
            setBluetoothOnCheck();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };

    private void setBluetoothOnCheck()
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter.isEnabled())
        {
            Intent i = new Intent(MainActivity.this,BluetoothList.class);
            startActivity(i);
            finish();
        }
        else
        {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent,10);
        }
    }
    @Override
    // requestCode 응답코드 ,resultCode 결과코드
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 10 :
                if(resultCode == Activity.RESULT_OK)
                {
                    Intent i = new Intent(MainActivity.this,BluetoothList.class);
                    startActivity(i);
                    finish();
                }
                else
                {

                }
        }
    }
}
