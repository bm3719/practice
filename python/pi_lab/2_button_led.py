import RPi.GPIO as GPIO
from time import sleep

ledPin = 11
buttonPin = 12

def setup():
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(ledPin, GPIO.OUT)  # Set the ledPin to OUTPUT mode.
    GPIO.setup(buttonPin, GPIO.IN, pull_up_down=GPIO.PUD_UP) # Set button to PULL UP input mode.

def loop():
    while True:
        if GPIO.input(buttonPin) == GPIO.LOW: # If button pressed.
            GPIO.output(ledPin, GPIO.HIGH) # Make ledPin output HIGH level to turn on LED.
            print('LED is turned on.')
        else:
            GPIO.output(ledPin, GPIO.LOW)  # Turn off LED.
            # print('LED turned off. <<<')
        sleep(0.1)

def destroy():
    GPIO.cleanup()                # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
