cities = {'CA': 'San Francisco', 'MI': 'Detroit',
          'FL': 'Jacksonville'}

cities['NY'] = 'New York'
cities['OR'] = 'Portland'

def find_city(themap, state):
    if state in themap:
        return themap[state]
    else:
        return "Not found."

# ok pay attention!
cities['_find'] = find_city

while True:
    print "State? (ENTER to quit)",
    state = raw_input("> ")

    if not state: break

    # this line is the most important ever! study!
    city_found = cities['_find'](cities, state)
    print city_found

# 1. Read it.
# 2. Actually, you can do most things with them, but some things are harder.
# 3. I'll print the keys and values, I guess.
for x in cities.items():
    print "%r: %r" % x
