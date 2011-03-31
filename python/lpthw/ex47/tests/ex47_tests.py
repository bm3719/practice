from nose.tools import *
from ex47.game import Room

def test_room():
    gold = Room("GoldRoom",
                """This room has gold in it you can grab. There's a
                door to the north.""")
    assert_equal(gold.name, "GoldRoom")
    assert_equal(gold.paths, {})

def test_room_paths():
    center = Room("Center", "Test room in the center.")
    north = Room("North", "Test room in the north.")
    south = Room("South", "Test room in the south.")

    center.add_paths({'north': north, 'south': south})
    assert_equal(center.go('north'), north)
    assert_equal(center.go('south'), south)

def test_map():
    start = Room("Start", "You can go west and down a hole.")
    west = Room("Trees", "There are trees here, you can go east.")
    down = Room("Dungeon", "It's dark down here, you can go up.")

    start.add_paths({'west': west, 'down': down})
    west.add_paths({'east': start})
    down.add_paths({'up': start})

    assert_equal(start.go('west'), west)
    assert_equal(start.go('west').go('east'), start)
    assert_equal(start.go('down').go('up'), start)

# helper function not named test_* won't get run, but can be used elsewhere.
def something():
    print "sdfds"

# 1. I kinda like nose, so I'll stick with it for now.
# 2. Looks like doctest incorporates the unit test inside a doc comment.  Not
#    sure if I like tests there though, but it has some upsides, namely the
#    concise test syntax and the fact that there's no external package
#    dependency.  I may use these in the future if I can't count on nose being
#    present.
# 3. Skipping.
