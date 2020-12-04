import RPi.GPIO as GPIO
from time import sleep

ledPins = [11, 12, 13, 15, 16, 18, 22, 3, 5, 24]

def setup():
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(ledPins, GPIO.OUT)  # Set the ledPin to OUTPUT mode.

def loop():
    while True:
        for led in ledPins:
            GPIO.output(led, GPIO.LOW)
            sleep(0.1)
            GPIO.output(led, GPIO.HIGH)


def destroy():
    GPIO.cleanup()                # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
