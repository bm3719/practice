#!/usr/bin/python

import sys

#for line in sys.stdin.readlines():
for line in sys.stdin:
    # Selects return rows that are tab-delimited.
    fields = line.strip().split('\t')

    # Just store id field to check for blank records.
    id = fields[0]
    name = fields[1]
    job = fields[2]
    cash = fields[3]
    checking = fields[4]
    savings = fields[5]
    investments = fields[6]
    equity = fields[7]
    debt = fields[8]
    mortgage = fields[9]

    # Clean up bad records.
    # Skip blank names and header columns.
    if id == '\n' or name == 'Name':
        continue

    # Aggregate expected output columns.
    assets = float(cash) + float(checking) + float(savings) + float(investments) + float(equity)
    liabilities = float(debt) + float(mortgage)
    # Create comma-delimited output for each row.
    print ','.join([str(name), str(job), str(assets), str(liabilities)])

    
    
