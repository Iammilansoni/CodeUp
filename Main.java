import java.util.Scanner;

public class Main {

    static Questions quizDatabase = new Questions();
    public static QuizUp quiz = new QuizUp();
    public static EvaluationManager evaluation = new EvaluationManager();

    private static String getUserInput() {
        System.out.println("Do you want to Attempt the Quiz or Create Quiz?");
        System.out.println("Type 'attempt' to Attempt Quiz");
        System.out.println("Type 'create' to Create Quiz");
        System.out.println("Type 'exit' to Exit Program");

        return ((Scanner) Inputs.scanner).nextLine().toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Quiz Program");
		String userInput = getUserInput();
        boolean attemptLater = false;
        while (!userInput.equalsIgnoreCase("exit")) {
            switch (userInput) {
                case "create": {
                    Questions.populateQuestionBank();
                    System.out.println("Questions created successfully.");
					String attemptNow = "";
					while (attemptNow.isEmpty()) {
						System.out.println("Do you want to attempt the quiz now? (yes/no)");
						attemptNow = Inputs.scanner.nextLine().toLowerCase();
						if (attemptNow.isEmpty() || (!attemptNow.equalsIgnoreCase("yes") && !attemptNow.equalsIgnoreCase("no"))) {
							System.out.println("Invalid Input!!.. Try again..");
							attemptNow = "";
						}
					}

                    if (attemptNow.equalsIgnoreCase("yes")) {
                        QuizUp.initiateQuiz();
						return;
                    } else {
                        attemptLater = true;
						break;
                    }   
                }

                case "attempt": {
                    QuizUp.initiateQuiz();
                    return;
                }

                case "exit": {
                    System.out.println("Exiting the Quiz...");
                    return; 
                }

                default: {
                    System.out.println("Invalid input - Please enter 'attempt', 'create', or 'exit'.");
					userInput = getUserInput();
                }
			}

			if (attemptLater) {
			System.out.println("You opted to attempt the quiz later. Do you want to attempt it now? (yes/no)");
			String attemptNow = Inputs.scanner.nextLine().toLowerCase();
			if (attemptNow.equalsIgnoreCase("yes")) {
				QuizUp.initiateQuiz();
				attemptLater = false; 
			} else {
				attemptLater = false; 
			}
			}
		break;
        }
    }
}

