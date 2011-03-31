from nose.tools import *
from ex50 import lexicon

def setup():
    print "SETUP!"

def teardown():
    print "TEAR DOWN!"

def test_directions():
    assert_equal(lexicon.scan("north"), [('direction', 'north')])
    result = lexicon.scan("north south east")
    assert_equal(result, [('direction', 'north'),
                          ('direction', 'south'),
                          ('direction', 'east')])

def test_verbs():
    assert_equal(lexicon.scan("go"), [('verb', 'go')])
    result = lexicon.scan("go kill eat")
    assert_equal(result, [('verb', 'go'),
                          ('verb', 'kill'),
                          ('verb', 'eat')])

def test_stops():
    assert_equal(lexicon.scan("the"), [('stop', 'the')])
    result = lexicon.scan("the in of")
    assert_equal(result, [('stop', 'the'),
                          ('stop', 'in'),
                          ('stop', 'of')])

def test_nouns():
    assert_equal(lexicon.scan("troi"), [('noun', 'troi')])
    result = lexicon.scan("troi panties")
    assert_equal(result, [('noun', 'troi'),
                          ('noun', 'panties')])

def test_numbers():
    assert_equal(lexicon.scan("1234"), [('number', 1234)])
    result = lexicon.scan("3 91234")
    assert_equal(result, [('number', 3),
                          ('number', 91234)])

def test_errors():
    assert_equal(lexicon.scan("ASDFDFASDF"), [('error', 'asdfdfasdf')])
    result = lexicon.scan("spot IAS troi")
    assert_equal(result, [('noun', 'spot'),
                          ('error', 'ias'),
                          ('noun', 'troi')])
