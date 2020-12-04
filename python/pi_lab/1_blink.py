import RPi.GPIO as GPIO
import time

ledPin = 11

def setup():
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(ledPin, GPIO.OUT)  # Set the ledPin to OUTPUT mode.
    GPIO.output(ledPin, GPIO.LOW) # Make ledPin output LOW level.
    print('Using pin' + str(ledPin))

def loop():
    while True:
        GPIO.output(ledPin, GPIO.HIGH) # Make ledPin output HIGH level to turn on LED.
        print('LED turned on. >>>')
        time.sleep(1)

        GPIO.output(ledPin, GPIO.LOW)  # Turn off LED.
        print('LED turned off. <<<')
        time.sleep(1)

def destroy():
    GPIO.cleanup()                # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
