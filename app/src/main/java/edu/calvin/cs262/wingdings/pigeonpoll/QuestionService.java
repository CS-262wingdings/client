package edu.calvin.cs262.wingdings.pigeonpoll;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuestionService {
    @GET("game/v1/questions")
    Call<QuestionList> getQuestions();

    @POST("game/v1/question")
    Call<Items> createQuestion(@Body Items item);

    @PUT("game/v1/question/{id}")
    Call<Items> updateDownloads(@Path("id") int id);
}
