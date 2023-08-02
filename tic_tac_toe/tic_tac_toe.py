from Players import Players
import GameLogic

game_finished = False

# creating players
player_one = Players(input('Name:\n'), input('Age:\n'))
print(player_one)
player_two = Players(input('Name:\n'), input('Age:\n'))
print(player_two)

print(f'\n{player_one.name}, {player_two.name}, I assume You know the rules\n')
print(Players.game_vis)

players = [player_one, player_two]

for move in range(9):
    player = players[move%2]

    a = input(f'{player.name}, enter the place for {player.symbol}:\n')
    game_won = player.make_a_move(a)

    if game_won:
        print(f'Congratulations {player.name}, you won!')
        break

    elif move == 8:   print('It\'s a draw')