package edu.calvin.cs262.wingdings.pigeonpoll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDownloads() {
            return downloads;
        }

        public void setDownloads(Integer downloads) {
            this.downloads = downloads;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }
    }

