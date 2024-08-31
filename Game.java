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
    }

    boolean guessNumber(final Level var1) throws InterruptedException {
        int var2 = (new Random()).nextInt(var1.max - var1.min + 1) + var1.min;

        while(true) {
            ExecutorService var3 = Executors.newSingleThreadExecutor();
            Future var4 = var3.submit(new Callable<Integer>(this) {
                public Integer call() throws Exception {
                    Scanner var1x = new Scanner(System.in);
                    System.out.printf("Take a guess (between %d and %d): ", var1.min, var1.max);
                    return var1x.nextInt();
                }
            });

            try {
                int var5 = (Integer)var4.get((long)var1.timeLimit, TimeUnit.SECONDS);
                if (var5 < var1.min || var5 > var1.max) {
                    System.out.println("Your guess is out of range. The secret number was " + var2 + ". Please guess a number between " + var1.min + " and " + var1.max + " in your next turn.");
                    System.exit(0);
                }

                if (var5 == var2) {
                    System.out.println("Congratulations! You guessed the number.");
                    boolean var6 = true;
                    return var6;
                }

                if (var1.hints) {
                    if (Math.abs(var5 - var2) <= 2) {
                        System.out.println("Near to the number! Try again.");
                    } else if (Math.abs(var5 - var2) <= 5) {
                        if (var5 < var2) {
                            System.out.println("Low! Try again.");
                        } else {
                            System.out.println("High! Try again.");
                        }
                    } else if (var5 < var2) {
                        System.out.println("Too low! Try again.");
                    } else {
                        System.out.println("Too high! Try again.");
                    }
                } else {
                    System.out.println("Try again.");
                }
            } catch (TimeoutException var11) {
                System.out.println("Time's up! You didn't guess in time. The secret number was " + var2 + ".");
                System.exit(0);
            } catch (ExecutionException var12) {
                var12.printStackTrace();
            } finally {
                var3.shutdownNow();
            }
        }
    }

    Level chooseLevel() {
        Scanner var1 = new Scanner(System.in);
        printWithTypingEffect("Choose a level: 1 for EASY, 2 for MEDIUM, 3 for HARD");
        int var2 = var1.nextInt();
        switch (var2) {
            case 1 -> return Game.Level.EASY;
            case 2 -> return Game.Level.MEDIUM;
            case 3 -> return Game.Level.HARD;
            default -> return Game.Level.EASY;
        }
    }

    public static void main(String[] var0) {
        String var1 = "NumberWizard";
        String var2 = "SHANTANU PRADHAN";
        printWithTypingEffect("Welcome to " + var1 + "!");
        System.out.println();
        printWithTypingEffect("Designed by: " + var2);
        printWithTypingEffect("CSE undergrad at SRM-IST");
        System.out.println();
        printWithTypingEffect("Rules:");
        printWithTypingEffect("1. You have to guess a secret number.");
        printWithTypingEffect("2. The secret number is between a range.");
        printWithTypingEffect("3. You have to guess the number in a time limit.");
        printWithTypingEffect("   Time limits for each level:");
        printWithTypingEffect("   EASY: 15 seconds");
        printWithTypingEffect("   MEDIUM: 12 seconds");
        printWithTypingEffect("   HARD: 10 seconds");
        printWithTypingEffect("4. You will get hints if you are near to the number.");
        System.out.println();
        printWithTypingEffect("Press any key to start the game...");
        Scanner var3 = new Scanner(System.in);
        var3.nextLine();
        Game var4 = new Game();
        boolean var5 = true;

        while(var5) {
            Level var6 = var4.chooseLevel();

            try {
                boolean var7 = var4.guessNumber(var6);
                if (var7) {
                    printWithTypingEffect("Do you want to play again? (yes/no)");
                    Scanner var8 = new Scanner(System.in);
                    String var9 = var8.nextLine();
                    var5 = var9.equalsIgnoreCase("yes");
                } else {
                    var5 = false;
                }
            } catch (InterruptedException var10) {
                var10.printStackTrace();
            }
        }

    }

    private static void printWithTypingEffect(String var0) {
        char[] var1 = var0.toCharArray();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            char var4 = var1[var3];
            System.out.print(var4);

            try {
                Thread.sleep(60L);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }
        }

        System.out.println();
    }

    static enum Level {
        EASY(1, 50, 15, true),
        MEDIUM(1, 100, 12, true),
        HARD(1, 500, 10, true);

        final int min;
        final int max;
        final int timeLimit;
        final boolean hints;

        private Level(int var3, int var4, int var5, boolean var6) {
            this.min = var3;
            this.max = var4;
            this.timeLimit = var5;
            this.hints = var6;
        }
    }
}
