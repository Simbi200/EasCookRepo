
import time
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setup(3, GPIO.OUT)

ricePourTime = None
waterPourTime = None
oilPourTime = None
a = None

"""Time to pour rice
"""
def setRicePourTime():
  global ricePourTime, waterPourTime, oilPourTime, a
  if a == 1:
    ricePourTime = 30
  if a == 1:
    ricePourTime = 45
  if a == 1:
    ricePourTime = 60
  return ricePourTime

"""Time to pour water
"""
def setWaterPourTime():
  global ricePourTime, waterPourTime, oilPourTime, a
  if a == 1:
    waterPourTime = 30
  if a == 1:
    waterPourTime = 45
  if a == 1:
    waterPourTime = 60
  return waterPourTime

"""Time to pour oil
"""
def setOilPourTime():
  global ricePourTime, waterPourTime, oilPourTime, a
  if a == 1:
    oilPourTime = 30
  if a == 1:
    oilPourTime = 45
  if a == 1:
    oilPourTime = 60
  return oilPourTime

"""pours oil
"""
def pourOil():
  global ricePourTime, waterPourTime, oilPourTime, a
  GPIO.output(3,True)
  time.sleep(1)
  GPIO.output(3,False)
  time.sleep(oilPourTime)
  GPIO.output(3,True)
  time.sleep(1)
  GPIO.output(3,False)

"""pours water
"""
def pourWater():
  global ricePourTime, waterPourTime, oilPourTime, a
  GPIO.output(3,True)
  time.sleep(1)
  GPIO.output(3,False)
  time.sleep(waterPourTime)
  GPIO.output(3,True)
  time.sleep(1)
  GPIO.output(3,False)

"""pours rice
"""
def pourRice():
  global ricePourTime, waterPourTime, oilPourTime, a
  GPIO.output(3,True)
  time.sleep(1)
  GPIO.output(3,False)
  time.sleep(ricePourTime)
  GPIO.output(3,True)
  time.sleep(1)
  GPIO.output(3,False)

"""Turns cooker on and off
"""
def cookerSwitch():
  global ricePourTime, waterPourTime, oilPourTime, a
  GPIO.output(3,True)
  time.sleep(2400)
  GPIO.output(3,False)

"""Cooks rice
"""
def cook():
  global ricePourTime, waterPourTime, oilPourTime, a
  pourRice()
  pourWater()
  pourOil()
  cookerSwitch()
  GPIO.cleanup()
