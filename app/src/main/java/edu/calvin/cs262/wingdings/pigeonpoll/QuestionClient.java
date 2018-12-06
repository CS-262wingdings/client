package edu.calvin.cs262.wingdings.pigeonpoll;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionClient {
    private static final String BASE_URL = "https://phonic-biplane-221307.appspot.com/";
    private static QuestionClient service;
    private Retrofit retrofit;

    private QuestionClient() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized QuestionClient getInstance() {
        if (service == null) {
            service = new QuestionClient() {
            };
        }
        return service;
    }

    public QuestionService getService() {
        return retrofit.create(QuestionService.class);
    }
}
