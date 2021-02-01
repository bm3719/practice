# Use ADC microcontroller with 3x potentiometers to control RGB color.
import RPi.GPIO as GPIO
import time
import math
from ADCDevice import *

adc = ADCDevice()
zPin = 12

def setup():
    global adc
    if (adc.detectI2C(0x48)): # If pcf8591.
        adc = PCF8591()
    elif (adc.detectI2C(0x4b)): # If ads7830.
        adc = ADS7830()
    else:
        print("No correct I2C address found, \n"
               "Use command 'i2cdetect -y 1' to check I2C address")
        exit(-1)
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(zPin, GPIO.IN, GPIO.PUD_UP)

def loop():
    while True:
        z = GPIO.input(zPin)
        x = adc.analogRead(1)
        y = adc.analogRead(0)

        print ("X: " + str(x) + ", y: " + str(y) + ", z: " + str(z))
        time.sleep(0.1)

def destroy():
    adc.close()
    GPIO.cleanup() # Release all GPIO.

print('Program is starting... \n')
try:
    setup()
    loop()
except KeyboardInterrupt:
    destroy()
