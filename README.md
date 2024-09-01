# NumberGuessingGame
## ABSTRACT
The Java number guessing game demonstrates programming skills with difficulty levels, time limits, and hints. Emphasizing input validation, concurrency, and exception handling, it provides a stable experience, highlighting the creativity and joy of coding.
The Game challenges players to guess a secret number with multiple difficulty levels, time limits, and a hint system. This interactive game aims to enhance the user’s deductive skills in an engaging way.
The Java project presented is a console-based number guessing game designed to entertain and challenge players of varying skill levels. The game offers three distinct difficulty levels - Easy, Medium, and Hard, each with its own range of numbers and time limits. Players are prompted to guess a randomly generated secret number within the specified range, with the objective of correctly identifying it within the allotted time.
### Key Features:
1.	Multiple Difficulty Levels: Players can choose from three difficulty levels - Easy, Medium, and Hard. Each level presents different ranges of numbers and time limits, catering to players with varying levels of experience and skill.
2.	Interactive Gameplay: The game engages players through an interactive console interface. Players input their guesses using the keyboard and receive immediate feedback based on their guesses and the game's rules.
3.	Time Limit: To add a sense of urgency and challenge, each guessing attempt is subject to a time limit. If the player fails to guess the number within the specified time, they lose the game.
4.	Hint System: Depending on the selected difficulty level, players may receive hints to aid them in their guessing. These hints provide directional guidance to help players narrow down their guesses and increase their chances of success.
5.	Play Again Option: Upon completing a game, players are given the option to play again. This feature enhances replayability and encourages continued engagement with the game.
### Technical Implementation:
The game is implemented using Java programming language and utilizes various language features and libraries to achieve its functionality. It employs enums to define the different difficulty levels and encapsulates related properties such as minimum and maximum numbers, time limits, and hint availability.
The core gameplay logic is encapsulated within the guessNumber() method, which handles the generation of secret numbers, user input, feedback generation, and game outcome determination. Additionally, the chooseLevel() method prompts users to select a difficulty level before initiating gameplay.
Concurrency is employed to enforce time limits on user input, ensuring that the game remains responsive and adheres to the specified time constraints. The ExecutorService and Future classes from the java.util.concurrent package facilitate this functionality.
### Concurrency Management:
Concurrency is employed to enforce time limits on user input, ensuring that the game remains responsive and adheres to the specified time constraints. The ExecutorService and Future classes from the java.util.concurrent package facilitate this functionality.
### Input Validation:
The game incorporates input validation to ensure that player guesses are within the specified range for each difficulty level. This prevents invalid guesses and maintains the integrity of the gameplay experience.
### Exception Handling:
Robust exception handling mechanisms are implemented to gracefully manage potential errors or interruptions during gameplay, such as timeout exceptions or unexpected input. This enhances the stability and reliability of the game.
### User Interaction:
The game fosters user engagement through clear prompts, informative feedback, and intuitive gameplay mechanics. This ensures a seamless and enjoyable experience for players of all skill levels.




