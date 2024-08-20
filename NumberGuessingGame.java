import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("Welcome to the Number Guessing Game!");
        int rounds = 0;
        int totalAttempts = 0;
        String playAgain = "yes";
        
        while (playAgain.equalsIgnoreCase("yes")) {
            rounds++;
            int attempts = 0;
            int maxAttempts = 10;
            int numberToGuess = random.nextInt(100) + 1;
            
            System.out.println("\nRound " + rounds + ":");
            System.out.println("Guess the number between 1 and 100. You have " + maxAttempts + " attempts.");
            
            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                try {
                    int guess = Integer.parseInt(scanner.nextLine());
                    attempts++;
                    totalAttempts++;

                    if (guess < numberToGuess) {
                        System.out.println("Too low! Try again.");
                    } else if (guess > numberToGuess) {
                        System.out.println("Too high! Try again.");
                    } else {
                        System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                }
            }
            
            if (attempts == maxAttempts) {
                System.out.println("Sorry, you've used all " + maxAttempts + " attempts. The number was " + numberToGuess + ".");
            }
            
            System.out.print("Do you want to play another round? (yes/no): ");
            playAgain = scanner.nextLine();
        }
        
        System.out.println("\nGame Over! You played " + rounds + " rounds with a total of " + totalAttempts + " attempts.");
        System.out.printf("Your average attempts per round: %.2f%n", (double) totalAttempts / rounds);
        
        scanner.close();
    }
}
