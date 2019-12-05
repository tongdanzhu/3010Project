import serial

import time

import MySQLdb as mdb

import sys

import RPi.GPIO as GPIO

channel = 23
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(24,GPIO.OUT)
GPIO.setup(25,GPIO.OUT)
GPIO.setup(12,GPIO.OUT)
GPIO.setup(channel,GPIO.OUT)
close = True

nomail = True


arduino = serial.Serial("/dev/ttyACM0")

arduino.baudrate=9600

time.sleep(2)

con = mdb.connect('localhost','root','password','sysc');

while True:

 data = arduino.readline()

 time.sleep(2)

 data = arduino.readline()

 pieces =data.split("\t")

 intensity = pieces[0]

 lightstate = pieces[1]

 mail = pieces[2]

 temp = pieces[4]

 bell = pieces[5]
 print(data)


 with con:

   cur =con.cursor()
   cur.execute("INSERT INTO light(houseID, currTime, currDate, intensity) VALUES(1, CURTIME(), CURDATE(), '%s')"%(intensity))
   cur.execute("INSERT INTO temperature(houseID, currTemp, currDate, currTime) VALUES(1, '%s', CURDATE(), CURTIME())"%(temp))
   cur.execute("SELECT threshold FROM temperatureControl")
   t = cur.fetchone()
   new_dict = dict(zip([x[0] for x in cur.description],t))
   print(t[0])
   print(temp)
   if int(temp)>int(t[0]):
      GPIO.output(channel, False)
      cur.execute("UPDATE temperatureControl SET fanState = 1  WHERE houseID = 1")
   if int(temp)<int(t[0]):
      GPIO.output(channel, True)
      cur.execute("UPDATE temperatureControl SET fanState = 0  WHERE houseID = 1") 
   cur.execute("SELECT mailboxstate FROM user")
   b = cur.fetchone()
   b_dict = dict(zip([x[0] for x in cur.description],b))
   if b[0]==0:
     if int(mail)>0:
       cur.execute("UPDATE user SET mailboxstate = 1  WHERE houseID = 1")
   if int(bell)==1:
      cur.execute("INSERT INTO visitor(houseID, currTime, currDate, confirm) VALUES(1, CURTIME(), CURDATE(), 0)")
   cur.execute("SELECT manualControl FROM lightControl")
   m = cur.fetchone()
   m_dict = dict(zip([x[0] for x in cur.description],m))
   cur.execute("SELECT switches FROM lightControl")
   s = cur.fetchone()
   s_dict = dict(zip([x[0] for x in cur.description],s))
   g = str(m[0])+str(s[0])
   if m[0] == 1:
     if s[0] == 1:
       GPIO.output(24,True)
       GPIO.output(25,True)
       GPIO.output(12,True)
     else:
       GPIO.output(24,False)
       GPIO.output(25,False)
       GPIO.output(12,False)
   else:
     if int(intensity)>=100:
       GPIO.output(24,False)
       GPIO.output(25,False)
       GPIO.output(12,False)
     if int(intensity)<100:
       GPIO.output(24,True)
       GPIO.output(25,True)
       GPIO.output(12,True)
   print(g)
  
   
