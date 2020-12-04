# Switch an LED on/off with button.

import RPi.GPIO as GPIO
from time import sleep

ledPin = 11
buttonPin = 12
ledState = False

def setup():
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(ledPin, GPIO.OUT)  # Set the ledPin to OUTPUT mode.
    GPIO.setup(buttonPin, GPIO.IN, pull_up_down=GPIO.PUD_UP) # Set button to PULL UP input mode.

# Event handler
def buttonEvent(channel):
    global ledState
    print('buttonEvent GPIO' + str(channel))
    ledState = not ledState
    if ledState:
        print('LED is turned on.')
    else:
        print('LED is turned off.')
    GPIO.output(ledPin, ledState)


def loop():
    GPIO.add_event_detect(buttonPin, GPIO.FALLING, callback = buttonEvent, bouncetime = 300)
    while True:
        pass;

def destroy():
    GPIO.cleanup()                # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
