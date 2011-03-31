def for_func(i, numbers, inc):
    for n in range(i, 10, inc):
        print "At the top i is %d" % i
        numbers.append(n)
        print "Numbers now: ", numbers
        print "At the bottom i is %d" % i

i = 0
numbers = []

for_func(i, numbers, 2)

print "The numbers: "

for num in numbers:
    print num

# 1. Done.
# 2. Done.
# 3. Done.
# 4. Done.
# 5. Done.
