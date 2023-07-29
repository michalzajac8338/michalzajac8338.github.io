class Players:
    ox = ['o','x']
    index = 0
    def __init__(self, name, age):
        self.name = name
        self.age = age
        self.symbol = Players.ox[Players.index]
        Players.index += 1
    def __repr__(self):
        return f'I am {self.name}, I am {self.age} years old and my symbol is {self.symbol}'