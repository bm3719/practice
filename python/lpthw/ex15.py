from sys import argv

script, filename = argv

txt = open(filename)

print "Here's your file: %r:" % filename
print txt.read()

# print "I'll also ask you to type it again:"
# file_again = raw_input("> ")

# txt_again = open(file_again)

# print txt_again.read()

# 1. Skipping.
# 2. Done.
# 3. A function is a self-contained algorithm that returns a value.  A method
#    is an algorithm contained within a class.
# 4. Done.
# 5. Depends on the use case, I guess.
# 6. I looked.
# 7. Done.
# 8.
txt.close()
