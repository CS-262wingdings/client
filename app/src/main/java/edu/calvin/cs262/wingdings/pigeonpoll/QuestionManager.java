// QuestionManager interfaces with the local question pool and the server question pool.
// It relays this data to the game during runtime.

package edu.calvin.cs262.wingdings.pigeonpoll;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager {
    // Stores all the questions which are in the local question pool
    private ArrayList<Question> questions;

    // Stores the questions which the user would actually like to use
    private ArrayList<Question> enabledQuestions;

    // Stores the questions which have been used this game already
    private ArrayList<Question> usedQuestions;

    // Stores the questions which have actually been asked
    private ArrayList<Question> askedQuestions;

    private Random random;

    // Stores this QuestionManager's instance, to make it a singleton
    public static QuestionManager instance;

    public QuestionManager() {
        questions = new ArrayList<Question>();
        enabledQuestions = new ArrayList<Question>();
        usedQuestions = new ArrayList<Question>();
        askedQuestions = new ArrayList<Question>();

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
                "Who is most likely to sell off all their belongings to charity and become a monk in Nepal?",
                "Who will be dead in 5 years?",
                "Who would you rather be stranded on a desert island with?",
                "Who would be most likely to go to a grocery store and buy eight dozen eggs?",
                "Who has the best smile?",
                "Who makes the best food?",
                "Who is most likely to wake up hungover on a cruise ship they didn't buy a ticket for?",
                "If you had to wear someone else's eyebrows as a mustache, whose eyebrows would you choose?",
                "Who could find the best deal online?",
                "Who is probably on an FBI watchlist?",
                "Who would make a good guest appearance on Ellen?",
                "Who is the most responsible?",
                "Which person is secretly an alien?",
                "Who would die first in a horror movie?",
                "Who has the worst luck?",
                "Who is the least responsible person?",
                "Who would you trust with your darkest secrets?",
                "Which person deserves to win the lottery?"
        };

        // This is just temp data
        for(int i = 0; i < questionText.length; i++) {
            Question q = new Question(questionText[i]);
            questions.add(q);
        }

        enabledQuestions = new ArrayList<Question>(questions);
    }

    public void addQuestion(String text, boolean local) {
        addQuestionLocally(new Question(text));
        if (!local) {
            uploadQuestion(new Question(text));
        }
    }

    private void addQuestionLocally(Question q) {
        questions.add(q);
        enabledQuestions.add(q);
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

    public boolean isQuestionDownloaded(Question q) {
        return questions.contains(q);
    }

    // Returns a question to be used by the game
    // This should only draw from the enabled pool
    Question getRandomQuestion() {
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

    void askQuestion( Question q) {
        askedQuestions.add(q);
    }

    public ArrayList<Question> getAskedQuestions() {
        return askedQuestions;
    }

    public ArrayList<Question> fakeGetOnlineQuestions() {
        ArrayList<Question> ret = new ArrayList<Question>();
        ret.add(new Question("Who could be on broadway?"));
        ret.add(new Question("Who would sell their brother for a corn chip?"));
        ret.add(new Question("Who makes the best jokes?"));
        return ret;
    }
}
