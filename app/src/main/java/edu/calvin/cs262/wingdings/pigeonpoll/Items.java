package edu.calvin.cs262.wingdings.pigeonpoll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * The type Items.
 */
public class Items {
        @SerializedName("time")
        @Expose
        private Date time;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("downloads")
        @Expose
        private Integer downloads;
        @SerializedName("contents")
        @Expose
        private String contents;

    /**
     * Instantiates a new Items.
     *
     * @param text      the text
     * @param id        the id
     * @param date      the date
     * @param downloads the downloads
     */
    public Items(String text, int id, Date date, int downloads) {
            this.contents = text;
            this.time = date;
            this.id = id;
            this.downloads = downloads;
        }

    /**
     * Gets time.
     *
     * @return the time
     */
    public Date getTime() {
            return time;
        }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(Date time) {
            this.time = time;
        }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
            return id;
        }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
            this.id = id;
        }

    /**
     * Gets downloads.
     *
     * @return the downloads
     */
    public Integer getDownloads() {
            return downloads;
        }

    /**
     * Sets downloads.
     *
     * @param downloads the downloads
     */
    public void setDownloads(Integer downloads) {
            this.downloads = downloads;
        }

    /**
     * Gets contents.
     *
     * @return the contents
     */
    public String getContents() {
            return contents;
        }

    /**
     * Sets contents.
     *
     * @param contents the contents
     */
    public void setContents(String contents) {
            this.contents = contents;
        }
    }

