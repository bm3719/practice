# Activate buzzer on button press.
import RPi.GPIO as GPIO
from time import sleep

buzzerPin = 11
buttonPin = 12

def setup():
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(buzzerPin, GPIO.OUT)
    GPIO.setup(buttonPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

def loop():
    while True:
        if GPIO.input(buttonPin) == GPIO.LOW: # Button pressed.
            GPIO.output(buzzerPin, GPIO.HIGH)
            print('Buzzer on')
        else:
            GPIO.output(buzzerPin, GPIO.LOW)
            print('Buzzer off')
        sleep(0.25);

def destroy():
    GPIO.cleanup() # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
