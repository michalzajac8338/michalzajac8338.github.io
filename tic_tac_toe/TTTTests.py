import unittest
import keyboard


from Players import Players

keyboard.press_and_release("enter")


def simulate_game(players,O,X):
    OX = []

    while len(O) > 0: #isinstance(O[0],int):
        OX.insert(0,O.pop())
        if len(X) < 1:  break
        OX.insert(0,X.pop())

    for move in range(9):
        player = players[move%2]
        game_won = player.make_a_move(OX[move])
        

        if game_won:
            return (f'{player.name} won')
            break

        if move == 8:   return 'Draw'

class TTTTesting(unittest.TestCase):

    def setUp(self):
        print('anything1111\n\n\n\n\n')
        player_one = Players('Michael', 24)
        player_two = Players('Milo', 21)
        self.players = [player_one, player_two]

    def test_draw(self):
        O = [1,3,4,6,8]
        X = [2,5,7,9]
        result = simulate_game(self.players,O,X)
        self.assertEqual(result, 'Draw')

unittest.main()