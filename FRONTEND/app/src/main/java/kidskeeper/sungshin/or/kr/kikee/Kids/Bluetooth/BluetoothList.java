package kidskeeper.sungshin.or.kr.kikee.Kids.Bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nainfox.bluetoothlib.bluetooth.BluetoothController;
import com.nainfox.bluetoothlib.bluetooth.BluetoothLeService;
import com.nainfox.bluetoothlib.bluetooth.Config;
import com.nainfox.bluetoothlib.bluetooth.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kidskeeper.sungshin.or.kr.kikee.Kids.KidsMain;
import kidskeeper.sungshin.or.kr.kikee.R;

public class BluetoothList extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;

    //UI 부분.
    CheckBox cbCheckAllow;
    Button btnBluetoothSearch;

    //검색된 기기의 목록을 처리하기 위한 부분
    List<BluetoothDevice> bluetoothDevices;
    List<Map<String, String>> dataDevice;
    SimpleAdapter adapterDevice;
    ListView searchDeviceList;

    //페어링 부분
    SimpleAdapter adapterPaired;
    List<Map<String,String>> dataPaired;
    ListView pairSucessList;

    int selectDevice;

    @Override
    protected void onCreate(Bundle savednstanceState){
        super.onCreate(savednstanceState);
        setContentView(R.layout.activity_bluetooth_list);

        //실행하는 기기 자체에 내장된 블루투스 Adapter 객체 반환
        //bluetoothAdapter = null 이면 해당 기기는 블루투스 사용 불가능
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDevices = new ArrayList<>();

        //검색된 기기의 목록을 저장하고 ListView 위에 출력해줌
        dataDevice = new ArrayList<>();
        adapterDevice = new SimpleAdapter(this, dataDevice, android.R.layout.simple_list_item_2, new String[]{"name", "address"}, new int[]{android.R.id.text1, android.R.id.text2});
        searchDeviceList = (ListView)findViewById(R.id.searchDeviceList);
        searchDeviceList.setAdapter(adapterDevice);

        selectDevice = -1;

        dataPaired = new ArrayList<>();
        adapterPaired = new SimpleAdapter(this, dataPaired, android.R.layout.simple_list_item_2, new String[]{"name", "address"}, new int[]{android.R.id.text1, android.R.id.text2});
        pairSucessList = (ListView)findViewById(R.id.pairSucessList);
        pairSucessList.setAdapter(adapterPaired);

        //다른 기기에서 내 기기를 검색할 수 있도록 허용하는 체크박스
        cbCheckAllow = (CheckBox)findViewById(R.id.cbCheckAllow);
        cbCheckAllow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                setCbCheckAllow();
            }
        });

        //내 인근에 연결 가능한 블루투스 장치를 검색하는 버튼
        btnBluetoothSearch = (Button)findViewById(R.id.btnBluetoothSearch);
        btnBluetoothSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                if(bluetoothAdapter.isDiscovering())
                {
                    bluetoothAdapter.cancelDiscovery();
                }
                else
                {
                    bluetoothAdapter.startDiscovery();
                }
            }
        });
        //BroadcastReceiver 사용을 위한 Intent 필터 등록.
        IntentFilter searchFilter = new IntentFilter();
        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED); // BluetoothAdapter.ACTION_DISCOVERY_STARTED : 블루투스 검색 시작.
        searchFilter.addAction(BluetoothDevice.ACTION_FOUND); // BluetoothDevice.ACTION_FOUND : 블루투스 디바이스 찾음.
        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); // BluetoothAdapter.ACTION_DISCOVERY_FINISHED : 블루투스 검색 종료.
        searchFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bluetoothDiscoveryReceiver, searchFilter);

        IntentFilter scanmodeFilter = new IntentFilter();
        scanmodeFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBluetoothScanmodeReceiver, scanmodeFilter);

        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(bluetoothConnectReceiver, connectFilter);

        // 검색된 디바이스목록 클릭시 페어링 요청
        searchDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                BluetoothDevice device = bluetoothDevices.get(position);

                try
                {
                    // 선택한 디바이스 페어링 요청
                    Method method = device.getClass().getMethod("createBond", (Class[]) null);
                    method.invoke(device, (Object[]) null);
                    selectDevice = position;
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    BroadcastReceiver bluetoothConnectReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action))
            {
                Toast.makeText(BluetoothList.this, device.getName() + "과 연결되었습니다.", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(BluetoothList.this,KidsMain.class);
                startActivity(i);
                finish();
            }
        }
    };

    BroadcastReceiver bluetoothDiscoveryReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            switch (action)
            {
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    dataDevice.clear();
                    bluetoothDevices.clear();
                    Toast.makeText(BluetoothList.this, "블루투스 검색을 시작합니다.", Toast.LENGTH_SHORT).show(); // 알림 메시지 출력.
                    break;

                // 블루투스 디바이스 찾음
                case BluetoothDevice.ACTION_FOUND:
                    // 검색한 블루투스 디바이스의 객체를 구함.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 데이터 저장
                    Map map = new HashMap();
                    map.put("name", device.getName()); //device.getName() : 블루투스 디바이스의 이름
                    map.put("address", device.getAddress()); //device.getAddress() : 블루투스 디바이스의 MAC 주소
                    dataDevice.add(map);

                    // 리스트 목록갱신
                    adapterDevice.notifyDataSetChanged();

                    //블루투스 디바이스 저장
                    bluetoothDevices.add(device);
                    break;

                // 블루투스 디바이스 검색 종료
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    break;

                // 블루투스 디바이스 페어링 상태 변화
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    BluetoothDevice paired = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(paired.getBondState() == BluetoothDevice.BOND_BONDED)
                    {
                        // 데이터 저장
                        Map map2 = new HashMap();
                        map2.put("name", paired.getName()); //device.getName() : 블루투스 디바이스의 이름
                        map2.put("address", paired.getAddress()); //device.getAddress() : 블루투스 디바이스의 MAC 주소
                        dataPaired.add(map2);

                        // 리스트 목록갱신
                        adapterPaired.notifyDataSetChanged();

                        // 검색된 목록
                        if(selectDevice != -1)
                        {
                            bluetoothDevices.remove(selectDevice);
                            dataDevice.remove(selectDevice);
                            adapterDevice.notifyDataSetChanged();
                            selectDevice = -1;
                        }
                    }
            }
        }
    };

    // '검색 허용' 체크 박스를 클릭했을 때 동작을 처리하는 메소드.
    private void setCbCheckAllow()
    {
        // '검색 허용' 체크 박스를 눌렀다면.
        if (cbCheckAllow.isChecked())
        {
            Toast.makeText(getApplicationContext(), "주변 기기에서 내 장치를 60초 동안 검색할 수 있도록 합니다.", Toast.LENGTH_SHORT).show(); // 알림 메시지 출력.

            // 검색 허용 모드가 활성화가 아니라면
            // BluetoothAdatper.SCAN_MODE_CONNECTABLE_DISCOVERABLE : 주변에서 내 기기를 검색할 수 있도록 허용하는 작업 Intent.
            if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
            {
                // BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE : 다른 기기에서 검색할 수 있도록 하는 작업 Intent.
                Intent searchAllowIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

                // BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION : 작업 시간을 설정하는 Intent Extra Key 값.
                // 60초 동안 상대방이 나를 검색하도록 함, 최대 120초까지 가능함.
                searchAllowIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
                startActivity(searchAllowIntent); // Intent 활성화.
            }
        }
    }

    // 블루투스 검색응답 모드 BroadcastReceiver
    BroadcastReceiver mBluetoothScanmodeReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1);
            switch (state)
            {
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                case BluetoothAdapter.SCAN_MODE_NONE:
                    break;
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                    break;
            }
        }
    };

    // activity 가 종료될때 호출되는 콜백 API
    @Override
    protected void onDestroy()
    {
        unregisterReceiver(bluetoothDiscoveryReceiver);
        unregisterReceiver(mBluetoothScanmodeReceiver);
        unregisterReceiver(bluetoothConnectReceiver);
        super.onDestroy();
    }
}


