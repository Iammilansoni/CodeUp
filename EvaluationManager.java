public class EvaluationManager {
    int totalCorrectAnswers = 0;
    private int totalIncorrectAnswers = 0;
    public void evaluateResponses(int[] userAnswers, String[][][] questionBank, int totalQuestions) {
        for (int i = 0; i < totalQuestions; i++) {
            if (userAnswers[i] == Integer.parseInt(questionBank[i][0][1])) {
                totalCorrectAnswers++;
            } else {
                totalIncorrectAnswers++;
            }
        }
    }

    public double calculatePercentage(int totalQuestions) {
        return ((double) totalCorrectAnswers / totalQuestions) * 100;
    }

    public void showResults(String[][] participantDetails) {
        System.out.println("Your Results:");
        System.out.println("Correct Answers: " + totalCorrectAnswers);
        System.out.println("Incorrect Answers: " + totalIncorrectAnswers);
        double percentage = calculatePercentage(Questions.totalQuestions);
        System.out.println("Your Quiz Score is: " + String.format("%.2f", percentage) + "%");

        // Store the percentage score in the participantDetails array
        participantDetails[0][1] = String.format("%.2f", percentage);  // Store percentage score
    }

    public void resetResults() {
        totalCorrectAnswers = 0;
        totalIncorrectAnswers = 0;
    }

    public void printQuizScore() {
        System.out.println("Your Quiz Score is: " + String.format("%.2f", calculatePercentage(Questions.totalQuestions)) + "%");
    }
}
