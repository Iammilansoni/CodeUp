public class getTotalQuestion {
    public static void getQuestion(String[][][] quizQuestion, String[][][] getQuestion) {

        quizQuestion = new String[getQuestion.length][getQuestion[0].length][getQuestion[0][0].length];

        for (int i = 0; i < getQuestion.length; i++) {
            quizQuestion[i][0][0] = getQuestion[i][0][0];
            for (int j = 1; j <= 4; j++) {
                quizQuestion[i][j][0] = getQuestion[i][j][0]; 
            }
            quizQuestion[i][0][1] = getQuestion[i][0][1]; 
        }
    }
}