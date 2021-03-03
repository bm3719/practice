# Use ADC microcontroller with potentiometer to control DC motor.
import RPi.GPIO as GPIO
import time
from ADCDevice import *

adc = ADCDevice()
motorPin1 = 13
motorPin2 = 11
enablePin = 15

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

    global p
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(motorPin1, GPIO.OUT)
    GPIO.setup(motorPin2, GPIO.OUT)
    GPIO.setup(enablePin, GPIO.OUT)
    p = GPIO(enablePin, 1000)
    p.start(0)

def mapNum(value, fromLow, fromHigh, toLow, toHigh):
    return (toHigh - toLow) * (value - fromLow) / (fromHigh - fromLow) + toLow

def motor(ADC):
    value = ADC - 128
    if (value > 0):  # Turn motor forward.
        GPIO.output(motorPin1, GPIO.HIGH)
        GPIO.output(motorPin2, GPIO.LOW)
        print("Motor turning forward...")
    elif (value < 0)
        GPIO.output(motorPin1, GPIO.LOW)
        GPIO.output(motorPin2, GPIO.HIGH)
        print("Motor turning backward...")
    else:
        GPIO.output(motorPin1, GPIO.LOW)
        GPIO.output(motorPin2, GPIO.LOW)
        print("Motor stop...")
    p.start(mapNum(abs(value), 0, 128, 0, 100))
    print("The PWM duty cycle: " + str(abs(value) * 100 / 127)

def loop():
    while True:
        value = adc.analogRead(0)
        print ("ADC value: " + str(value))
        motor(value)
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
