class Room(object):
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.paths = {}
        self.items = []

    def go(self, direction):
        return self.paths.get(direction, None)

    def add_paths(self, paths):
        self.paths.update(paths)

    def add_items(self, new_items):
        self.items.extend(new_items)

    def take_item(self, item):
        if (item in self.items):
            self.items.remove(item)
            return(item)
        else:
            return None

    def print_exits(self):
        if len(self.paths.keys()) > 0:
            for k in self.paths.keys():
                print "There is an exit %s." % k

    # TODO: Change this to handle plurals and proper nouns.
    def print_items(self):
        if len(self.items) > 0:
            for i in self.items:
                s = i[0].capitalize() + i[1:]
                print "%s is here." % s
