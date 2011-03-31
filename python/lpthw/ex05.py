name = 'Zed A. Shaw'
age = 35 # not a lie
height = 74 # inches
weight = 180 # lbs
eyes = 'Blue'
teeth = 'White'
hair = 'Brown'

print "Let's talk about %s." % name
print "He's %d inches tall." % height
print "He's %d pounds heavy." % weight
print "Actually that's not too heavy."
print "He's got %s eyes and %s hair." % (eyes, hair)
print "His teeth are usually %s depending on the coffee." % teeth

# this line is tricky, try to get it exactly right
print "If I add %d, %d, and %d I get %d" % (
    age, height, weight, age + height + weight)

# 1. Done.
# 2.
print "Testing %r" % "sdf"
print "Testing %r" % 3
# 3. Already know them.
# 4.
def in_to_cm(inches):
    print "%d inches = %0.2f centimeters." % (inches, inches * 2.54)

def lbs_to_kg(lbs):
    print "%d pounds = %0.2f kilograms." % (lbs, lbs * 0.453)
