package modulebank.modulebanktestapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface StackOverflowApi {
    @GET("/2.2/search/advanced?order=desc&sort=activity&site=stackoverflow")
    Call<StackAnswersModel> getAnswers(@Query("q") String query, @Query("page") int page);
}
