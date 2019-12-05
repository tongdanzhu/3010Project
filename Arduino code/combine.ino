#include "dht11.h"
#define DHT11PIN 4
#include <Stepper.h>




dht11 DHT11;
const int ledPin = 2;
const int ldrPin = A0;
const int trigPin = 8;
const int echoPin = 9;
const int button = 7;
const int buzzer = 6;
const int fanPin = 13;
long duration;
int distance;
String ledopen = "op";
String ledclose = "cl";
String a;
int t;
String an;
int isclose = 1;
int op;

int intensity() {
  return analogRead(ldrPin);
}

int distances() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);
  // Calculating the distance
  return duration * 0.0343/ 2;
}

int temp() {
  DHT11.read(DHT11PIN);
  int tem = (float)DHT11.temperature;
  return tem;
}

int hum() {
  DHT11.read(DHT11PIN);
  int hum = (float)DHT11.humidity;
  return hum;
}







void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input

  pinMode(ledPin, OUTPUT);//Set the ledPin as Output

  pinMode(ldrPin, INPUT);//Set the ldrPin as Input

  pinMode(button, INPUT);//Set the button as Input

  pinMode(fanPin, OUTPUT);//Set buzzer as Output

  pinMode(buzzer, OUTPUT);
}





void loop() {

  //intensity sensor
  int ldrStatus = intensity();//read from sensor


  Serial.print(ldrStatus);
  Serial.print("\t");
  Serial.print(1);
  Serial.print("\t");
  delay(50);


  //distance sensor
  // Prints the distance on the Serial Monitor
  distance = distances();
  if (distance <5){
    Serial.print(1);
  } else {
    Serial.print(0);
  }
  Serial.print("\t");
  delay(50);


  //temp. and hum. sensor
  //print out the humidity
  Serial.print(distance);
  Serial.print("\t");
  //print out the temperature
  t = temp();
  Serial.print(t);
  Serial.print("\t");

  delay(50);

  //Botton
  //if button be pressed, turn on buzzer
  if (digitalRead(button) == HIGH) {
    Serial.print(1);
    tone(buzzer, 1000);
    delay(50);
  } else { //no one push button, turn off buzzer
    noTone(buzzer);
    Serial.print(0);
    delay(50);
  }


  Serial.println();
  delay(1000);
}
