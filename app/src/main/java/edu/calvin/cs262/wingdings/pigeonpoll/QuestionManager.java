// QuestionManager interfaces with the local question pool and the server question pool.
// It relays this data to the game during runtime.

package edu.calvin.cs262.wingdings.pigeonpoll;

import android.net.Network;
import android.util.Log;
import android.webkit.ConsoleMessage;

import java.io.Console;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import java.sql.Timestamp;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
//            Question q = new Question(questionText[i], questions.size() + i, new Date(System.currentTimeMillis()), 0);
            Question q = new Question(questionText[i], questions.size() + i, new Timestamp(System.currentTimeMillis()), 0);
            questions.add(q);
//            uploadQuestion(questionText[i]);
            downloadQuestions();
        }

        enabledQuestions = new ArrayList<Question>(questions);
    }

    public void addQuestion(String text, boolean local) {
//        Question q = new Question(text, questions.size(), new Date(System.currentTimeMillis()), 0);
//        addQuestionLocally(q);
//        if (!local) {
//            uploadQuestion(text);
//        }
    }

    private void addQuestionLocally(Question q) {
        questions.add(q);
        enabledQuestions.add(q);
    }

//    // Upload a question to the server
//    private void uploadQuestion(String text) {
//        Call<Question> call = QuestionClient.getInstance().getService().createQuestion(text);
//
//        call.enqueue(new Callback<Question>() {
//            @Override
//            public void onResponse(Call<Question> call, Response<Question> response) {
//                if (response.isSuccessful()) {
////                    Question questions = response.body();
////                    Question q = new Question(questions.text, questions.id, questions.timeStamp, questions.downloads);
////                    addQuestionLocally(q);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Question> call, Throwable t) {
//                Log.d("Error", t.getMessage());
//            }
//        });
//    }
//
    private void downloadQuestions() {

        Call<QuestionList> call = QuestionClient.getInstance().getService().getQuestions();

        call.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                if (response.isSuccessful()) {
                    List<Items> LI = response.body().getItems();
                    for (Items listItems : LI) {
                        Log.d("Successfull : ", String.valueOf(listItems.getContents()));
                        Question q = new Question(listItems.getContents(), listItems.getId(), listItems.getTime(), listItems.getDownloads());
                        if (!questions.contains(q)) {
                            addQuestionLocally(q);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {
                Log.d("MyApp", t.getMessage());
            }

        });
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
        ret.add(new Question("Who could be on broadway?", 99, new Date(System.currentTimeMillis()), 4));
        ret.add(new Question("Who would sell their brother for a corn chip?", 100, new Date(System.currentTimeMillis() - 14000000 ), 140));
        ret.add(new Question("Who makes the best jokes?", 101, new Date(System.currentTimeMillis() - 259599 ), 24924));
        return ret;
    }
}
