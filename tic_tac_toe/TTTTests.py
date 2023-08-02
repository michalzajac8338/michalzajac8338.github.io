import unittest
import GameLogic
from Players import Players

def simulate_game(players,O,X):
    OX = []

    if len(O) > len(X):
        while True:
            OX.insert(0,O.pop())
            if len(X) < 1:  break
            OX.insert(0,X.pop())
    else:
        while len(X) > 0:
            OX.insert(0,X.pop())
            OX.insert(0,O.pop())

    move = 0
    for move in range(9):
        player = players[move%2]
        print(f'{player.name}, it\'s Your turn:')
        game_won = player.make_a_move(OX[move])

        if game_won:
            return (f'Congratulations {player.name}, you won!')
            break

        if move == 8:   return 'It\'s a draw'

class TTTTesting(unittest.TestCase):

    def setUp(self):
        self.player_one = Players('Michael', 24)
        self.player_two = Players('Milo', 21)
        self.players = [self.player_one, self.player_two]

    def test_draw(self):
        O = [1,3,4,6,8]
        X = [2,5,7,9]
        result = simulate_game(self.players,O,X)
        self.assertEqual(result, 'It\'s a draw')

    def test_win_player_one(self):
        O = [1,2,3]
        X = [4,5]
        result = simulate_game(self.players,O,X)
        self.assertEqual(result, f'Congratulations {self.player_one.name}, you won!')

    def test_win_player_two(self):
        O = [1,2,9]
        X = [4,5,6]
        result = simulate_game(self.players,O,X)
        self.assertEqual(result, f'Congratulations {self.player_two.name}, you won!')

    def tearDown(self):
        del self.player_one
        del self.player_two

        Players.game_vis = '1|2|3\n4|5|6\n7|8|9\n'
        Players.index = 0
        GameLogic.XandO = []

unittest.main()