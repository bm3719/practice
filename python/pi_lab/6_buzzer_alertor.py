# Activate passive buzzer on button press, using PWM of defined frequency.
import RPi.GPIO as GPIO
from time import sleep
import math

buzzerPin = 11
buttonPin = 12

def setup():
    global p;
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(buzzerPin, GPIO.OUT)
    GPIO.setup(buttonPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)
    p = GPIO.PWM(buzzerPin, 1)
    p.start(0)

def loop():
    while True:
        if GPIO.input(buttonPin) == GPIO.LOW: # Button pressed.
            alertorStart()
            print('Buzzer on')
        else:
            alertorStop()
            print('Buzzer off')
        sleep(0.25)

def alertorStart():
    p.start(50)
    for x in range(0, 361):  # Sine wave.
         sinVal = math.sin(x * (math.pi / 180.0))
         toneVal = 2000 + sinVal * 500
         p.ChangeFrequency(toneVal)
         sleep(0.001)

def alertorStop():
    p.stop()

def destroy():
    GPIO.cleanup() # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
