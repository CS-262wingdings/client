package edu.calvin.cs262.wingdings.pigeonpoll;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.List;

public class QuestionList {
    @SerializedName("items")
    @Expose
    private List<Items> items;


    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}

