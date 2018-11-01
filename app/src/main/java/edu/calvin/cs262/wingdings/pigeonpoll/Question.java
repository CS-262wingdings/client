package edu.calvin.cs262.wingdings.pigeonpoll;

public class Question {
    // Stores the actual
    public String text;
    public float rating;

    public Question(String text, float rating) {
        this.text = text;
        this.rating = rating;
    }

    public float changeRating(float delta) {
        rating += delta;
        return rating;
    }
}