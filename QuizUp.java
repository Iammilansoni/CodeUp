import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class QuizUp {
    private static EvaluationManager evaluationManager = new EvaluationManager();
    private static Scanner inputScanner = new Scanner(System.in);
    private static String[][] participantDetails;
    private static String[][] allParticipantDetails;

    public static void main(String[] args) {
        initiateQuiz();
    }

    public static void initiateQuiz() {
        boolean continueQuiz = true;

        while (continueQuiz) {
            System.out.println("=====================================");
            System.out.println("     Welcome to the Quiz Application ");
            System.out.println("=====================================");
            System.out.println("Enter 'start' to begin the quiz or 'exit' to quit.");
            String userChoice = inputScanner.nextLine();

            if (userChoice.equalsIgnoreCase("exit")) {
                break;
            }

            if (!userChoice.equalsIgnoreCase("start")) {
                continue;
            }

            System.out.print("Enter the number of participants: ");
            String noOfParticipants = inputScanner.nextLine();

            // Check if there are any questions in the quiz database
            if (Questions.totalQuestions == 0 || Questions.questionBank == null) {
                System.out.println("No custom questions available. Using demo questions...");
                Questions.questionBank = demoQuiz.demoQuestion;
                Questions.totalQuestions = demoQuiz.demoQuestion.length;
            }

            // Validate the number of participants
            while (!isValidInt(noOfParticipants)) {
                System.out.println("Invalid input, please enter a valid number.");
                System.out.print("Enter the number of participants: ");
                noOfParticipants = inputScanner.nextLine();
            }

            int noOfParticipantsInt = Integer.parseInt(noOfParticipants);
            allParticipantDetails = new String[noOfParticipantsInt][2];

            // Quiz for each participant
            for (int i = 0; i < noOfParticipantsInt; i++) {
                participantDetails = new String[1][2];
                System.out.print("\nEnter participant " + (i + 1) + "'s name: ");
                participantDetails[0][0] = inputScanner.nextLine();
                participantDetails[0][1] = "";
                presentQuestions(participantDetails[0][0]);
                evaluationManager.evaluateResponses(Questions.userAnswers, Questions.questionBank, Questions.totalQuestions);
                participantDetails[0][1] = String.valueOf(evaluationManager.calculatePercentage(Questions.totalQuestions));
                allParticipantDetails[i][0] = participantDetails[0][0];
                allParticipantDetails[i][1] = participantDetails[0][1];
                evaluationManager.resetResults();
            }

            // Shuffle questions for the next quiz
            shuffleQuestions();

            // Custom sort participants by percentage score
            sortParticipantsByScore(allParticipantDetails);

            // Display sorted participants and their scores
            System.out.println("\n=====================================");
            System.out.println("   Participant Scores (Ascending Order) ");
            System.out.println("=====================================");
            for (String[] participant : allParticipantDetails) {
                System.out.printf("%-10s - %s%%\n", participant[0], participant[1]);
            }

            // Save results to file
            saveResultsToFile(allParticipantDetails);

            // Display a formatted star graph of scores
            System.out.println("\n=====================================");
            System.out.println("   Graph of Participant Scores ");
            System.out.println("=====================================");
            displayStarGraph(allParticipantDetails);

            // Ask user if they want to retry the quiz
            System.out.print("Would you like to take the quiz again? (yes/no): ");
            String retryChoice = inputScanner.nextLine();
            if (!retryChoice.equalsIgnoreCase("yes")) {
                continueQuiz = false;
            }
        }

        System.out.println("Thank you for participating!");
    }

    private static void presentQuestions(String participantName) {
        Questions.userAnswers = new int[Questions.totalQuestions];
        System.out.println("\nStarting the quiz for " + participantName + "...");
        for (int i = 0; i < Questions.totalQuestions; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + Questions.questionBank[i][0][0]);
            System.out.println("Options:");
            for (int j = 1; j <= 4; j++) {
                System.out.println("Option " + j + ": " + Questions.questionBank[i][j][0]);
            }

            boolean validInput = false;
            String answer = "";
            while (!validInput) {
                System.out.print("Enter your answer (1, 2, 3, 4): ");
                answer = inputScanner.nextLine();

                if (isValidInt(answer) && Integer.parseInt(answer) >= 1 && Integer.parseInt(answer) <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input, please enter a number between 1 and 4.");
                }
            }

            Questions.userAnswers[i] = Integer.parseInt(answer);
        }

        System.out.println("All questions answered for " + participantName + ".");
    }

    // Shuffle questions using Fisher-Yates algorithm
    private static void shuffleQuestions() {
        for (int i = Questions.totalQuestions - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1)); // Random index between 0 and i
            String[][] temp = Questions.questionBank[i];
            Questions.questionBank[i] = Questions.questionBank[j];
            Questions.questionBank[j] = temp;
        }
    }

    // Save results to file
    private static void saveResultsToFile(String[][] participants) {
        try {
            FileWriter fileWriter = new FileWriter("quiz_results.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);
            for (String[] participant : participants) {
                writer.printf("%s - %s%%\n", participant[0], participant[1]);
            }
            writer.close();
            System.out.println("Results saved to quiz_results.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving results: " + e.getMessage());
        }
    }

    // Custom sort method to sort participants by their scores
    private static void sortParticipantsByScore(String[][] participants) {
        for (int i = 0; i < participants.length - 1; i++) {
            for (int j = i + 1; j < participants.length; j++) {
                if (Double.parseDouble(participants[i][1]) > Double.parseDouble(participants[j][1])) {
                    String[] temp = participants[i];
                    participants[i] = participants[j];
                    participants[j] = temp;
                }
            }
        }
    }

    // Display star graph based on percentage scores
    private static void displayStarGraph(String[][] participants) {
        // Maximum length of stars to represent 100%
        int maxStars = 50;

        // Find the maximum percentage score
        double maxPercentage = findMaxPercentage(participants);

        for (String[] participant : participants) {
            String name = participant[0];
            double percentage = Double.parseDouble(participant[1]);

            // Calculate star length for each participant
            int starLength = (int) ((percentage / maxPercentage) * maxStars);

            // Print formatted participant name and graph
            System.out.printf("%-10s: ", name);
            for (int i = 0; i < starLength; i++) {
                System.out.print("*");
            }
            System.out.printf(" (%.2f%%)\n", percentage);
        }
    }

    // Find the maximum percentage score to scale the graph
    private static double findMaxPercentage(String[][] participants) {
        double max = Double.parseDouble(participants[0][1]);
        for (int i = 1; i < participants.length; i++) {
            double score = Double.parseDouble(participants[i][1]);
            if (score > max) {
                max = score;
            }
        }
        return max;
    }

    // Validate integer input without using built-in methods
    private static boolean isValidInt(String input) {
        if (input == null || input.isEmpty()) return false;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
