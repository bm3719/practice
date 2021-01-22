# Use ADC microcontroller with 3x potentiometers to control RGB color.
import RPi.GPIO as GPIO
import time
from ADCDevice import *

ledRedPin = 13
ledGreenPin = 15
ledBluePin = 11
adc = ADCDevice()

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
    global r, g, b
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(ledRedPin, GPIO.OUT)
    GPIO.setup(ledGreenPin, GPIO.OUT)
    GPIO.setup(ledBluePin, GPIO.OUT)
    r = GPIO.PWM(ledRedPin, 1000)
    r.start(0)
    g = GPIO.PWM(ledGreenPin, 1000)
    g.start(0)
    b = GPIO.PWM(ledBluePin, 1000)
    b.start(0)

def loop():
    while True:
        rVal = adc.analogRead(0)
        gVal = adc.analogRead(1)
        bVal = adc.analogRead(2)
        r.ChangeDutyCycle(rVal * 100 / 255)
        g.ChangeDutyCycle(gVal * 100 / 255)
        b.ChangeDutyCycle(bVal * 100 / 255)
        print ("Red: " + str(rVal) + ", Green: " + str(gVal) + ", Blue: " + str(bVal))
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
