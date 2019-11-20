#include "dht11.h"
#define DHT11PIN 4
#include <Stepper.h>



dht11 DHT11;
const int ledPin = 13;
const int ldrPin = A0;
const int trigPin = 11;
const int echoPin = 12;
const int button = 5;
const int buzzer = 6;
long duration;
int distance;


int intensity() {
  return analogRead(ldrPin);
}

int stubintensity() {
  int ldr = 99;
  if (ldr == 99) {
    ldr = 101;
  } else {
    ldr = 99;
  }
  return ldr;
}

int distance() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);
  // Calculating the distance
  return duration * 0.034 / 2;
}

int stubdistance() {
  return 20;
}

int temp() {
  return (float)DHT11.temperature;
}

int stubtemp() {
  return 20;
}

int hum() {
  return (float)DHT11.humidity;
}

int stubhum() {
  return 20;
}



void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input

  pinMode(ledPin, OUTPUT);//Set the ledPin as Output

  pinMode(ldrPin, INPUT);//Set the ldrPin as Input

  pinMode(button, INPUT);//Set the button as Input

  pinMode(buzzer, OUTPUT);//Set buzzer as Output
}




void loop() {

  //intensity sensor
  int ldrStatus = stubintensity();//read from sensor

  if (ldrStatus <= 100) {//turn on light if intensity is lower than 100

    digitalWrite(ledPin, HIGH);
    Serial.print(ldrStatus);
    Serial.print("\t");
    Serial.print("lightON");
    Serial.print("\t");
    delay(50);

  } else {//turn off if higher than 100

    digitalWrite(ledPin, LOW);

    Serial.print(ldrStatus);
    Serial.print("\t");
    Serial.print("lightOFF");
    Serial.print("\t");
    delay(50);
  }


  //distance sensor
  // Prints the distance on the Serial Monitor
  Serial.print(stubdistance());
  Serial.print("\t");
  delay(50);


  //temp. and hum. sensor
  //print out the humidity
  Serial.print(stubhum(), 2);
  Serial.print("\t");
  //print out the temperature
  Serial.print(stubtemp(), 2);
  Serial.print("\t");
  delay(50);

  //Botton
  //if button be pressed, turn on buzzer
  if (digitalRead(button) == HIGH) {
    Serial.print("SomeoneOutside");
    tone(buzzer, 1000);
  } else { //no one push button, turn off buzzer
    noTone(buzzer);
    Serial.print("NoOneOutside");
  }

  Serial.println();
  delay(100);
  
}
