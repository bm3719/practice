from sys import argv
from os.path import exists

script, from_file, to_file = argv

print "Copying from %s to %s" % (from_file, to_file)

# we could do these two on one line too, how?
input = open(from_file)
indata = input.read()

# print "The input file is %d bytes long" % len(indata)

if exists(to_file):
    print "The output file exists."
print "Ready, hit RETURN to continue, CTRL-C to abort."
raw_input()

output = open(to_file, 'w')
output.write(indata)

print "Alright, all done."

output.close()
input.close()

# 1. Done.
# 2. Done.
# 3. Could do it like this, though it's fugly.
# open(to_file, 'w').write(open(from_file).read()).close()
# 4. Already know this.
# 5. The command `type' is equivalent.
# 6. Closing tends to flush output buffers.
