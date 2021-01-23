# Use ADC microcontroller with 3x potentiometers to control RGB color.
import RPi.GPIO as GPIO
import time
import math
from ADCDevice import *

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
    GPIO.setmode(GPIO.BOARD)

def loop():
    while True:
        value = adc.analogRead(0)
        voltage = value / 255.0 * 3.3
        rt = 10 * voltage / (3.3 - voltage) # Thermistor resistance.
        tempK = 1 / (1 / (273.15 + 25) + math.log(rt / 10) / 3950.0)
        tempC = tempK - 273.15

        print ("ADC value: " + str(value) + ", Voltage: " + str(voltage) + ", Temperature: " + str(tempC))
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
