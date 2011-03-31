from nose.tools import *
from ex49 import lexicon
from ex49 import parser

def setup():
    print "SETUP!"

def teardown():
    print "TEAR DOWN!"

def test_direction_sentence():
    t_list = lexicon.scan("go north")
    result = parser.parse_sentence(t_list)
    assert_equal(result.subject, 'player')
    assert_equal(result.object, 'north')
    assert_equal(result.verb, 'go')

def test_stops():
    t_list = lexicon.scan("kill the bear")
    result = parser.parse_sentence(t_list)
    assert_equal(result.subject, 'player')
    assert_equal(result.verb, 'kill')
    assert_equal(result.object, 'bear')

def test_bad_sentence():
    t_list = lexicon.scan("bear north the")
    assert_raises(parser.ParserError, parser.parse_sentence, t_list)

# 1. I already know I prefer functions managed just by module when possible.
# 2. Will do this in the next exercise.
# 3. Will do this in the next exercise.
# 4. Maybe extend this externally to have various handlers mapped for certain
#    kinds of input and/or certain keywords.
