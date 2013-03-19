# Check the weather for Richmond, VA on The Weather Underground.
import pexpect
import sys

child = pexpect.spawn ('telnet rainmaker.wunderground.com')
# Dump output to screen.
child.logfile = sys.stdout
# First prompt just expects a return.
child.expect('continue:')
child.sendline('')
# Wait for the code prompt, then enter "RIC".
child.expect('code-- ')
child.sendline('RIC')
# Go ahead and exit.
child.expect('exit: ')
child.send('X')
# Cleanup.
if child.isalive():
    child.close()
if child.isalive():
    print '\nChild did not exit gracefully.'
else:
    print '\nChild exited gracefully.'

