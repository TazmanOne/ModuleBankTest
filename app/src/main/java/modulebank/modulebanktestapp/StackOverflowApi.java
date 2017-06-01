package modulebank.modulebanktestapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 02.06.2017.
 */

public interface StackOverflowApi {
    @GET("/2.2/search/advanced?order=desc&sort=relevance&site=stackoverflow")
    Call<StackAnswersModel> getAnswers(@Query("q") String query);
}
