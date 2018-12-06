package edu.calvin.cs262.wingdings.pigeonpoll;

import android.support.annotation.NonNull;
import java.sql.Timestamp;

import java.util.Date;

public class Question {
    // Stores the question
    public String text;
    public Date timeStamp;
    public int downloads, id;

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