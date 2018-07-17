package kidskeeper.sungshin.or.kr.kikee.Kids;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kidskeeper.sungshin.or.kr.kikee.R;

public class bluetoothDeviceList extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;

    final static int BLUETOOTH_REQUEST_CODE = 100;

    private TextView txtState;
    private CheckBox chkFindme;
    private Button btnSearch;
    private ListView listPaired;
    private ListView listDevice;


    //선택된 디바이스 유무에 따른
    private int selectDevices;

    private SimpleAdapter adapterPaired;
    private SimpleAdapter adapterDevice;
    //페어링된 목록을 맵형태로 저장하는 리스트
    private List<Map<String,String>> dataPaired;
    //검색된 목록들을 저장하는 리스트
    private List<Map<String,String>> dataDevice;
    //블루투스 연결 관련 리스트를 저장하는 리스트
    private List<BluetoothDevice> bluetoothDevices;
    int selectDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device_list);

        txtState = (TextView)findViewById(R.id.txtState);
        chkFindme = (CheckBox)findViewById(R.id.chkFindme);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        listPaired = (ListView)findViewById(R.id.listPaired);
        listDevice = (ListView)findViewById(R.id.listDevice);

        //adapter1
        dataPaired = new ArrayList<>();
        adapterPaired = new SimpleAdapter(this, dataPaired, android.R.layout.simple_list_item_2
                                         ,new String[]{"name","address"}, new int[]{android.R.id.text1,android.R.id.text2});

        listPaired.setAdapter(adapterPaired);

        //Adapter2
        dataDevice = new ArrayList<>();
        adapterDevice = new SimpleAdapter(this, dataPaired, android.R.layout.simple_list_item_2
                ,new String[]{"name","address"}, new int[]{android.R.id.text1,android.R.id.text2});

        listDevice.setAdapter(adapterDevice);

        //검색된 블루투스 디바이스 데이터
        bluetoothDevices = new ArrayList<>();
        //선택된 디바이스 없음
        selectDevice = -1;

        //블루투스 지원유무 확인
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //블루투스 지원하지 않으면 null을 리턴
        if(mBluetoothAdapter == null){
            Toast.makeText(this, ".블루투스를 지원하지 않는 단말기 입니다.",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //블루투스 브로드캐스트 리시버 등록
        //리시버1
        IntentFilter stateFilter = new IntentFilter();
        stateFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothStateReceiver,stateFilter);

        //리시버2
        IntentFilter searchFilter = new IntentFilter();
        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        searchFilter.addAction(BluetoothDevice.ACTION_FOUND);
        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothSearchReceiver, searchFilter);

        //리시버3
        IntentFilter scanmodeFilter = new IntentFilter();
        scanmodeFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBluetoothScanmodeReceiver, scanmodeFilter);


        if(!mBluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BLUETOOTH_REQUEST_CODE);

        }else GetListPairedDevice();

        listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = bluetoothDevices.get(position);

                try{
                    Method method = device.getClass().getMethod("createBond", (Class[]) null);
                    method.invoke(device, (Object[]) null);
                    selectDevice = position;
                 }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
    }
    BroadcastReceiver mBluetoothStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,-1);

            if(state == BluetoothAdapter.STATE_ON){
                txtState.setText("블루투스 활성화");
            }

            else if (state == BluetoothAdapter.STATE_TURNING_ON){
                txtState.setText("블루투스 활성화 중...");
            }
            else if(state == BluetoothAdapter.STATE_TURNING_OFF){
                txtState.setText("블루투스 비활성화 중...");
            }
        }
    };

    //블루투스 검색 결과 BroadcastReceiver
    BroadcastReceiver mBluetoothSearchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                //블루투스 디바이스 검색 종료
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:dataDevice.clear();
                     bluetoothDevices.clear();
                     Toast.makeText(bluetoothDeviceList.this,"블루투스 검색 시작", Toast.LENGTH_SHORT).show();
                     break;

                case BluetoothDevice.ACTION_FOUND:
                    // 검색한 블루투스 디바이스의 객체를 구한다.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //데이터 저장
                    Map map = new HashMap();
                    map.put("name",device.getName());
                    map.put("address",device.getAddress());
                    dataDevice.add(map);
                    //리스트 목록갱신
                    adapterDevice.notifyDataSetChanged();

                    //블루투스 디바이스 저장
                    bluetoothDevices.add(device);
                    break;

                    //블루투스 디바이스 페어링 상태 변화
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    BluetoothDevice paired = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(paired.getBondState() == BluetoothDevice.BOND_BONDED){
                        //데이터 저장
                        Map map2 = new HashMap();
                        map2.put("name",paired.getName());
                        map2.put("address",paired.getAddress());
                        dataPaired.add(map2);

                        //리스트 목록 갱신
                        adapterPaired.notifyDataSetChanged();

                        //검색된 목록
                        if(selectDevice != -1){
                            bluetoothDevices.remove(selectDevice);

                            dataDevice.remove(selectDevice);
                            adapterDevice.notifyDataSetChanged();
                            selectDevice = -1;
                        }
                    }
                    break;
            }
        }
    };

    //블루투스 검색 응답 모드 BroadcaseReceiver
    BroadcastReceiver mBluetoothScanmodeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1);
            switch(state){
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                case BluetoothAdapter.SCAN_MODE_NONE:
                    chkFindme.setChecked(false);
                    chkFindme.setEnabled(true);
                    Toast.makeText(bluetoothDeviceList.this,"검색 응답 모드 종료",Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                    Toast.makeText(bluetoothDeviceList.this,"다른 블루투스 기기에서 내 휴대폰을 찾을 수 있습니다.",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //블루투스 검색 버튼 클릭
    public void mOnBluetoothSearch(View v){
        //검색버튼 비활성화
        btnSearch.setEnabled(false);
        //mBluetoothAdapter.isDiscovering() : 블루투스 검색중인지 여부
        //mBluetoothAdapter.cancelDiscovery() : 블루투스 검색 취소

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
        //블루투스 검색 시작
        mBluetoothAdapter.startDiscovery();
    }
    //검색응답 모드 - 블루투스가 외부 블루투스의 요청에 답변하는 슬레이브 상태
    //BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE : 검색응답 모드 활성화 + 페이지 모드 활성화
    //BluetoothAdapter.SCAN_MODE_CONNECTABLE : 검색응답 모드 비활성화 + 페이지 모드 활성화
    //BluetoothAdapter.SCAN_MODE_NONE : 검색응답 모드 비활성화 + 페이지 모드 비활성화
    //검색응답 체크박스 클릭

    public void mOnChkFindme(View v){
        //검색 응답 체크
        if(chkFindme.isChecked()){
            if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                //60초 동안 상대방이 나를 검색할 수 있음
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,60);
                startActivity(intent);
            }
        }
    }
    //이미 페어링 된 목록 가져오기
    public void GetListPairedDevice(){
        Set<BluetoothDevice> pairedDevice = mBluetoothAdapter.getBondedDevices();

        dataPaired.clear();
        if(pairedDevice.size() > 0){
            for(BluetoothDevice device : pairedDevice){
                Map map = new HashMap();
                map.put("name", device.getName());
                map.put("address",device.getAddress());
                dataPaired.add(map);
            }
        }
        //리스트 목록갱신
        adapterPaired.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case BLUETOOTH_REQUEST_CODE:
                //블루투스 활성화 승인
                if(resultCode == Activity.RESULT_OK){
                    GetListPairedDevice();
                }
                //블루투스 활성화 거절
                else{
                    Toast.makeText(this, "블루투스를 활성화해야 합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
        }
    }

    protected void onDestory(){
        unregisterReceiver(mBluetoothStateReceiver);
        unregisterReceiver(mBluetoothSearchReceiver);
        unregisterReceiver(mBluetoothScanmodeReceiver);
        super.onDestroy();

    }
}
