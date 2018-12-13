package edu.calvin.cs262.wingdings.pigeonpoll;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Question.
 */
public class Question implements Serializable {
    private static final long serialVersionUID = 3L;

    public String text;
    public Date timeStamp;
    public int downloads;
    public int id;

    /**
     * Instantiates a new Question.
     *
     * @param text      the text
     * @param id        the id
     * @param timeStamp the time stamp
     * @param downloads the downloads
     */
    public Question(String text, int id, Date timeStamp, int downloads) {
        this.text = text;
        this.id = id;
        this.timeStamp = timeStamp;
        this.downloads = downloads;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Question) {
            return ((Question) obj).text.equals(text);
        }
        return false;
    }
}