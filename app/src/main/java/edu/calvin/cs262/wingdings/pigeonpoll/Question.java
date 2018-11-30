package edu.calvin.cs262.wingdings.pigeonpoll;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
    private static final long serialVersionUID = 2L;

    // Stores the question
    public String text;
    public Date timeStamp;
    public int downloads;

    public Question(String text, Date timeStamp, int downloads) {
        this.text = text;
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