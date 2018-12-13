package edu.calvin.cs262.wingdings.pigeonpoll;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * The type Question list.
 */
public class QuestionList {
    @SerializedName("items")
    @Expose
    private List<Items> items;


    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Items> getItems() {
        return items;
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    public void setItems(List<Items> items) {
        this.items = items;
    }
}

