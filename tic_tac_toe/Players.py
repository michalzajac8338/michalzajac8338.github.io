import GameLogic

class Players:

    ox = ['o','x']
    index = 0
    game_vis = '1|2|3\n4|5|6\n7|8|9'
    print(game_vis)
    
    def __init__(self, name, age):
        self.name = name
        self.age = age
        self.symbol = Players.ox[Players.index]
        self.squares = []
        Players.index += 1
        
    def __repr__(self):
        return f'I am {self.name}, I am {self.age} years old and my symbol is {self.symbol}'
    
    def make_a_move(self,square):
        try:
            self.square = int(square)
            GameLogic.proper_value(self.square,self.squares)
            self.squares.append(self.square)
            Players.game_vis = Players.game_vis.replace(str(self.square),self.symbol)
            print(Players.game_vis)
        
            return GameLogic.check_win(self.squares)
        
        except (ValueError, Exception) as e:
            print(e)
            self.make_a_move(int(input('Try again\n')))