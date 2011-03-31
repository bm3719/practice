directions = ['north', 'south', 'east', 'west',
              'down', 'up', 'left', 'right', 'back']
verbs = ['go', 'stop', 'kill', 'eat']
stops = ['the', 'in', 'of', 'from', 'at', 'it']
nouns = ['door', 'bear', 'princess', 'cabinet']

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


# class Lexicon(object):
#     def __init__(self):
#         self.sentence = []
#         self.directions = ['north', 'south', 'east', 'west',
#                            'down', 'up', 'left', 'right', 'back']
#         self.verbs = ['go', 'stop', 'kill', 'eat']
#         self.stops = ['the', 'in', 'of', 'from', 'at', 'it']
#         self.nouns = ['door', 'bear', 'princess', 'cabinet']

#         def scan(self, input_text):
#             words = input_text.lower().split()
#             for w in words:
#                 sentence.append(construct_tuple(w))
#             return sentence

#         def construct_tuple(self, word):
#             if word in verbs:
#                 return ('verb', word)
#             elif word in stops:
#                 return ('stop', word)
#             elif word in directions:
#                 return ('direction', word)
#             elif word in nouns:
#                 return ('noun', word)
#             elif convert_number(word) != None:
#                 return ('number', convert_number(word))
#             else:
#                 return ('error', word)

#         def convert_number(self, word):
#             try:
#                 return int(word)
#             except ValueError:
#                 return None

# 1. Skipping.  I think these are adequate.
# 2. Skipping.
# 3. Done.
# 4. "234".isdigit() works.
# 5. Shorter.
