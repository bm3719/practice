from nose.tools import *
from ex50 import lexicon
from ex50 import parser
from ex50.room import Room

def setup():
    print "SETUP!"

def teardown():
    print "TEAR DOWN!"

def test_room_items():
    ten_forward = Room("Ten Forward", "You are in Ten Forward")
    ten_forward.add_items(['riker'])
    ten_forward.add_items(['troi'])
    assert_equal(ten_forward.items, ['riker', 'troi'])
