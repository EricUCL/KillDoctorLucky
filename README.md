# CS 5010 Project

This repo represents the coursework for CS 5010.

**Developer:** Yichen Wang

**Email:** wang.yichen7@northeastern.edu

## How to Run
To run the jar file please use below, where args represent map text file

In your working directory ~/KillDoctorLucky run this script:
```bash
java -jar res/KillDoctorLucky.jar res/mansion.txt 10
```
The first argument represents the file containing the map. The second argument represents the max turns.

## Example Runs

### the target character's pet effect on the visibility of a space from neighboring spaces
- res/example_run_1.txt
- line 169 shows that this room is occupied with pet thus invisible.
### the player moving the target character's pet
- res/example_run_2.txt
- line 54 shows that the player move the pet.
### a human-player making an attempt on the target character's life
- res/example_run_3.txt
- line 55 shows that the player hit the target.
### a computer-controlled player making an attempt on the target character's life
- res/example_run_4.txt
- line 30 shows that the computer player hit the target.
### a human-player winning the game by killing the target character
- res/example_run_5.txt
- line 83 shows that the player wins.
### a computer-controlled player winning the game by killing the target character
- res/example_run_6.txt
- line 33 shows that the computer player wins.
### the target character escaping with his life and the game ending
- res/example_run_7.txt
- line 401 shows that the target escaped.
### Extra Credit: Wandering Pet
- res/myapp_0_0.log
- This is a log file for tracking the location of the pet. The route conforms to DFS.

## Assumptions
I assume that the user has the ability to understand command line operations. I assume that the user tries to enter legitimate, system-understandable input.

## Limitations
The program does not implement the GUI; the readability of the program's command line interface could be improved.

## Design Changes
I implemented all the rules of the game to make the program truly playable by players.
I separated out some of the functionality that should be managed by the Controller and added the View module to achieve a perfect MVC architecture.

## Reference
https://boardgamegeek.com/boardgame/257/kill-doctor-lucky

https://www.baeldung.com/java-depth-first-search