class WrongNumber(Exception):
    def __str__(self):
        return 'The number doesn\'t belong to [1:9]'

class AlreadyTaken(Exception):
    def __init__(self,position):
        self.position = position
        
    def __str__(self):
        return f'Position {self.position} is already taken!'