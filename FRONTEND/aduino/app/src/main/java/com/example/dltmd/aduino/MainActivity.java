package com.example.dltmd.aduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//java 에서는 배열보다 util 패키지의 List,Set,Map 인터페이스를 주로 사용
// 배열은 같은 타입만 저장 가능하나, 아래 인터페이스는 서로 다른 타입을 같은 List 안에 저장할 수 있다.
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.dltmd.aduino.R;

public class MainActivity extends AppCompatActivity {

    //사용자 정의 함수, 블루투스 활성 상태의 변경 결과를 APP 으로 알려줄 때 식별자로 사용됨(0보다 커야함)
    static final int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;

    // 폰의 블루투스 모듈을 사용하기 위한 오브젝트
    BluetoothAdapter mBluetoothAdapter;

    /**
     BluetoothDevice 로 기기의 장치정보를 알아낼 수 있는 자세한 메소드 및 상태값을 알아낼 수 있다.
     연결하고자 하는 다른 블루투스 기기의 이름, 주소, 연결 상태 등의 정보를 조회할 수 있는 클래스.
     현재 기기가 아닌 다른 블루투스 기기와의 연결 및 정보를 알아낼 때 사용.
     */
    BluetoothDevice mRemoteDevice;

    //스마트 폰과 페어링 된 디바이스 간 통신 체널에 대응하는 BluetoothSocket
    BluetoothSocket mSocket = null;

    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter = '\n';

    Thread mWorkerThread = null;
    byte[] readBuffer;
    int readBufferPosition;

    EditText mEditReceive, mEditSend;
    Button mButtonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditReceive = (EditText)findViewById(R.id.receiveString);
        mEditSend = (EditText)findViewById(R.id.sendString);
        mButtonSend = (Button)findViewById(R.id.sendButton);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //문자열 전송하는 함수(쓰레드 사용x)
                sendData(mEditSend.getText().toString());
                mEditSend.setText("");
            }
        });
        //블루투스 활성화 시키는 메소드
        checkBluetooth();
    }
    //블루투스 장치의 이름이 주어졌을 때 해당 블루투스 장치 객체를 페이렁 된 장치 목록에서 찾아내는 코드
    BluetoothDevice getDeviceFromBondedList(String name){
        //BluetoothDevice : 페어링 된 기기 목록을 얻어옴
        BluetoothDevice selectedDevice = null;
        //getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 set 형식이며,
        //set 형식에서는 n번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾음
        for(BluetoothDevice device : mDevices){
            //getName() :단말기의 Bluetooth Adapter 이름을 반환
            if(name.equals(device.getName())){
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }
    //문자열 전송하는 함수(쓰레드 사용 X)
    void sendData(String msg){
        msg += mStrDelimiter; // 문자열 종료표시(\n)
        try{
            //getBytes() :String을 byte로 변환
            //OutputStream.write :데이터를 쓸때는 write(byte[])메소드를 사용함. byte[] 안에 있는 데이터를 한번에 기록해줌
            mOutputStream.write(msg.getBytes());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"데이터 전송중 오류 발생", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //connectToSelectedDevice() : 원격 장치와 연결하는 과정을 나타냄
    //실제 데이터 송 수신을 위해서는 원하는 소켓으로부터 입 출력 스트림을 얻고 입출력 스트림을 이용해 이루어짐
    void connectToSelectDevice(String selectDeviceName){
        mRemoteDevice = getDeviceFromBondedList(selectDeviceName);

        //java.util.UUID.fromString : 자바에서 중복되지 않는 unique 키 생성
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try{
            //소켓 생성, RFCOMM 체널을 통한 연결
            //createRfcommSocketToServiceRecord(uuid): 이 함수를 이용해 원격 블루투스 장치와 통신할 수 있는 소켓 생성
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는 BluetoothSocket 오브젝트를 리턴함
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            //데이터 송 수신을 위한 스트림 얻기
            //BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
            //1. 데이터를 보내기 위한 OutputStream
            //2. 데이터를 받기 위한 InputStream

            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
            //데이터 수신 준비
            beginListenForData();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"블루투스 연결 중 오류가 발생했습니다.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    //데이터 수신(쓰레드 사용 수신된 메시지를 계속 검사함
    void beginListenForData(){
        final Handler handler = new Handler();

        readBufferPosition = 0;  // 버퍼 내 수신 문자 저장 위치
        readBuffer = new byte[1024];  //수신 버퍼

        //문자열 수신 스레드
        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //interrupt()메소드를 이용 스레드를 종료시키는 예제이다.
                //interrupt()메소드는 하던일을 멈추는 메서드이다.
                //isInterrupted()메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        //InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽을 수 있는 문자열 개수를 반환
                        int byteAvailable = mInputStream.available();  //수신 데이터 확인
                        if(byteAvailable > 0){                        //데이터가 수신된 경우
                            byte[] packetByte = new byte[byteAvailable];
                            //read(buf[]) : 입력스트림에서 buf[]크기만큼 읽어서 저장. 없을 경우 -1 리턴
                            mInputStream.read(packetByte);
                            for(int i = 0; i <byteAvailable; i++){
                                byte b = packetByte[i];
                                if(b == mCharDelimiter){
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    //System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    // readBuffer 배열을 처음부터 끝까지 encodeBytes 배열로 복사
                                    System.arraycopy(readBuffer,0,encodedBytes,0,encodedBytes.length);

                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        //수신된 문자열 데이터에 대한 처리
                                        @Override
                                        public void run() {
                                            //mStrDelimiter = '\n';
                                            mEditReceive.setText(mEditReceive.getText().toString() + data+ mStrDelimiter);

                                        }
                                    });

                                }
                                else{
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }catch (Exception e){   //데이터 수신중 오류발생
                        Toast.makeText(getApplicationContext(),"데이터 수신 중 오류가 발생했습니다.",Toast.LENGTH_LONG).show();
                        finish();  //app 종료
                    }
                }
            }
        })
    }
}
