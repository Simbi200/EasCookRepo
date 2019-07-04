#!/usr/bin/python3
from tkinter import *
import os, sys
import tkinter.font as font
import pyrebase
import RPi.GPIO as GPIO
import threading
import time

portions = 1   # number of rice portions declaration
tym = 0     # declaring countdown time to be used before cooking starts
cancel = False   # variable hundles cancel button from console. this is set to true if user cancels from console
datSet = False   # set to true when user set time and rice amount parameters from console
textMsgC = "0" #displayed message variable
waterPin = 4 #GPIO for water pump
ricePin = 17 #GPIO for rice valve
oilPin = 27  #GPIO for oil pump
cookerPin = 22 #GPIO for cooker


oilDelay = 1 # time it takes for oil pump to pump specific amount of oil 
waterDelay = 1  # time it takes for water pump to pump specific amount of water
riceDelay = 1   # time it takes for rice valve to open for specific amount of rice to pass



""" This class handles Tkinter GUI thread so that it runs while the rest of the code continue
"""
class App(threading.Thread):
    global textMsgC, textMsg, datSet, cancel,textMsgC, stnBy, portions, stage, datSet, hour, minute


    stnBy = 0    # standBy status variable
    #portions = 1
    stage = 0 # keeps track of stage in console menu
    hour = 12  # to be set 
    minute = 30 # to be set

    def __init__(self): #constructor for this class
        global textMsg, datSet, cancel,textMsgC, stnBy, portions, stage, datSet, hour, minute
        threading.Thread.__init__(self)
        stnBy = 0
        portions = 1
        stage = 0
        hour = 12
        minute = 30
        datSet = False  
        cancel = False
      
        
        self.start()

    def callback(self):
        self.root.quit()



    def m_Start(self): #called when start button is pressed
        
        global textMsg, stnBy, portions, stage, datSet, tym
        if stnBy == 0 and stage == 0:
            self.writeTxt("select rice portions -/+")
            stage = 1
            stnBy = 1
        elif stnBy == 1 and stage == 3:
            self.writeTxt("cooking will start shortly")
            tym = ((hour*360) + (minute*60)) 
            datSet = True
    
    def writeTxt(self, t):
        textMsg.set(t)        


    def m_cancel(self):
        global textMsg, stnBy, portions,stage,cancel
        if stnBy !=0:
            stnBy = 0
            stage = 0
            cancel = True
            self.writeTxt("on standby")


    def okButtm(self):
        global textMsg, stnBy, stage
        if stnBy == 1 and stage == 1:
            self.writeTxt("Set timer: hours first")
            stage = 2

        elif stnBy == 1 and stage == 2:
            self.writeTxt("set minutes")
            stage = 3

        elif stnBy == 1 and stage == 3:
            self.writeTxt("Start to start cooking")
            stage = 3


    def upButtm(self):
        global textMsg, stnBy, portions, stage, hour, minute
        if stnBy == 1 and stage == 1 and portions <= 3:
            if portions != 3:
                portions = portions + 1
            self.writeTxt("Portions: "+ str(portions))

        elif stnBy == 1 and stage == 2 and hour <= 24:
            if hour != 24:
                hour = hour + 1
            self.writeTxt("Hours: "+ str(hour))

        elif stnBy == 1 and stage == 3 and minute <= 59:
            if minute != 59:
                minute = minute + 1
            self.writeTxt("minutes: "+ str(minute))


    def downButtm(self):
        global textMsg, stnBy, portions, hour, minute
        if stnBy == 1 and stage == 1 and portions >= 1:
            if portions != 1:
                portions = portions - 1
            self.writeTxt("Portions: "+ str(portions))

        elif stnBy == 1 and stage == 2 and hour >= 0:
            if hour != 0:
                hour = hour - 1
            self.writeTxt("Hour: "+ str(hour))

        elif stnBy == 1 and stage == 3 and minute >= 0:
            if minute != 0:
                minute = minute - 1
            self.writeTxt("minutes: "+ str(minute))


    def run(self):
        global textMsg, textMsgC
        self.root = Tk()
        self.root.protocol("WM_DELETE_WINDOW", self.callback)


        w = self.root.winfo_screenwidth()
        h = self.root.winfo_screenheight()
        h1 = int(h/55)
        h11 = int(h/15)
        h22 = int(h/15)
        dh = int(h*0.1)

        hel36 = font.Font(family = 'Helvetica', size = h11, weight = 'bold')
        hel362 = font.Font(family = 'Helvetica', size = h22, weight = 'bold')


        displayFrame = Frame(self.root, width=w, height = h1)
        displayFrame.pack()

        buttonsFrame = Frame(self.root)
        buttonsFrame.pack()

        textMsg = StringVar()
        textMsg.set("On standby")

        textDisplay = Label(displayFrame,
                            bd = dh,
                            relief = "solid",
                            bg = "white",
                            fg="black",
                            font = hel36,
                            width = w,
                            textvariable = textMsg)
        textDisplay.pack()


        blankText1 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText2 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText3 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText4 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText5 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText6 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText7 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText8 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        blankText9 = Label(buttonsFrame, bd = h1,
                           relief = "solid",
                           bg = "black",
                           font = hel36)

        menuButt = Button(buttonsFrame, text="Start", bd= 5, padx=20, pady = 10,  bg='#d06200', font = hel362, command=self.m_Start)
        cancelButt = Button(buttonsFrame, text="Cancel ",  bd= 5,  bg='#d06200', font = hel362, command=self.m_cancel)
        upButt = Button(buttonsFrame, text="+", bg='#d06200',  bd= 5, font = hel362, command=self.upButtm)
        downButt = Button(buttonsFrame, text=" - ", bg='#d06200',  bd= 5, font = hel362, command=self.downButtm)
        okButt = Button(buttonsFrame, text="Enter", bg='#d06200',  bd= 5, font = hel362, command=self.okButtm)

        blankText1.grid( row = 0 ,column = 1,rowspan = 2, columnspan = 4, sticky='EWNS')
        blankText2.grid( row = 0 ,column =5, columnspan = 1, sticky='EWNS')
        blankText3.grid( row = 1 ,column = 0, columnspan = 4, sticky='EWNS')
        blankText4.grid( row = 1 ,column = 4, columnspan = 3, sticky='EWNS')
        blankText5.grid( row = 2 ,column = 2, columnspan = 1, sticky='EWNS')
        blankText6.grid( row = 2 ,column = 4, columnspan = 1, sticky='EWNS')
        blankText7.grid( row = 2 ,column = 0, columnspan = 1, sticky='EWNS')
        blankText8.grid( row = 3 ,column = 0, columnspan = 7, sticky='EWNS')
        blankText9.grid( row = 2 ,column = 6, columnspan = 1, sticky='EWNS')

        menuButt.grid(row=0, column=0, columnspan=2, sticky='EWNS')
        cancelButt.grid(row=0, column=5, columnspan=2, sticky='EWNS')
        downButt.grid(row=2, column=1, columnspan=1, sticky='EWNS')
        okButt.grid(row=2, column=3, columnspan=1, sticky='EWNS')
        upButt.grid(row=2, column=5, columnspan=1, sticky='EWNS')

        self.root.overrideredirect(1)
        self.root.configure(background='black')
        self.root.geometry("%dx%d+0+0" % (w, h))      
     

        self.root.mainloop()


class Ccook(threading.Thread):

    global  datSet, textMsgC, portions, wait, oilDelay, waterDelay, riceDelay, waterPin, ricePin, oilPin, cookerPin

    def __init__(self):
        
        
        datSet = False
        threading.Thread.__init__(self)
        # Firebase Configuration
        config = {
          "apiKey": "AIzaSyAIZyOFVeRcLq7uoKvye3ytdb2svilof6w", #----- Change to customize! -----
          "authDomain": "eascok200.firebaseapp.com", #----- Change to customize! -----
          "databaseURL": "https://eascok200.firebaseio.com", #----- Change to customize! -----
          "storageBucket": "eascok200.appspot.com",
           "serviceAccount": "/home/pi/Downloads/eascok200-firebase-adminsdk-13nri-e04acee1f8.json"#----- Change to customize! -----
        }

        firebase = pyrebase.initialize_app(config)

        auth = firebase.auth()
        #authenticate a user
        self.user = auth.sign_in_with_email_and_password("esc-69-14@cc.ac.mw", "ab123456")

        # Firebase Database Intialization
        self.db = firebase.database()
        GPIO.setmode(GPIO.BCM)


        GPIO.setup(waterPin, GPIO.OUT)# water
        GPIO.setup(ricePin, GPIO.OUT)#rice
        GPIO.setup(oilPin, GPIO.OUT)#oil
        GPIO.setup(cookerPin, GPIO.OUT)#cooker
        



    #starts cooking
    def cook(self):
        global  datSet, portions, wait, oilDelay, waterDelay, riceDelay, waterPin, ricePin, oilPin, cookerPin
        GPIO.output(cookerPin,True)
        self.db.update({"feedback":"cooking is in progress"},self.user['idToken'])
        print ("cooking")
        time.sleep(5)#2400
        GPIO.output(cookerPin,False)
        print ("done...")


    #puts ingredients
    def pourIngredients(self):
        global portions, portions, oilDelay, waterDelay, ricePin, riceDelay

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

        #puts rice
        time.sleep(1)
        GPIO.output(ricePin,True)
        print ("putting rice")
        time.sleep(riceDelay)
        GPIO.output(ricePin,False)

        #puts water
        GPIO.output(waterPin,True)
        print ("putting water")
        time.sleep(waterDelay)
        GPIO.output(waterPin,False)

        #puts oil
        time.sleep(1)
        GPIO.output(oilPin,True)
        print ("putting oil")
        time.sleep(oilDelay)
        GPIO.output(oilPin,False)

    def listeCommands(self):
        global datSet, textMsgC
        datSet = False
        while(True):
            ses = self.db.child("session").get(self.user['idToken']).val()
            gui.writeTxt("on standby")            
            time.sleep(5)
            if ses==1 or datSet:
                self.timerT()

    def timerT(self):
        global cancel, datSet
        if datSet:
            tm = tym
        else:
            tm = self.db.child("time").get(self.user['idToken']).val()
            portions = self.db.child("portion").get(self.user['idToken']).val()

        timeout = time.time() + tm  # minutes from now
        textMsgC = "timer..."
        while (time.time() < timeout):
            gui.writeTxt("count down started")
            time.sleep(5)
            c = self.db.child("interapt").get(self.user['idToken']).val()
            if cancel or c == "cancel":
                self.db.update({"session":0}, self.user['idToken'])
                self.db.update({"interapt":"OK"}, self.user['idToken'])
                cancel = False
                self.listeCommands()
        
        datSet = False
        gui.writeTxt("cooking to start in a moment")
        self.pourIngredients()
        gui.writeTxt("cooking started")
        self.cook()
        gui.writeTxt("Done")
        time.sleep(2)
        self.db.update({"session":0},self.user['idToken'])
        self.db.update({"feedback":"your rice is ready..."}, self.user['idToken'])        



    
gui = App()
#time.sleep(2)
eCook = Ccook()
#gui.run()
eCook.listeCommands()