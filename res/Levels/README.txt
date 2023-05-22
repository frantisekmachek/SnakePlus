The files inside the Levels folder need to be in this format:

--------------------------
Level[NUMBER]

example: Level1
--------------------------

The file also has to be a text document (extension .txt).

The text inside also follows a format:

--------------------------------------------------------------
Each wall inside the Level is written as x,y.

x,y

example:
0,0
0,1
1,0
1,1

Here, the walls would be in the top left corner, creating a
square.
--------------------------------------------------------------

NOTE: Coordinates 0,0; 1,0 and 2,0 cannot be occupied by walls, because the Snake is spawned there.