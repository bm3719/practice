from nose.tools import *
from ex50 import lexicon
from ex50 import parser

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
    t_list = lexicon.scan("kill the riker")
    result = parser.parse_sentence(t_list)
    assert_equal(result.subject, 'player')
    assert_equal(result.verb, 'kill')
    assert_equal(result.object, 'riker')

def test_bad_sentence():
    t_list = lexicon.scan("riker north the")
    assert_raises(parser.ParserError, parser.parse_sentence, t_list)

