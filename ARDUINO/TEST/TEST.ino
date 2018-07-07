godconst int LED = 13;
const int LED2 = 12;
const int SPEAKER=9; 
const int BUTTON1=2; 
const int BUTTON2=3;
boolean lastButton = LOW;
boolean currentButton = LOW;
boolean status1 = false;
boolean status2 = false;
boolean led1On = LOW;
boolean led2On = HIGH;

int notes[]={
1047,1047,1047,1047,1047,1047,1047,1047,1047,1047
  ,1047,1047,1047,1047,1047,1047,1047,1047,1047,1047
};
int fastTimes[]= {
200,200,200,200,200,200,200,200,200,200,200
  ,200,200,200,200,200,200,200,200,200
};

int slowTimes[]= {
500,500,500,500,500,500,500,500,500,500,500
  ,500,500,500,500,500,500,500,500,500
};

boolean debounce(boolean last)
{
 boolean current=digitalRead(BUTTON1); //현재 버튼 상태를 확인
 if(last!=current) //이전 버튼 상태와 현재 버튼 상태가 다름
 {
 delay(5); //5ms 동안 기다림
 current=digitalRead(BUTTON1); //current에 현재 버튼 상태를 저장
 }
 return current; //current 변수값을 반환
}

boolean debounce2(boolean last)
{
 boolean current=digitalRead(BUTTON2); //현재 버튼 상태를 확인
 if(last!=current) //이전 버튼 상태와 현재 버튼 상태가 다름
 {
 delay(5); //5ms 동안 기다림
 current=digitalRead(BUTTON2); //current에 현재 버튼 상태를 저장
 }
 return current; //current 변수값을 반환
}

void setup()
{
  pinMode(SPEAKER,OUTPUT);
  pinMode(LED,OUTPUT);
  pinMode(LED2,OUTPUT);
  Serial.begin(9600);
}

void loop()
{  
 currentButton=debounce(lastButton); //디바운싱 처리된 버튼값을 읽음
 if(lastButton==LOW && currentButton==HIGH) //버튼을 누름
 {
  status1 = !status1;
  Serial.println(status1);
 }
 lastButton == currentButton;

  if(status1 == true) {
   for(int i = 0; i<20; i++) {
          tone(SPEAKER, notes[i], fastTimes[i]);
//          digitalWrite(LED,HIGH);
//          digitalWrite(LED2,LOW);
//            delay(200);
//          digitalWrite(LED,LOW);
//           digitalWrite(LED2, HIGH);
//            delay(200);
          digitalWrite(LED,led1On);
          digitalWrite(LED2,led2On);
           led1On =! led1On;
          led2On =! led2On;
          delay(1000);
             currentButton=debounce(lastButton); //디바운싱 처리된 버튼값을 읽음
 if(lastButton==LOW && currentButton==HIGH) //버튼을 누름
 {
  status1 = !status1;
  Serial.println(status1);
 }
 lastButton == currentButton;
  }
}

// currentButton=debounce2(lastButton); //디바운싱 처리된 버튼값을 읽음
// if(lastButton==LOW && currentButton==HIGH) //버튼을 누름
// {
//        for(int i = 0; i<10; i++)
//        {
//        tone(SPEAKER, notes[i], slowTimes[i]);
//          
//        digitalWrite(LED,HIGH);
//          delay(500);
//        digitalWrite(LED,LOW);
//          delay(500);
//          
//        }
//       
//    }
//  
//  noTone(SPEAKER);

  }
