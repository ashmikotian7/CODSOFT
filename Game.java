import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Number Guessing Game!");

        boolean playAgain = true;
        int totalAttempts = 0;
        int roundsWon = 0;

        while (playAgain) {
            int minRange = 1;
            int maxRange = 100;
            int attempts = 5; 
            int score = 0; 

            System.out.println("Enter a secret number between " + minRange + " and " + maxRange + ":");
            int secretNumber = getIntInput(scanner, minRange, maxRange);

            System.out.println("I have selected a number between " + minRange + " and " + maxRange + ". You have " + attempts + " attempts to guess it.");

            boolean guessedCorrectly = false;

            for (int guessCount = 1; guessCount <= attempts; guessCount++) {99
                System.out.print("Attempt " + guessCount + ": Enter your guess: ");
                int userGuess = getIntInput(scanner, minRange, maxRange);
                totalAttempts++;

                if (userGuess == secretNumber) {
                    System.out.println("Congratulations! You've guessed the correct number in " + guessCount + " attempts.");
                    score++;
                    roundsWon++;
                    guessedCorrectly = true;
                    break;
                } else if (Math.abs(userGuess - secretNumber) <= 5) {
                    System.out.println("You're very close to the secret number! Keep going!");
                } else if (userGuess < secretNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you've used all your attempts. The correct number was: " + secretNumber);
            }

            System.out.println("Your current score for this round: " + score);

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainInput = scanner.next().toLowerCase();
            playAgain = playAgainInput.equals("yes") || playAgainInput.equals("y");
        }

        System.out.println("Thank you for playing!");
        System.out.println("Total attempts made: " + totalAttempts);
        System.out.println("Rounds won: " + roundsWon);
        scanner.close();
    }

    private static int getIntInput(Scanner scanner, int min, int max) {
        int input;
        while (true) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ":");
                }
            } else {
                System.out.println("Please enter a valid integer:");
                scanner.next(); 
            }
        }
        return input;
    }
}
