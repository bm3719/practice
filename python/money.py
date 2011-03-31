# Just a simple program to calculate accumulated interest.  If only it were so
# easy in real life.

trace = 1  # print each year?

def calc(principal, interest, years):
    for y in range(years):
        principal = principal * (1.00 + (interest / 100.0))
        if trace: print y+1, '=> %.2f' % principal
    return principal
    
