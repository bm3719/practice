# Make LED fade in/out
import RPi.GPIO as GPIO
from time import sleep

ledPin = 33

def setup():
    global pwm
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(ledPin, GPIO.OUT)  # Set the ledPin to OUTPUT mode.
    GPIO.output(ledPin, GPIO.LOW)
    pwm = GPIO.PWM(ledPin, 500)   # Set PWM frequency to 500Mhz
    pwm.start(0)

def loop():
    while True:
        for dc in range(1, 101, 1):
            pwm.ChangeDutyCycle(dc)
            sleep(0.1)
        sleep(1)
        for dc in range(100, -1, -1):
            pwm.ChangeDutyCycle(dc)
            sleep(0.1)
        sleep(1)

def destroy():
    pwm.stop()     # Stop PWM
    GPIO.cleanup() # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
