directions = ['north', 'south', 'east', 'west',
              'down', 'up', 'left', 'right', 'back']
verbs = ['go', 'stop', 'kill', 'eat', 'kiss', 'get', 'smell',
         'attack', 'examine', 'touch', 'feel', 'think', 'hit',
         'murder', 'e', 'taste', 'l', 'look', 'lick']
stops = ['the', 'in', 'of', 'from', 'at', 'it', 'to', 'about']
nouns = ['door', 'spot', 'troi', 'tricorder', 'panties', 'riker',
         'microspanner', 'data', 'phaser', 'self']

def scan(input_text):
    sentence = []
    words = input_text.lower().split()
    for w in words:
        sentence.append(construct_tuple(w))
    return sentence

def construct_tuple(word):
    if word in verbs:
        return ('verb', word)
    elif word in stops:
        return ('stop', word)
    elif word in directions:
        return ('direction', word)
    elif word in nouns:
        return ('noun', word)
    elif convert_number(word) != None:
        return ('number', convert_number(word))
    else:
        return ('error', word)

def convert_number(word):
    try:
        return int(word)
    except ValueError:
        return None

