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

int temp() {
  return (float)DHT11.temperature;
}

int hum() {
  return (float)DHT11.humidity;
}




void setup() {

  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input

  pinMode(ledPin, OUTPUT);

  pinMode(ldrPin, INPUT);

  pinMode(button, INPUT);

  pinMode(buzzer, OUTPUT);

  stepper.setSpeed(200);
}



void loop() {

  //light
  for (int i = 0; i < 2000; i++) {
    int ldrStatus = intensity();
    if (ldrStatus == NULL) {
      Serial.print("Intensity sensor is not working!!!");
      Serial.print("   ");
    }
  }


  //distance
  for (int i = 0; i < 2000; i++) {
    distance = distance();
    // Prints the distance on the Serial Monitor
    if (distance == NULL) {
      Serial.print("Distance sensor is not working!!!");
      Serial.print("   ");
    }
  }


  //temp
  for (int i = 0; i < 2000; i++) {
    if (temp() == NULL || hum() == NULL) {
      Serial.print("DHT11 is not working!!!");
      Serial.print("   ");
    }
  }

  delay(50);

}
