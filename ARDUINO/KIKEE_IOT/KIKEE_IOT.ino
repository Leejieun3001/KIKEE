#include <SoftwareSerial.h>

//모터 연결
int E1 = 10;      // 1번(A) 모터 Enable
int E2 = 11;      // 2번(B) 모터 Enable
int M1 = 12;      // 1번(A) 모터 PWM  
int M2 = 13;      // 2번(B) 모터 PWM
#define motor_speed 255

#define BT_RXD A5
#define BT_TXD A4
SoftwareSerial bluetooth(BT_RXD, BT_TXD);// RX: A5, TX: A4

char rec_data;
bool rec_chk = false;
bool dance_chk = false;

#define red_LED A0
//const int red_LED = A3;
const int green_LED = 7;

void setup(){
  Serial.begin(9600);             
  bluetooth.begin(9600);
           
  Serial.println("Eduino Smart Car Start!");
  Serial.println("");
  
  // turn on motor
  pinMode(M1, OUTPUT);      // 출력핀 설정진
  pinMode(M2, OUTPUT);

  //정답 유무 
  pinMode(red_LED ,OUTPUT);
  pinMode(green_LED ,OUTPUT);
}

void loop(){
    
    if(Serial.available()){
    bluetooth.write(Serial.read());
    }
    
  if(bluetooth.available()){         //블루투스 명령 수신
     //Serial.write(bluetooth.read());
     rec_data = bluetooth.read();
     Serial.write(rec_data); 
     //rec_chk = true;
  }
  
  //단어 정답 체크
 /*if(rec_data == 'y'){
    dance_chk = ture;
    analogWrite(red_LED ,255);
    //digitalWrite(green_LED, HIGH);
    //dancing();
  }
  else if (rec_data == 'n'){
    digitalWrite(red_LED ,HIGH);
    digitalWrite(green_LED, HIGH); 
  }
  else if ( rec_data = 'e'){
    digitalWrite(red_LED ,LOW);
    digitalWrite(green_LED, LOW); 
  }*/
  
  //모터 방향 구동
  if(rec_data == 'g'){  //go
    motor_forwards();
  } 
  else if(rec_data == 'b'){ //back
    motor_backwards();
  }
  else if(rec_data == 'l'){ //Go Left
    motor_left();
  }
  else if(rec_data == 'r'){ //Go Right        
    motor_right();               
  }
  else if(rec_data == 's'){ 
    motor_stop();
    } // Stop 
  else if(rec_data == 'y'){
    analogWrite(red_LED, 255);
  }
}

void motor_stop(){
  digitalWrite(M1,HIGH);
  digitalWrite(M2,HIGH);
  analogWrite(E1, 0);
  analogWrite(E2, 0);
}

void motor_forwards(){
  digitalWrite(M1,HIGH);
  digitalWrite(M2,HIGH);
  analogWrite(E1, motor_speed);
  analogWrite(E2, motor_speed);
}

void motor_backwards(){
  digitalWrite(M1,LOW);
  digitalWrite(M2,LOW);
  analogWrite(E1, motor_speed);
  analogWrite(E2, motor_speed);
}

void motor_right(){
  digitalWrite(M1,HIGH);
  digitalWrite(M2,LOW);
  analogWrite(E1, motor_speed);
  analogWrite(E2, motor_speed);
}

void motor_left(){
  digitalWrite(M1,LOW);
  digitalWrite(M2,HIGH);
  analogWrite(E1, motor_speed);
  analogWrite(E2, motor_speed);
}

/*void dancing(){
  for(i = 0 ; i <=255 ; i+=10){
     digitalWrite(M1,HIGH);
    digitalWrite(M2,LOW);
    analogWrite(E1, i);
    analogWrite(E2, i);
    delay(30);
    }
   delay(1000);
  for(i = 255 ; i >= 0 ; i-=10){
     digitalWrite(M1,LOW);
    digitalWrite(M2,HIGH);
    analogWrite(E1, i);
    analogWrite(E2, i);
    delay(30);
  }
  delay(1000);
  motor_stop();
  dance_chk = false;
}*/

