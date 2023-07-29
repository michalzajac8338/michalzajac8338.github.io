from Players import Players
import GameLogic

game_vis = '1|2|3\n4|5|6\n7|8|9'
game_finished = False
Os = []
Xs = []

print(game_vis)
player1 = Players(input('Name:\n'), input('Age:\n'))
print(player1)

def first_player(o):
    try:
        GameLogic.proper_value(o,Os)

        global game_vis
        game_vis = game_vis.replace(str(o),'o')
        print(game_vis)
        
        Os.append(o)
        return GameLogic.check_win(Os)
    except Exception:
        print('Retry')
        first_player(int(input('Kółko:\n')))

def second_player(x):

    global game_vis
    game_vis = game_vis.replace(str(x),'x')
    print(game_vis)
    Xs.append(x)
    return GameLogic.check_win(Xs)

while game_finished == False:
    game_finished = first_player(int(input('Kółko:\n')))
    game_finished = second_player(int(input('Krzyżyk:\n')))
    