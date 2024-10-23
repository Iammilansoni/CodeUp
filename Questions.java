import java.util.Scanner;

public class Questions {
    static Inputs getInput = new Inputs();
    public static int totalQuestions;
    public static String[][][] questionBank;
    public static String[][][] quizQuestions;
    public static int[] userAnswers;

    public static void populateQuestionBank() {
        totalQuestions = getValidIntInput("Enter the total number of questions: ");
        questionBank = new String[totalQuestions][5][3]; // Updated to store difficulty level

        for (int i = 0; i < totalQuestions; i++) {
            questionBank[i][0][0] = getUniqueQuestion(i);
            for (int j = 1; j <= 4; j++) {
                questionBank[i][j][0] = getUniqueOption(i, j);
            }
            questionBank[i][0][1] = getValidAnswer();
            questionBank[i][0][2] = getValidDifficulty();
            confirmQuestionDetails(i);
        }
    }

    private static String getUniqueQuestion(int index) {
        String question;
        do {
            System.out.println("Enter question " + (index + 1) + ": ");
            question = getInput.scanner.nextLine();
            if (isQuestionDuplicate(question, index)) {
                System.out.println("Question already exists. Please enter a unique question.");
            }
        } while (isQuestionDuplicate(question, index) || question.isEmpty());
        return question;
    }

    private static boolean isQuestionDuplicate(String question, int currentIndex) {
        for (int m = 0; m < currentIndex; m++) {
            if (questionBank[m][0][0].equals(question)) {
                return true;
            }
        }
        return false;
    }

    private static String getUniqueOption(int questionIndex, int optionIndex) {
        String option;
        do {
            System.out.print("Enter Option " + optionIndex + ": ");
            option = getInput.scanner.nextLine();
            if (isOptionDuplicate(questionIndex, option, optionIndex)) {
                System.out.println("Option already exists. Please enter a unique option.");
            }
        } while (isOptionDuplicate(questionIndex, option, optionIndex) || option.isEmpty());
        return option;
    }

    private static boolean isOptionDuplicate(int questionIndex, String option, int currentOptionIndex) {
        for (int k = 1; k < currentOptionIndex; k++) {
            if (questionBank[questionIndex][k][0].equals(option)) {
                return true;
            }
        }
        return false;
    }

    private static String getValidAnswer() {
        return String.valueOf(getValidIntInput("Enter the correct answer option (1, 2, 3, 4): ", 1, 4));
    }

    private static String getValidDifficulty() {
        String difficulty;
        do {
            System.out.print("Enter difficulty level (easy, medium, hard): ");
            difficulty = getInput.scanner.nextLine().toLowerCase();
        } while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard"));
        return difficulty;
    }

    private static void confirmQuestionDetails(int index) {
        System.out.println("\nQuestion Summary:");
        System.out.println("Question: " + questionBank[index][0][0]);
        System.out.println("Options:");
        for (int j = 1; j <= 4; j++) {
            System.out.println("Option " + j + ": " + questionBank[index][j][0]);
        }
        System.out.println("Correct Answer: " + questionBank[index][0][1]);
        System.out.println("Difficulty Level: " + questionBank[index][0][2]);
        System.out.println("Do you want to edit the question, options, or answer? (YES/NO): ");
        String choice = getInput.scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            index--; // Decrement to redo the current question
        }
    }

    public static void selectQuestion() {
        int quizQuestionsCount = getValidIntInput("Enter the number of questions for the quiz: ");
        boolean[] selectedQuestions = new boolean[totalQuestions];
        String difficultyFilter = getDifficultyFilter();

        quizQuestions = new String[quizQuestionsCount][5][2];

        System.out.println("List of questions:");
        showQuestions(questionBank);

        for (int i = 0; i < quizQuestionsCount; i++) {
            int questionChoice;
            while (true) {
                questionChoice = getValidIntInput("Select Question " + (i + 1) + " (Enter Question Number): ") - 1;
                if (isQuestionValidForSelection(selectedQuestions, difficultyFilter, questionChoice)) {
                    quizQuestions[i][0][0] = questionBank[questionChoice][0][0];
                    quizQuestions[i][0][1] = questionBank[questionChoice][0][1];
                    for (int j = 1; j <= 4; j++) {
                        quizQuestions[i][j][0] = questionBank[questionChoice][j][0];
                    }
                    selectedQuestions[questionChoice] = true;
                    break;
                }
            }
        }

        System.out.println("\nQuiz Questions Selected:");
        showQuestions(quizQuestions);
        userAnswers = new int[quizQuestionsCount];
        takeQuiz(quizQuestions);
    }

    private static void showQuestions(String[][][] quizQuestions2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showQuestions'");
    }

    private static String getDifficultyFilter() {
        System.out.print("Do you want to filter questions by difficulty? (yes/no): ");
        String filterChoice = getInput.scanner.nextLine().toLowerCase();
        if (filterChoice.equals("yes")) {
            return getValidDifficulty();
        }
        return null;
    }

    private static boolean isQuestionValidForSelection(boolean[] selectedQuestions, String difficultyFilter, int questionChoice) {
        if (questionChoice < 0 || questionChoice >= totalQuestions) {
            System.out.println("Invalid question number! Choose a number between 1 and " + totalQuestions);
            return false;
        }
        if (selectedQuestions[questionChoice]) {
            System.out.println("This question has already been selected. Please choose another question.");
            return false;
        }
        if (difficultyFilter != null && !questionBank[questionChoice][0][2].equals(difficultyFilter)) {
            System.out.println("This question does not match the selected difficulty level. Please choose another question.");
            return false;
        }
        return true;
    }

    public static void takeQuiz(String[][][] input) {
        System.out.println("\nStart the Quiz!");
        for (int i = 0; i < input.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + input[i][0][0]);
            System.out.println("Options:");
            for (int j = 1; j <= 4; j++) {
                System.out.println("Option " + j + ": " + input[i][j][0]);
            }
            userAnswers[i] = getValidIntInput("Your answer (1-4): ", 1, 4);
        }
        evaluateQuiz(input);
    }

    public static void evaluateQuiz(String[][][] input) {
        int score = 0;
        String[][] results = new String[input.length][3];

        for (int i = 0; i < input.length; i++) {
            results[i][0] = input[i][0][0]; // Question
            results[i][1] = String.valueOf(userAnswers[i]); // User Answer
            results[i][2] = input[i][0][1]; // Correct Answer

            if (userAnswers[i] == Integer.parseInt(input[i][0][1])) {
                score++;
            }
        }

        displayResults(results, score);
    }

    private static void displayResults(String[][] results, int score) {
        System.out.println("\nQuiz Results:");
        System.out.println("------------------------------------------------");
        System.out.println("| Question                      | Your Answer | Correct Answer |");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < results.length; i++) {
            String question = results[i][0];
            String userAnswer = results[i][1];
            String correctAnswer = results[i][2];

            // Format output to fit within a specific width
            String formattedQuestion = formatString(question, 30);
            String formattedUserAnswer = formatString(userAnswer, 15);
            String formattedCorrectAnswer = formatString(correctAnswer, 15);

            System.out.println("| " + formattedQuestion + " | " + formattedUserAnswer + " | " + formattedCorrectAnswer + " |");
        }

        System.out.println("------------------------------------------------");
        System.out.println("Your Score: " + score + "/" + results.length);
        displayOrderedQuestions(results);
    }

    private static void displayOrderedQuestions(String[][] results) {
        System.out.println("\nQuestions Ordered by Difficulty:");
        String[][] easyQuestions = new String[results.length][3];
        String[][] mediumQuestions = new String[results.length][3];
        String[][] hardQuestions = new String[results.length][3];
        int easyCount = 0, mediumCount = 0, hardCount = 0;

        for (int i = 0; i < results.length; i++) {
            String difficulty = questionBank[i][0][2]; // Fetch difficulty from question bank
            if (difficulty.equals("easy")) {
                easyQuestions[easyCount++] = results[i];
            } else if (difficulty.equals("medium")) {
                mediumQuestions[mediumCount++] = results[i];
            } else if (difficulty.equals("hard")) {
                hardQuestions[hardCount++] = results[i];
            }
        }

        printOrderedQuestions("Easy", easyQuestions, easyCount);
        printOrderedQuestions("Medium", mediumQuestions, mediumCount);
        printOrderedQuestions("Hard", hardQuestions, hardCount);
    }

    private static void printOrderedQuestions(String difficulty, String[][] questions, int count) {
        if (count > 0) {
            System.out.println(difficulty + " Questions:");
            for (int i = 0; i < count; i++) {
                System.out.println("- " + questions[i][0]);
            }
        }
    }

    private static String formatString(String str, int width) {
        StringBuilder sb = new StringBuilder(str);
        if (sb.length() < width) {
            for (int i = sb.length(); i < width; i++) {
                sb.append(" ");
            }
        } else if (sb.length() > width) {
            sb.setLength(width); // Truncate if too long
        }
        return sb.toString();
    }

    private static int getValidIntInput(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            String str = getInput.scanner.nextLine();
            if (isNumeric(str)) {
                input = Integer.parseInt(str);
                return input;
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }
    }

    private static int getValidIntInput(String prompt, int min, int max) {
        int input;
        while (true) {
            input = getValidIntInput(prompt);
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("Input out of range! Please enter a number between " + min + " and " + max + ".");
            }
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        populateQuestionBank();
        selectQuestion();
    }

    public static boolean isValidInt(String answer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidInt'");
    }
}

class Input {
    Scanner scanner = new Scanner(System.in);
}
