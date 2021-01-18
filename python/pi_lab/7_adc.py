# Use ADC microcontroller with poteniometer.
# NOTE: Requires I2C enabled in OS, smbus package, and custom ADCDevice
# package.
import time
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

def loop():
    while True:
        value = adc.analogRead(0)  # Read channel 0.
        voltage = value / 255.0 * 3.3
        print ("ADC value: " + str(value) + ", Voltage: " + str(voltage))
        time.sleep(0.1)

def destroy():
    adc.close()

print('Program is starting... \n')
try:
    setup()
    loop()
except KeyboardInterrupt:
    destroy()
