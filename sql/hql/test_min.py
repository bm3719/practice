#!/usr/bin/python

import sys

for line in sys.stdin:
    fields = line.strip().split('\t')

    first = fields[0]
    last = fields[1]
    
    print ','.join([str(first), str(last)])

    
    
