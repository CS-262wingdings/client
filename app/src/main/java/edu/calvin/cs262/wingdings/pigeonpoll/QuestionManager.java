// QuestionManager interfaces with the local question pool and the server question pool.
// It relays this data to the game during runtime.

package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Question manager.
 */
public class QuestionManager implements Serializable {
    // Stores the version for serialization
    private static final long serialVersionUID = 3L;

    // Stores all the questions which are in the local question pool
    private ArrayList<Question> questions;

    // Stores the questions which the user would actually like to use
    private ArrayList<Question> enabledQuestions;

    // Stores the questions which have been used this game already
    private transient ArrayList<Question> usedQuestions;

    // Stores the questions which have actually been asked
    private transient ArrayList<Question> askedQuestions;

    private transient Random random;

    // Stores this QuestionManager's instance, to make it a singleton
    public static QuestionManager instance;

    private transient Context context;

    /**
     * Instantiates a new Question manager.
     *
     * @param context the context
     */
    public QuestionManager(Context context)  {
        this.context = context;

        questions = new ArrayList<Question>();
        enabledQuestions = new ArrayList<Question>();
        usedQuestions = new ArrayList<Question>();
        askedQuestions = new ArrayList<Question>();

        random = new Random();
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static QuestionManager getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionManager(context);
            instance.loadQuestions();
        }
        return instance;
    }

    /**
     * Add question.
     *
     * @param text   the text
     * @param online the online
     */
    public void addQuestion(String text, boolean online) {
       Question q = new Question(text, questions.size(), new Date(System.currentTimeMillis()), 0);
       if (online) {
        uploadQuestion(text);
       } else {
           addQuestionLocally(q);
       }
    }


    /**
     * Add question locally.
     *
     * @param q the q
     */
    void addQuestionLocally(Question q) {
        questions.add(q);
        enabledQuestions.add(q);
        saveQuestions();
    }


   // Upload a question to the server
   private void uploadQuestion(String text) {
       Date date = new Date(System.currentTimeMillis());
       Items item = new Items(text, 1, date, 1);
       Call<Items> call = QuestionClient.getInstance().getService().createQuestion(item);

       call.enqueue(new Callback<Items>() {
           @Override
           public void onResponse(Call<Items> call, Response<Items> response) {
               if (response.isSuccessful()) {
                   Items responseQuestions = response.body();
                   Question q = new Question(responseQuestions.getContents(), responseQuestions.getId(), responseQuestions.getTime(), responseQuestions.getDownloads());
                   addQuestionLocally(q);
               }
           }

           @Override
           public void onFailure(Call<Items> call, Throwable t) {
               Log.d("Error", t.getMessage());
           }
       });
   }

    void downloadQuestions(final DownloadQuestion dq) {
        Call<QuestionList> call = QuestionClient.getInstance().getService().getQuestions();

        call.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                if (response.isSuccessful()) {
                    List<Items> LI = response.body().getItems();
                    ArrayList<Question> returnList = new ArrayList<Question>();

                    for (Items listItems : LI) {
                        Log.d("Successfull : ", String.valueOf(listItems.getContents()));
                        Question q = new Question(listItems.getContents(), listItems.getId(), listItems.getTime(), listItems.getDownloads());
//                        if (!questions.contains(q)) {
                            returnList.add(q);
//                        }
                    }

                    dq.returnList(returnList);
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

    public void resetQuestions() {
        askedQuestions = new ArrayList<Question>();

        for (Question q : questions) {
            if (usedQuestions.contains(q)) {
                enableQuestion(q);
            }
        }

        usedQuestions = new ArrayList<Question>();
    }

    private void saveQuestions() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(context.openFileOutput("questions.sav", Context.MODE_PRIVATE));
            out.writeObject(instance);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestions() {
        loadQuestions(false);
    }

    private void loadQuestions(boolean corrupted) {
        boolean exists = true;
        File testFile = context.getFileStreamPath("questions.sav");
        if (testFile == null || !testFile.exists()) {
            exists = false;
        }

        if (!exists || corrupted) {
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
                    "Which person deserves to win the lottery?",
                    "Who would raise a baby animal like their child out of the kindness of their own heart?",
                    "Which person would you switch wardrobes with?",
                    "Who is most capable of writing the next NYT best seller?",
                    "Who would be the most intimidating person if they gained 40 pounds of muscle?",
                    "If you had to choose someone to be the earth's embassador for alien civilizations, who would you volunteer?",
                    "Who is most able to beat you in a fist fight?",
                    "Who is most able to beat you in a public debate?",
                    "Who would look the best in a mullet?",
                    "Who is the most qualified to use the word y'all unironically?",
                    "Who throws caution to the wind?",
                    "Who would be the first to protest an oppressive regime?",
                    "Who would get famous on the internet for doing something stupid?",
                    "Who is your evil twin?",
                    "Pretend that this is a really embarrassing question. Vote for the last player but don't tell them what it is.",
                    "Who could probably make animals sing like in a Disney Princess movie?",
                    "Who would look the best in full Victorian English attire? (this includes the top hat)",
                    "Who has probably blown a blood vessel in their forehead at some point in their life?",
                    "Who needs a stern talking-to?",
                    "Who could calm an angry bull with their soothing voice?",
                    "Who probably likes Circus Peanuts and Necco Wafers, but is too embarrassed to say it?",
                    "Who would be a good voice actor for a cartoon character?",
                    "Who would make the most competent fireman/firewoman?",
                    "Who would benefit from the hippie lifestyle?",
                    "Who do you suspect has a secret superpower?"
            };

            for(int i = 0; i < questionText.length; i++) {
                Question q = new Question(questionText[i], 1, new Date(System.currentTimeMillis()), 0);
                addQuestionLocally(q);
            }

            saveQuestions();
        }

        try {
            ObjectInputStream in = new ObjectInputStream(context.openFileInput("questions.sav"));
            QuestionManager qm = (QuestionManager)in.readObject();
            setQuestions(qm.questions);
            setEnabledQuestions(qm.getEnabledQuestions());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (questions.size() == 0) {
            loadQuestions(true);
        }
    }

    private ArrayList<Question> getEnabledQuestions() {
        return enabledQuestions;
    }

    boolean isQuestionEnabled(Question q) {
        return enabledQuestions.contains(q);
    }

    void disableQuestion(Question q) {
        enabledQuestions.remove(q);
        saveQuestions();
    }

    void enableQuestion(Question q) {
        enabledQuestions.add(q);
        saveQuestions();
    }

    void deleteQuestion(Question q) {
        questions.remove(q);
        saveQuestions();
    }

    private void setQuestions(ArrayList<Question> otherQuestions) {
        this.questions = otherQuestions;
    }

    private void setEnabledQuestions(ArrayList<Question> otherQuestions) {
        this.enabledQuestions = otherQuestions;
    }
}
