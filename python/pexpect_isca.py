# Log into ISCABBS as a Guest user.  This doesn't bother logging out
# gracefully, but who cares?
import pexpect

child = pexpect.spawn ('telnet bbs.iscabbs.com')
child.expect ('Name: ')
child.sendline ('Guest')
# Wait for the continue prompt, then hit return.
child.expect ('continue...', timeout=10)
child.sendline ('')
# I never want to read first few forums, so skip them.
child.expect ('->')
child.send ('sgsgsg')
# We're logged in, so let's get into interactive mode and start wasting time.
print("Escape character is '^]'.\n")
child.interact()
# At this point the script is running again.
print 'Left interactve mode.'
if child.isalive():
    child.close()
if child.isalive():
    print 'Child did not exit gracefully.'
else:
    print 'Child exited gracefully.'
