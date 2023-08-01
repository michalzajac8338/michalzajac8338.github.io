import TTTExceptions

def check_win(XO):
    if len(XO) >= 3:
        if 5 in XO:
            if 2 in XO and 8 in XO:
                return True
            if 4 in XO and 6 in XO:
                return True
            if 1 in XO and 9 in XO:
                return True
            if 3 in XO and 7 in XO:
                return True
        if 1 in XO:
            if 2 in XO and 3 in XO:
                return True
            if 4 in XO and 7 in XO:
                return True
        if 9 in XO:
            if 3 in XO and 6 in XO:
                return True
            if 7 in XO and 8 in XO:
                return True
    return False

XandO = []
def proper_value(xo,XO):
    if xo in XandO:
        raise TTTExceptions.AlreadyTaken(xo)
    elif xo < 1 or xo > 9:
        raise TTTExceptions.WrongNumber
    else:
        XandO.append(xo)
