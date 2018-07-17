package kidskeeper.sungshin.or.kr.kikee.Kids;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;

public class BluetoothService {

    //블루투스 연결을 위해 필요한 객체
    private BluetoothAdapter btAdapter;

    private Activity mActivity;
    private Handler mHandler;
    public BluetoothService(Activity ac, Handler h)
    {
        mActivity = ac;
        mHandler = h;

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    public boolean getDeviceState()
    {
        if(btAdapter == null){
            return false;
        }
        else {
            return true;
        }
    }

    public void enableBluetooth(){
        if(btAdapter.isEnabled())
        {

        }
        else {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(i,1);
        }
    }

}
