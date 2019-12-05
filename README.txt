*SYSC 3010 Project
*--------------------------------------------------------------------
*Project Name: Sweet Home System
*Project Team: T7
*Project Team Members:
*Lixuan Luo 101019254
*Tongdan Zhu 101057752
*Changlin Liu 101048980
*Xucheng Shi 101020641
*---------------------------------------------------------------------
*Components:
*Arduino code (Folder) --Arduino sensor codes
*Sweet (Folder) --Android app codes
*sweet-test (Folder) --Test cases for testing Android app,sensors and database
*jar (Folder) --.jar files for Junit test
*htdocs (Folder) --PHP codes for display statistic graphs on website 
*-----------------------------------------------------------------------
*Steps:
*	Server Part:
*	1. Connect Raspberry Pi to a monitor
*	2. Open terminal
*	3. Access your database	
*	
*	Hardware Part:
*	1. Connect Raspberry Pi to your Arduino circuits.
*	2. Download "Arduino code" folder and run "combine.ino" in terminal on the server.
*	3. Sensors will send data into database and print out in terminal.
*	4. You can press the doorbell, change distance in front of the ultrasonic sensor  
*	   on your circuit.
*
*	Software Part:
*	1. Run "Sweet" in Android studio
*	2. In app, after you log in, you have privileges to:
*	   control the temperature using the temperature button; 
*	   control the light using the light button; 
*	   confirm your mail using the mailbox button; 
*	   confirm your visitors using the doorbell button.
*----------------------------------------------------------------------------
