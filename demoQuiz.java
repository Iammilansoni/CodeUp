import java.util.Scanner;

/**
 * The demoQuiz class represents a collection of demo quiz questions.
 * It contains a static array demoQuestion, which holds questions, options, correct answer, and difficulty level.
 * The class provides a method iterateDemoQuestions to display all the demo questions with their options and correct answer.
 * This class can be used as a fallback for a quiz system in case no custom questions are provided by the admin.
 * Owner: Ajay.
 */
public class demoQuiz {
    public static String[][][] demoQuestion = {
            // Easy Questions
            {
                    {"What is the capital of France?", "1", "easy"},
                    {"Paris"}, {"London"}, {"Berlin"}, {"Rome"}
            },
            {
                    {"How many days are in a week?", "1", "easy"},
                    {"7"}, {"6"}, {"5"}, {"8"}
            },
            {
                    {"What is 5 + 3?", "2", "easy"},
                    {"6"}, {"8"}, {"10"}, {"12"}
            },
            // Medium Questions
            {
                    {"Which planet is known as the Red Planet?", "2", "medium"},
                    {"Venus"}, {"Mars"}, {"Jupiter"}, {"Saturn"}
            },
            {
                    {"What is the boiling point of water?", "2", "medium"},
                    {"90°C"}, {"100°C"}, {"120°C"}, {"80°C"}
            },
            {
                    {"Who wrote 'Hamlet'?", "2", "medium"},
                    {"Charles Dickens"}, {"William Shakespeare"}, {"Mark Twain"}, {"George Orwell"}
            },
            // Hard Questions
            {
                    {"What is the largest ocean on Earth?", "4", "hard"},
                    {"Atlantic Ocean"}, {"Indian Ocean"}, {"Arctic Ocean"}, {"Pacific Ocean"}
            },
            {
                    {"What is the chemical symbol for water?", "1", "hard"},
                    {"H2O"}, {"O2"}, {"CO2"}, {"NaCl"}
            },
            {
                    {"What gas do plants absorb?", "2", "hard"},
                    {"Oxygen"}, {"Carbon Dioxide"}, {"Nitrogen"}, {"Hydrogen"}
            },
            {
                    {"What is the hardest natural substance?", "1", "hard"},
                    {"Diamond"}, {"Iron"}, {"Gold"}, {"Quartz"}
            },
            {
                    {"What is the capital of Japan?", "3", "hard"},
                    {"Seoul"}, {"Beijing"}, {"Tokyo"}, {"Bangkok"}
            },
            {
                    {"What is 12 divided by 4?", "1", "hard"},
                    {"3"}, {"2"}, {"4"}, {"6"}
            },
            {
                    {"What is 9 - 4?", "1", "hard"},
                    {"5"}, {"6"}, {"3"}, {"2"}
            }
    };

    /**
     * Method to get user-selected difficulty level.
     * 
     * @return The selected difficulty level.
     */
    public static String getDifficultyLevel() {
        try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Select difficulty level (easy, medium, hard): ");
                String difficulty = scanner.nextLine().toLowerCase();
                
                // Validate input
                while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")) {
                    System.out.println("Invalid choice. Please select easy, medium, or hard:");
                    difficulty = scanner.nextLine().toLowerCase();
                }
                
                return difficulty;
        }
    }

    /**
     * Method to iterate over and display each demo question based on the selected difficulty.
     *
     * @param demoQuestion The array containing demo questions.
     * @param difficulty The selected difficulty level.
     */
    public static void iterateDemoQuestions(String[][][] demoQuestion, String difficulty) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;  // Track the score of the user
        int totalQuestions = 0;

        for (String[][] question : demoQuestion) {
            if (question[0][2].equals(difficulty)) { // Check if question matches selected difficulty
                totalQuestions++;
                System.out.println("Question " + totalQuestions + ": " + question[0][0]);
                System.out.println("Options:");
                for (int j = 1; j <= 4; j++) {
                    System.out.println("  " + j + ". " + question[j][0]);
                }
                System.out.print("Enter your answer (1-4): ");
                String userAnswer = scanner.nextLine();

                // Check if the user's answer is correct
                if (userAnswer.equals(question[0][1])) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer was: " + question[Integer.parseInt(question[0][1])][0] + "\n");
                }
            }
        }
        scanner.close();

        // Display the final score
        System.out.println("Quiz finished! Your score: " + score + "/" + totalQuestions);
    }

    public static void main(String[] args) {
        String selectedDifficulty = getDifficultyLevel();
        iterateDemoQuestions(demoQuestion, selectedDifficulty);
    }
}
