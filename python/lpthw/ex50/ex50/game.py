from random import randint
from room import Room
from lexicon import *
from parser import *

ten_forward = Room("Ten Forward", """
This is Ten Forward.  It is the recreational facility on the USS Enterprise,
where officers, crew, and passengers can, um... recreate.
""")
ten_forward.add_items(['riker'])
trois_quarters = Room("Troi's Quarters", """
You are in Troi's quarters.
""")
trois_quarters.add_items(['troi'])
datas_quarters = Room("Troi's Quarters", """
You are in Data's quarters.
""")
datas_quarters.add_items(['data', 'spot'])
hallway = Room("Hallway", """
You are in a hallway on board the USS Enterprise.
""")
ten_forward.add_paths({'south': hallway})
trois_quarters.add_paths({'west': hallway})
datas_quarters.add_paths({'east': hallway})
hallway.add_paths({'north': ten_forward,
                   'east': trois_quarters,
                   'west': datas_quarters})

class Game(object):
    def __init__(self):
        self.current_room = hallway
        self.inventory = ['microspanner']
        self.game_over = False

    def change_room(self, direction):
        if (direction in self.current_room.paths.keys()):
            self.current_room = self.current_room.paths[direction]
            self.look()
        else:
            print "You walk directly into a solid surface."

    def look(self):
        print self.current_room.description
        self.current_room.print_exits()
        self.current_room.print_items()

    def end_game(self, reason):
        self.game_over = True
        print reason
        print "Game over."

    def print_inventory(self):
        if (len(self.inventory) == 0):
            print "You possess nothing."
        else:
            print "You possess:"
            for i in self.inventory:
                print i
    
    def run_game(self):
        print """
A Night with Spot

You are a young crewman, just assigned to the Enterprise.  Despite the promises
made by the Starfleet recruiter at your local mall, so far your life here has
been boring and uneventful.  You have no friends and spend all of your off-duty
time alone in your quarters.  Frustrated and lonely, you resolve to make
tonight interesting, regardless of the consequences.
"""
        
        self.look()
        while(not self.game_over):
            input = raw_input("> ")
            if input == 'quit' or input == 'exit' or input == 'kill self':
                self.end_game("You commit suicide.")
            elif input == 'inventory' or input == 'i':
                self.print_inventory()
            elif input == 'sit':
                print "You sit on the floor for awhile, then stand back up."
            elif input == 'jump':
                print "You reach a height of 6 inches before gravity returns you to the floor."
            elif input == 'meditate':
                print "You self-medicate with some Vulcan neuropressure."
            elif input == 'listen':
                print "You hear the ever-present hum of the warp engine."
            elif input == 'pee' or input == 'urinate' or input == 'piss':
                print "Your bladder is already empty."
            elif input == 'poop' or input == 'shit':
                print "Your colon contains no fecal matter at the present time."
            elif input == 'think':
                print "Your mind fills with various perverse images for awhile."
            elif input == 'fart':
                print "You temporarily pollute some of the Enterprise's atmosphere."
            elif input == 'sleep':
                print "You are already well rested."
            elif input == 'wait' or input == 'z':
                print "You stand around idly for awhile, but regret not getting paid while doing it."
            elif input == 'look' or input == 'l':
                self.look()
            else:
                try:
                    # Handle abbreviated movement commands.
                    if input == 'n':
                        input = "go north"
                    elif input == 's':
                        input = "go south"
                    elif input == 'e':
                        input = "go east"
                    elif input == 'w':
                        input = "go west"
                    sentence = parse_sentence(scan(input))
                    self.do_action(sentence)
                except ParserError:
                    quips = ['Bwah?', 'Guh?', 'Derp?', 'Wuh?', 'Glorp?', 'Dave ain\'t here, man.',
                             'You glarbled the fleef mrok.', 'sdfsdfkg?']
                    print quips[randint(0, len(quips) - 1)]

    # There's probably a better way to handle verbs, but I can't think of it
    # right now.
    def do_action(self, sentence):
        # Handle room transitions.
        if sentence.verb == 'go':
            self.change_room(sentence.object)
        # Check if the object exists here.
        elif (not sentence.object in self.current_room.items) and (not sentence.object in self.inventory) and (not sentence.object == 'self'):
            print "You do not see %s here." % sentence.object
        # Start handling verbs.
        elif sentence.verb == 'eat':
            if sentence.object == 'self':
                self.end_game("""
You take several bites out of your arm and while you are just beginning to feel
full, you suddenly pass out due to blood loss and die.
""")
            elif sentence.object == 'riker':
                self.end_game("""
You attempt to take a bite out of Riker, but he overpowers you and instead takes
a few bites out of YOU.  You slowly bleed to death on the floor while everyone
laughs at you.
""")
            elif sentence.object == 'troi':
                self.end_game("""
You attempt to take a bite out of Troi, but she dodges your greedy maw long
enough to contact Riker on her communicator.  Riker arrives swiftly and
uppercuts you into a bloody pulp.
""")
            elif sentence.object == 'spot':
                self.end_game("""
You forcibly shove Spot into your mouth and choke to death.
""")
            elif sentence.object == 'data':
                self.end_game("""
You take several bites out of Data and choke to death as his plastic bits get
stuck in your throat.
""")
            else:
                self.end_game("You forcibly shove the " + str(sentence.object) + " into your esophagus and die.")
        elif sentence.verb in ['kill', 'attack', 'hit', 'murder']:
            if sentence.object == 'riker':
                if 'panties' in self.inventory:
                    print """
You hold the panties forth in Riker's direction.  His eyes light up and he
greedily snatches them from your grasp.  After fondling them for some time, he
puts them on his head and sits back down in his chair, but his face is soon
covered in a vile green slime that begins to consume his biomatter and he dies
a painful death.  As he will no longer be needing his phaser, you quietly
pocket it.

Acquired phaser.
"""
                    self.inventory.append('phaser')
                    self.inventory.remove('panties')
                    self.current_room.remove('riker')
                else:
                    self.end_game("""
You impotently flail your arms at Riker, who chuckles for awhile then delivers
a devastating uppercut, causing a fatal concussion.  As your life drains away,
you see Riker flexing over your defeated carcass, while several female ensigns
swoon at his most recent display of masculinity.
""")
            elif sentence.object == 'data':
                if 'phaser' in self.inventory:
                    print """
You melt Data's face with Riker's phaser, rendering him fully non-functional.
"""
                    datas_quarters.items.remove('data')
                else:
                    self.end_game("""
Your punches bounce harmlessly off Data's face.  Data then decides to use your
body to explore human sexuality.  After many days, you eventually die from
internal bleeding.
""")
            elif sentence.object == 'troi':
                self.end_game("""
You punch Troi as hard as you can in the face.  She falls over but manages to
contact Riker on her communicator before you can finish her off.  Riker quickly
arrives and severs your jugular vein with a powerful bite.
""")
            elif sentence.object == 'spot':
                if 'data' in self.current_room.items:
                    self.end_game("""
You only manage to get a single kick into Spot before Data incapacitates you
with a judo chop to the solar plexus.  Data then decides to use your body to
explore human sexuality.  After many days, you eventually die from an
infection due to a ruptured colon.
""")
                else:
                    self.end_game("""
After delivering a series of punishing kicks to Spot, he eventually dies.  You
feast upon his remains and return to your job mopping the Jeffries tubes.  Worf
eventually pins you for the murder due to your bloody fingerprints being all
over the crime scene.  You spend the remainder of your life in the Enterprise's
brig being sexually violated on a daily basis by your Nausicaan cellmate until
he decides to employ your body in his experimentation with necrophilia.
""")
            else:
                print "You strangle the %s for awhile, but nothing happens." % sentence.object
        elif sentence.verb == 'kiss':
            if sentence.object == 'riker':
                print """
You move in to kiss Riker's bearded lips, but he sees the homosexual passion in
your eyes and pushes you aside.  You fall to the floor and manage only to kiss
the filthy carpet.  Everyone in Ten Forward laughs at your pathetic self.  You
feel humiliated.
"""
            elif sentence.object == 'troi':
                print """
Troi can already sense your lustful urges and quickly removes her uniform and
panties.  Hours of sweaty Betazoid culture ensue and afterward Troi allows you
to keep the panties she was wearing as a souvenir, then leaves to go recite
poetry on the holodeck.

You feel a small sense of accomplishment from this deed, but still strangely
unsatisfied somehow.

Acquired panties.
"""
                self.inventory.append('panties')
                self.current_room.items.remove('troi')
            elif sentence.object == 'data':
                self.end_game("""
You begin to make out with Data's cold, impassive face.  Data stands motionless
for several minutes while you slobber upon him but then decides to use your
body to explore human sexuality.  After many days, you eventually die from
massive rectal bleeding.
""")
            elif sentence.object == 'spot':
                if not 'data' in self.current_room.items:
                    self.end_game("""
You kiss Spot on his furry face, innocently at first, but soon your beastiality
fetish overwhelms you and you sexually violate Spot for many hours.  In a
lust-filled mania, you hold aloft Spot into the air, proudly quote Prospero
from 'The Tempest', and take a massive, fatal bite from his ravaged body.

Your sexual frustrations temporarily sated, you return to work mopping the
Jeffries tubes until you die of a Betazoid STD 2 weeks later.  Though in
physical agony upon death, you die with a sick grin on your face.
""")
                else:
                    self.end_game("""
Data observes you kissing Spot and decides he still has much to learn about
this new human behavior.  He then decides to use your body to explore human
sexuality.  After many days, you eventually die from an infection related to a
prolapsed rectum.
""")
            elif sentence.object == 'self':
                print """
You kiss your arms and hands long enough to realize that this activity is
unlikely to improve your self-esteem or sense of self-worth.
"""
            else:
                print """
You kiss the %s passionately, to no effect.
""" % sentence.object
        elif sentence.verb in ['examine', 'e', 'look', 'l']:
            if sentence.object == 'riker':
                print """
Riker is posing here, barrel-chested and confident.  The top of his uniform is
unzipped a few inches past Starfleet standard, and a manly tuft of chest hair
is sprouting forth.  Riker momentarily glances over at you and decides you
aren't worthy of his interest.  He is apparently just back from an away
mission, as he is sporting a type 1 phaser on his belt.
"""
            elif sentence.object == 'troi':
                print """
Counselor Troi is here, apparently just off duty, since she is still in her
uniform.  This being the 7th season, you notice Troi's once youthful, Italian
appearance has given way to a tired biker-face and a generally less appealing
body shape.  You think to yourself, \"Hey, I'd still hit it!\"  Troi smirks
knowingly as you suddenly remember that she's empathic.
"""
            elif sentence.object == 'data':
                print """
Lieutenant Data stands here, stiffly erect and motionless.  You wonder if
Geordi perhaps put him in sleep mode to save power until Data's head eventually
swivels inhumanly in your direction and his vocoder outputs the words
\"Greetings, Crewman.\"
"""
            elif sentence.object == 'spot':
                print """
Here lies Spot, pet Cat of Data.  Spot is the only mammalian quadruped on the
ship and suddenly you realize that you haven't been in the company of one since
you left for Starfleet basic training.  The sight of Spot fills you with an
unfamiliar urge that you find hard to control.
"""
            elif sentence.object == 'phaser':
                print """
The standard issue Federation sidearm, the Type 1 phaser pistol.  This model
contains an extend 12 round magazine, polished feed-ramp, case-hardened steel
frame, full-length guide rod, extended grip safety, Hogue rubber grips, and
high visibility tritium night sights.
"""
            elif sentence.object == 'microspanner':
                print """
A tool you swiped while mopping Engineering one day, the microspanner is waved
around in front of a glowing plastic board while the light on the tip blinks.
"""
            elif sentence.object == 'panties':
                print """
The unwashed and recently-worn lower undergarments of Counselor Troi.  These
panties have a noxious odor emanating forth from them and upon close
inspection, there is a gelatinous, green-colored substance rhythmically
pulsating on the inner surface.
"""
            elif sentence.object == 'self':
                print """
You examine your unremarkable body for awhile and conclude that it is awkward
and unattractive.  Without a mirror, you cannot examine your face, but you have
seen it recently enough to remember that it is also unattractive and projects
only mediocrity.
"""
        elif sentence.verb == 'touch' or sentence.verb == 'feel':
            if sentence.object == 'riker':
                print """
You attempt to touch Riker, but he sees your groping hand approach and pushes
you to the floor.  Everyone in Ten Forward then has a good laugh at your
expense.  Life has conspired to humiliate you once again.
"""
            elif sentence.object == 'troi':
                print "Troi feels soft, warm, and squishy."
            elif sentence.object == 'data':
                print "Data feels cold and lifeless."
            elif sentence.object == 'spot':
                print """
Spot feels furry, and fur just happens to be your favorite texture.
"""
            elif sentence.object == 'phaser':
                print """
The phaser feels like an inanimate object, but holding it makes you feel like a
man.
"""
            elif sentence.object == 'microspanner':
                print """
The microspanner feels like a hard, metal object, which is what it is.
"""
            elif sentence.object == 'panties':
                print "The panties feel warm and moist."
            elif sentence.object == 'self':
                print """
The texture of your skin feels rough and lumpy, mostly due to the acne problem
you've suffered from since puberty.  You have several large, active pimples on
your nose and forehead, which feel a bit sore when touched, but the 10 years of
deep, permanent acne scars are what's mainly responsible for your ghoul-like
complexion.
"""
        elif sentence.verb == 'think':
            if sentence.object == 'riker':
                print """
You hate Riker.  You hate his dashing good looks.  You hate whatever makes him
irresistable to women.  Most of all, you hate his self-confidence and how
everything in his life works out for the best, while you suffer in obscurity.
"""
            elif sentence.object == 'troi':
                print """
You find yourself fantasizing about Counselor Troi less as she ages, but with
her in front of you, you nonetheless can't stop imagining her 'out of uniform.'
"""
            elif sentence.object == 'spot':
                print """
Something about Spot fills you with a warm feeling in your loins.
"""
            elif sentence.object == 'data':
                print """
While you envy Data's lack of emotion, you resent how an inanimate object like
him gets more women than you -- a real person who actually has feelings.
"""
            else:
                print """
You have no feelings about it one way or the other.
"""
        elif sentence.verb == 'smell':
            if sentence.object == 'riker':
                print "Riker smells strongly of masculinity."
            elif sentence.object == 'troi':
                print "Troi smells like spaghetti, very cheesy spaghetti."
            elif sentence.object == 'data':
                print "Data smells faintly of plastic."
            elif sentence.object == 'spot':
                print "Spot smells like cat food."
            elif sentence.object == 'phaser':
                print "The phaser smells like Riker's sweaty palm."
            elif sentence.object == 'microspanner':
                print "The microspanner smells like Geordi's sweaty palm."
            elif sentence.object == 'panties':
                print "The panties smell like space mold."
            elif sentence.object == 'self':
                print "You smell like pickles."
        elif sentence.verb == 'taste' or sentence.verb == 'lick':
            if sentence.object == 'riker':
                self.end_game("""
You approach Riker with your tongue extended.  He notices and swiftly
roundhouse kicks you across the room.  Your head hits the 3D chess set and
cracks your skull.  The last thing you hear before dying is everyone in
Ten Forward laughing at you and shouting praises upon a grinning Riker.
""")
            elif sentence.object == 'troi':
                print "Troi allows you to lick her face, which tastes of a strong, dry cheese."
            elif sentence.object == 'data':
                print """
Data observes you passively while you lick his plastic skin, which tastes, not
surprisingly, of plastic.
"""
            elif sentence.object == 'spot':
                self.end_game("""
You attempt to taste Spot but Data confuses your innocent curiosity for an
attempt to eat him.  He impales you with a robotic arm then stares into your
face with a blank expression as you gurgle your final breath.
""")
            elif sentence.object == 'phaser':
                print "The phaser tastes like Riker's sweaty palm."
            elif sentence.object == 'microspanner':
                print "The microspanner tastes like Geordi's sweaty palm."
            elif sentence.object == 'panties':
                self.end_game("""
You lick the panties repeatedly.  The taste of space mold is overwhelming and
like a punch in the face.  You start to feel light-headed and pass out.  Now in
the presence of a food source, the mold begins multiplying and devours your
body.
""")
            elif sentence.object == 'self':
                print "You lick your arm, which tastes like stale vinegar."
        elif sentence.verb == 'get':
            if sentence.object in self.inventory:
                print "You already possess the %s" % sentence.object
            else:
                print "That will not fit in your pocket."
            
def main():
    Game().run_game()

if __name__ == "__main__":
    main()
        
