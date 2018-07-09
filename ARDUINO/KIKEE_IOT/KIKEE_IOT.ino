/* ???뚯뒪???먮??대끂(Eduino)???섑빐??踰덉뿭, ?섏젙, ?묒꽦?섏뿀怨??뚯쑀沅??먰븳 ?먮??대끂??寃껋엯?덈떎. 
 *  ?뚯쑀沅뚯옄???덈씫??諛쏆? ?딄퀬 臾대떒?쇰줈 ?섏젙, ??젣?섏뿬 諛고룷????踰뺤쟻??泥섎쾶??諛쏆쓣 ?섎룄 ?덉뒿?덈떎. 
 *  
 *  ?먮??대끂 援먯쑁??2???꾨몢?대끂 ?ㅻ쭏?몄뭅 援щ룞?덉젣 
 *  - ?ㅻ쭏?명룿 ?댄뵆???댁슜???ㅻ쭏?몄뭅 議곗쥌?섍린 -
 *  
 *  ?ㅻ쭏?명룿??臾대즺 釉붾（?ъ뒪 ?댄뵆???댁슜???ㅻ쭏?몄뭅瑜?議곗쥌?섎뒗 ?뚯뒪?낅땲??
 *  
 */

#include <SoftwareSerial.h>
#include <AFMotor.h>
AF_DCMotor motor_L(3);              // 紐⑦꽣?쒕씪?대쾭 L293D  3: M3???곌껐,  4: M4???곌껐
AF_DCMotor motor_R(4); 

#define BT_RXD A5
#define BT_TXD A4
SoftwareSerial bluetooth(BT_RXD, BT_TXD);       // RX: A5, TX: A4

char rec_data;
bool rec_chk = false;

int i;
int j;

//珥덉쓬?뚯꽱??異쒕젰?(trig)怨??낅젰?(echo) ?ㅼ젙
int trigPin = A0;
int echoPin = A1;

void setup(){
  Serial.begin(9600);              // PC????쒕━???듭떊?띾룄
  bluetooth.begin(9600);            // ?ㅻ쭏?명룿 釉붾（?ъ뒪 ?듭떊?띾룄
  Serial.println("Eduino Smart Car Start!");

  pinMode(echoPin, INPUT);   // echoPin ?낅젰
  pinMode(trigPin, OUTPUT);  // trigPin 異쒕젰

  // turn on motor
  motor_L.setSpeed(250);              // ?쇱そ 紐⑦꽣???띾룄   
  motor_L.run(RELEASE);
  motor_R.setSpeed(250);              // ?ㅻⅨ履?紐⑦꽣???띾룄   
  motor_R.run(RELEASE);
}


void loop(){
  
  if(bluetooth.available()){         // 釉붾（?ъ뒪 紐낅졊 ?섏떊
     rec_data = bluetooth.read();
     Serial.write(rec_data);
     rec_chk = true;
  }  

  if(rec_data == 'g'){  // ?꾩쭊, go
     motor_L.run(FORWARD);  motor_R.run(FORWARD);        
  } 
  else if(rec_data == 'b'){ // ?꾩쭊, back
     motor_L.run(BACKWARD);  motor_R.run(BACKWARD);    
  }
  else if(rec_data == 'l'){ // 醫뚰쉶?? Go Left
   motor_L.run(RELEASE);  motor_R.run(FORWARD);     
  }
  else if(rec_data == 'r'){ // ?고쉶?? Go Right
    motor_L.run(FORWARD);  motor_R.run(RELEASE);                
  }
  else if(rec_data == 'w'){ // ?쒖옄由??뚯쟾, Right Rotation
     motor_L.run(BACKWARD);   motor_R.run(FORWARD);      
  }
  else if(rec_data == 'q'){ // ?쒖옄由??뚯쟾, Left Rotation
      motor_L.run(FORWARD);   motor_R.run(BACKWARD);    
  }
  else if(rec_data == 's'){  } // Stop 

  if(rec_data == 's' ){       // ?뺤?
    if(rec_chk == true){
       for (i=250; i>=0; i=i-20) {
          motor_L.setSpeed(i);  motor_R.setSpeed(i);  
          delay(10);
       }  
       motor_L.run(RELEASE);       motor_R.run(RELEASE);
    }
  }
  else{
    if(rec_chk == true){
      if(rec_data == 'l'){  // Left
        for (i=20; i<200; i=i+10) {
          motor_L.setSpeed(i);  motor_R.setSpeed(i);
          delay(30);
        }
       }
       else if(rec_data == 'r'){       // Right
        for (i=20; i<200; i=i+10) {
          motor_L.setSpeed(i);  motor_R.setSpeed(i);
          delay(30);
        }
       }
       else if(rec_data == 'w' || rec_data == 'q'){ // Rotation Left, Right
        for (i=0; i<250; i=i+20) {
          motor_L.setSpeed(i);  motor_R.setSpeed(i);  
          delay(20);
        }
       }
       else if(rec_data == 'g'){ //Go
        for (i=0; i<250; i=i+20) { 
           motor_L.setSpeed(i);  motor_R.setSpeed(i);  
          delay(10);   
        }
       }
       else{
        for (i=0; i<250; i=i+20) { //  Back
           motor_L.setSpeed(i);  motor_R.setSpeed(i);  
          delay(10);                         
        }
       }
     }
    else{     
          motor_L.setSpeed(250);  motor_R.setSpeed(250);  
    }
  }
  rec_chk = false;
}

