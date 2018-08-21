#include <MsTimer2.h>
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
bool motor_chk = false;
bool word_chk = false;
boolean led_control = LOW;

const int red_LED = 8;
const int green_LED = 9;

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
     digitalWrite(green_LED, HIGH);
     //rec_chk = true;
  }
  if(rec_data == 'y' || rec_data == 'n' || rec_data == 'e'){
    word_chk = true;
  }
    else{ 
      motor_chk = true;
    }
  
  //단어 정답 체크
  if(word_chk == true){
    if(rec_data == 'y'){
        digitalWrite(red_LED ,LOW);
        digitalWrite(green_LED, HIGH);
        delay(500);    
      }
      else if (rec_data == 'n'){
        digitalWrite(red_LED ,HIGH);
        digitalWrite(green_LED, LOW); 
        delay(500);
      }
      else if(rec_data == 'e') {
        digitalWrite(red_LED ,LOW);
        digitalWrite(green_LED, LOW);
        delay(500);
     word_chk = false;
    }if(rec_data == 'd'){
        dancing();
        MsTimer2::set(500,flash);
        MsTimer2::start();
    }
    else {
        digitalWrite(red_LED ,LOW);
        digitalWrite(green_LED, LOW);
        delay(500);
      }
  }
  
  //모터 방향 구동
  if(motor_chk == true){
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
        motor_chk = false;
    }else{
      motor_stop();
    }
}


void flash(){
      digitalWrite(red_LED ,led_control);
      digitalWrite(green_LED, !led_control);
      led_control = !led_control;
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
  analogWrite(E2, 0);
}

void motor_left(){
  digitalWrite(M1,LOW);
  digitalWrite(M2,HIGH);
  analogWrite(E1, 0);
  analogWrite(E2, motor_speed);
}

void dancing(){
  for(int i = 0 ; i <=255 ; i+=5){
    digitalWrite(M1,HIGH);
    digitalWrite(M2,LOW);
    analogWrite(E1, i);
    analogWrite(E2, i);
}
}

