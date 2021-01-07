# Make LED cycle through random RGB colors.
import RPi.GPIO as GPIO
from time import sleep
from random import randint

pins = {"red": 11, 'green': 12, 'blue': 13}

def setup():
    global pwmRed, pwmGreen, pwmBlue
    GPIO.setmode(GPIO.BOARD)      # Use physical board numbering.
    GPIO.setup(list(pins.values()), GPIO.OUT)    # Set the ledPin to OUTPUT mode.
    GPIO.output(list(pins.values()), GPIO.HIGH)
    pwmRed = GPIO.PWM(pins['red'], 2000)   # Set PWM frequency to 2KHz
    pwmGreen = GPIO.PWM(pins['green'], 2000)
    pwmBlue = GPIO.PWM(pins['blue'], 2000)
    pwmRed.start(0)
    pwmGreen.start(0)
    pwmBlue.start(0)

def setColors(r, g, b):
    pwmRed.ChangeDutyCycle(r);
    pwmGreen.ChangeDutyCycle(g);
    pwmBlue.ChangeDutyCycle(b);

def loop():
    while True:
        r = randint(0, 100);
        g = randint(0, 100);
        b = randint(0, 100);
        setColors(r, g, b)
        print('R=' + str(r) + ' G=' + str(g) + ' B=' + str(b))
        sleep(1)

def destroy():
    pwmRed.stop()  # Stop PWM
    pwmGreen.stop()
    pwmBlue.stop()
    GPIO.cleanup() # Release all GPIO.

print('Program is starting... \n')
setup()
try:
    loop()
except KeyboardInterrupt:
    destroy()
