// QuestionManager interfaces with the local question pool and the server question pool.
// It relays this data to the game during runtime.

package wingdings.cs262.calvin.edu.pigeonpoll;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager {
    // Stores all the questions which are in the local question pool
    private ArrayList<Question> questions;

    // Stores the questions which the user would actually like to use
    private ArrayList<Question> enabledQuestions;

    // Stores the questions which have been used this game already
    private ArrayList<Question> usedQuestions;

    private Random random;

    // Stores this QuestionManager's instance, to make it a singleton
    public static QuestionManager instance;

    public QuestionManager() {
        questions = new ArrayList<Question>();
        enabledQuestions = new ArrayList<Question>();
        usedQuestions = new ArrayList<Question>();

        random = new Random();

        loadQuestionsFromStorage();
    }

    public static QuestionManager getInstance() {
        if (instance == null) {
            instance = new QuestionManager();
        }
        return instance;
    }

    private void loadQuestionsFromStorage() {
        // TODO: load questions from a file in local storage.

        String[] questionText = {
                "Who is the man?",
                "Who will be dead in 5 years?",
                "Who is the best looking?",
                "Who would be most likely to go to a grocery store and by eight dozen eggs?",
                "Who has the best smile?",
                "Who makes the best food?",
                "Who is most likely to wake up hungover on a cruise ship they didn't buy a ticket for?",
                "Who has the worst style?",
                "Who could find the best deal online?",
                "Who is the most like a hipster?"
        };

        // This is just temp data
        for(int i = 0; i < 10; i++) {
            Question q = new Question(questionText[i], 3);
            questions.add(q);
        }

        enabledQuestions = new ArrayList<Question>(questions);
    }

    // Upload a question to the server
    private void uploadQuestion(Question q) {
        // TODO: interface with server
    }

    // Download a question from the server
    // Index is some way of finding which question it is in the database
    private void downloadQuestion(int index) {
        // TODO: interface with server
    }

    // Returns a question to be used by the game
    // This should only draw from the enabled pool
    public Question getRandomQuestion() {
        // TODO: replace with throw exception
        if (enabledQuestions.size() == 0) {
            int randomIndex = random.nextInt(usedQuestions.size());
            Question selectedQuestion = usedQuestions.get(randomIndex);
            return selectedQuestion;
        }
        int randomIndex = random.nextInt(enabledQuestions.size());
        Question selectedQuestion = enabledQuestions.get(randomIndex);
        usedQuestions.add(enabledQuestions.remove(randomIndex));
        return selectedQuestion;
    }

    public ArrayList<Question> getAllQuestions() {
        return questions;
    }
}
