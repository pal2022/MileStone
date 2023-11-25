# MileStone
Mansion Game is an interactive text based game developed by me. I made this game as a project
for a project assignment for Programming design paradigm.

GAME DESCRIPTION
Mansion game is a multiplayer game where players move between rooms in a mansion. They can get items with special features from the room.
They can look around the nearby rooms to decide where to go next. There is a target character which moves in a particular pattern in the mansion.
Players can interact with each other while playing the game. It is a game with human players and computer controlled players.

FEATURES
-It can be a multiplayer or single player game.
-Players can explore the rooms.
-Players can pick items.
-Players can move around the neighbouring rooms.
-World image with players current position.
![image](https://github.com/pal2022/MileStone/assets/132899382/7ad44023-542e-49f9-bba7-eb772990dcb3)

ASSUMPTIONS for the game

-Players are expected to write input using keyboard in form of text or numbers.
-The game is a text based game and is displayed using console or terminal.
-There are fixed number of rooms and items.
-Two rooms are considered neighbours onlt if they share a wall between them.
-Players have a max limit to pick items.
-Target character moves between rooms in a fixed pattern.
-The game has exceptions to handle the invalid inputs of the user
-It is a text based game.
-The world image file shows the spaces, items and for the players and the target character in their current room.
MileStone 3 assumptions
- Players are given an extra oppurtunity to attack the target if they coincidently come to the same room as the player.
- When using looking around command player can only see left and right neighbours of all the neighbours of that room assuming there are only two windows, one on the left wall and one on the right wall.
- Now the window on the left is assumed to to bigger hence player can also see the items present in the same room.
- The player having the most point/ causing most damage to the target wins the game.
- When printing the neighbours of a room if one of the neighbours has pet then it is not printed.
- After attacking the target the item used to attack the target is removed from the player's inventory.
- If player chooses to attack the target but doen't have any item then default attack poking him in the eye causing 1 damage to the target occurs.
- The target character excapes if maximum number of turns is reached and it's health is greater than 0.
- The game ends even if the max turns not reached but the target character dies.
- The maxturns of the game is not decided by the users.
- Players cannot attack the target if they are watched by other players.


HOW TO RUN JAR FILE
Milestone3.jar jar file
The jar file is designed to execute the game. It is a text based game.
It has both human players and computer controlled players.
How to execute jar file
java -jar Milestone3.jar C:\Users\hp\eclipse-workspace\lab00_getting_started\MileStone1\model\src\run\PalMansion.txt  20
The file path and max number of turns are required when running the jar file.
Now the game starts. Enter the number of players to play the game and then write their name and the space id to continue 
with the game by using options from menu.
![image](https://github.com/pal2022/MileStone/assets/132899382/13bda3ce-ffcf-4ccd-9ee0-b9bb73fbfc67)

Citations
I don't have specific links right now, but some of the websites I have referred are geeksforgeeks, stackoverflow, tutorialspoint.
https://www.geeksforgeeks.org/
https://stackoverflow.com/
https://www.tutorialspoint.com/index.htm


