# Import Libraries
import pyrebase
import RPi.GPIO as GPIO
import time  

# Firebase Configuration
config = {
  "apiKey": "AIzaSyAIZyOFVeRcLq7uoKvye3ytdb2svilof6w", #----- Change to customize! -----
  "authDomain": "eascok200.firebaseapp.com", #----- Change to customize! -----
  "databaseURL": "https://eascok200.firebaseio.com", #----- Change to customize! -----
  "storageBucket": "eascok200.appspot.com",
   "serviceAccount": "/home/pi/Downloads/eascok200-firebase-adminsdk-13nri-01eaf18045.json"#----- Change to customize! -----
}

firebase = pyrebase.initialize_app(config)

auth = firebase.auth()
#authenticate a user
user = auth.sign_in_with_email_and_password("esc-69-14@cc.ac.mw", "ab123456")

# Firebase Database Intialization
db = firebase.database()

GPIO.setmode(GPIO.BCM)

#GPIO.setup(12, GPIO.OUT)
GPIO.setup(4, GPIO.OUT)# water
GPIO.setup(17, GPIO.OUT)#rice
GPIO.setup(27, GPIO.OUT)#oil
GPIO.setup(12, GPIO.OUT)#cooker
portions = 0
wait = 0
oilDelay = 0
waterDelay = 0
riceDelay = 0

#reads portions and sets delay times
def readAmounts():
  global portions, wait, oilDelay, waterDelay, riceDelay
  if portions == 1:
    oilDelay = 1
    riceDelay = 4#60
    waterDelay = 4#60
  elif portions == 2:
    oilDelay = 1.5
    riceDelay = 4#90
    waterDelay = 4#90
  elif portions == 3:
    oilDelay = 1.5
    riceDelay = 4#120
    waterDelay = 4#120

#starts cooking

def cook():
  global portions, wait, oilDelay, waterDelay, riceDelay
  GPIO.output(17,True)
  db.update({"feedback":"cooking is in progress"},user['idToken'])
  print ("cooking")
  time.sleep(5)#2400
  GPIO.output(17,False)
  print ("done...")    
  

#puts ingredients
def pourIngredients():
  global portions, wait, oilDelay, waterDelay, riceDelay  
  
    #puts rice
  time.sleep(1)
  GPIO.output(27,True)
  print ("putting rice")
  time.sleep(riceDelay)
  GPIO.output(27,False)  
  
  #puts water
  GPIO.output(4,True)
  print ("putting water")
  time.sleep(waterDelay)
  GPIO.output(4,False)
  
  #puts oil
  time.sleep(1)
  GPIO.output(4,True)
  print ("putting oil")
  time.sleep(oilDelay)
  GPIO.output(4,False)  

while(True):   
    ses = db.child("session").get(user['idToken']).val()
    print("waiting...")    
    time.sleep(0.5)    
    
    if (ses==1):
        
        tm = db.child("time").get(user['idToken']).val()
        port = db.child("portion").get(user['idToken']).val()
        portions = port
        wait = 1
        db.update({"feedback":"cooking will start soon"},user['idToken'])
        readAmounts()
        time.sleep(wait)
        pourIngredients()
        cook()
        db.update({"session":0},user['idToken'])
        db.update({"feedback":"your rice is ready..."},user['idToken'])

GPIO.cleanup()

