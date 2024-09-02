import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Game {
    public Game() {
        // Constructor body can be empty or contain initialization code
    }

    static class GameResult {
        boolean won;
        int secretNumber;
        boolean timedOut;

        GameResult(boolean won, int secretNumber, boolean timedOut) {
            this.won = won;
            this.secretNumber = secretNumber;
            this.timedOut = timedOut;
        }
    }

    GameResult guessNumber(final Level level, Scanner scanner) throws InterruptedException {
        int secretNumber = (new Random()).nextInt(level.max - level.min + 1) + level.min;

        while (true) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Integer> future = executor.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    System.out.printf("Take a guess (between %d and %d): ", level.min, level.max);
                    return scanner.nextInt();
                }
            });

            try {
                int guess = future.get(level.timeLimit, TimeUnit.SECONDS);
                if (guess < level.min || guess > level.max) {
                    System.out.println("Your guess is out of range. The secret number was " + secretNumber + ". Please guess a number between " + level.min + " and " + level.max + " in your next turn.");
                    return new GameResult(false, secretNumber, false);
                }

                if (guess == secretNumber) {
                    System.out.println("Congratulations! You guessed the number.");
                    return new GameResult(true, secretNumber, false);
                }

                if (level.hints) {
                    int difference = Math.abs(guess - secretNumber);
                    int range = level.max - level.min;

                    if (level == Level.EASY) {
                        if (difference <= 1) {
                            System.out.println("Very close! Try again.");
                        } else if (difference <= 2) {
                            System.out.println("Close! Try again.");
                        } else if (difference <= 5) {
                            if (guess < secretNumber) {
                                System.out.println("A bit low! Try again.");
                            } else {
                                System.out.println("A bit high! Try again.");
                            }
                        } else if (difference > 4) {
                            if (guess < secretNumber) {
                                System.out.println("Too low! Try again.");
                            } else {
                                System.out.println("Too high! Try again.");
                            }
                        } else {
                            if (guess < secretNumber) {
                                System.out.println("Low! Try again.");
                            } else {
                                System.out.println("High! Try again.");
                            }
                        }
                    } else if (level == Level.MEDIUM) {
                        if (difference <= 2) {
                            System.out.println("Very close! Try again.");
                        } else if (difference <= 5) {
                            System.out.println("Close! Try again.");
                        } else if (difference <= 10) {
                            if (guess < secretNumber) {
                                System.out.println("A bit low! Try again.");
                            } else {
                                System.out.println("A bit high! Try again.");
                            }
                        } else if (difference > 8) {
                            if (guess < secretNumber) {
                                System.out.println("Too low! Try again.");
                            } else {
                                System.out.println("Too high! Try again.");
                            }
                        } else {
                            if (guess < secretNumber) {
                                System.out.println("Low! Try again.");
                            } else {
                                System.out.println("High! Try again.");
                            }
                        }
                    } else if (level == Level.HARD) {
                        if (difference <= range * 0.005) { // within 0.5% of the range
                            System.out.println("Very close! Try again.");
                        } else if (difference <= range * 0.01) { // within 1% of the range
                            System.out.println("Close! Try again.");
                        } else if (difference <= range * 0.025) { // within 2.5% of the range
                            if (guess < secretNumber) {
                                System.out.println("A bit low! Try again.");
                            } else {
                                System.out.println("A bit high! Try again.");
                            }
                        } else if (difference > range * 0.08) { // more than 8% of the range
                            if (guess < secretNumber) {
                                System.out.println("Too low! Try again.");
                            } else {
                                System.out.println("Too high! Try again.");
                            }
                        } else {
                            if (guess < secretNumber) {
                                System.out.println("Low! Try again.");
                            } else {
                                System.out.println("High! Try again.");
                            }
                        }
                    }
                } else {
                    System.out.println("Try again.");
                }
            } catch (TimeoutException e) {
                System.out.println();
                System.out.println("Time's up! You didn't guess in time. The secret number was " + secretNumber + ".");
                System.exit(0); // Exit the program when time is up
            } catch (ExecutionException e) {
                System.out.println("An error occurred while processing your guess.");
                e.printStackTrace();
                return new GameResult(false, secretNumber, false);
            } finally {
                executor.shutdownNow();
            }
        }
    }

    Level chooseLevel(Scanner scanner) {
        printWithTypingEffect("Choose a level: 1 for EASY, 2 for MEDIUM, 3 for HARD");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()
        switch (choice) {
            case 1:
                return Game.Level.EASY;
            case 2:
                return Game.Level.MEDIUM;
            case 3:
                return Game.Level.HARD;
            default:
                return Game.Level.EASY;
        }
    }

    public static void main(String[] args) {
        String gameName = "NumberWizard";
        String designerName = "SHANTANU PRADHAN";
        printWithTypingEffect("Welcome to " + gameName + "!");
        System.out.println();
        printWithTypingEffect("Designed by: " + designerName);
        printWithTypingEffect("CSE undergrad at SRM-IST");
        System.out.println();
        printWithTypingEffect("Rules:");
        printWithTypingEffect("1. You have to guess a secret number.");
        printWithTypingEffect("2. The secret number is between a range.");
        printWithTypingEffect("3. You have to guess the number in a time limit.");
        printWithTypingEffect("   Time limits for each level:");
        printWithTypingEffect("   EASY: " + Level.EASY.timeLimit + " seconds");
        printWithTypingEffect("   MEDIUM: " + Level.MEDIUM.timeLimit + " seconds");
        printWithTypingEffect("   HARD: " + Level.HARD.timeLimit + " seconds");
        printWithTypingEffect("4. You will get hints if you are near to the number.");
        System.out.println();
        printWithTypingEffect("Press any key to start the game...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        Game game = new Game();
        boolean playAgain = true;

        while (playAgain) {
            Level level = game.chooseLevel(scanner);

            try {
                GameResult result = game.guessNumber(level, scanner);
                if (!result.won && !result.timedOut) {
                    System.out.println("You didn't guess the number. The secret number was " + result.secretNumber + ".");
                }
            } catch (InterruptedException e) {
                System.out.println("An error occurred during the game.");
                e.printStackTrace();
            }

            printWithTypingEffect("Do you want to play again? (yes/no)");
            scanner.nextLine(); // Consume the newline character left by previous input
            String response = scanner.nextLine();
            playAgain = response.equalsIgnoreCase("yes");
        }
    }

    private static void printWithTypingEffect(String message) {
        char[] chars = message.toCharArray();
        for (char c : chars) {
            System.out.print(c);
            try {
                Thread.sleep(60L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    static enum Level {
        EASY(1, 50, 10, true),
        MEDIUM(1, 100, 7, true),
        HARD(1, 500, 5, true);

        final int min;
        final int max;
        final int timeLimit;
        final boolean hints;

        private Level(int min, int max, int timeLimit, boolean hints) {
            this.min = min;
            this.max = max;
            this.timeLimit = timeLimit;
            this.hints = hints;
        }
    }
}
