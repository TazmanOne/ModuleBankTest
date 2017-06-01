package modulebank.modulebanktestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.btn_favorite)
    Button btn_favorite;
    @BindView(R.id.badge_textView)
    TextView favorite_count;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String apiUrl = "https://api.stackexchange.com/";
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String fav_str = getString(R.string.favorite);
        String emoj_str = new String(Character.toChars(0x2B50));
        btn_favorite.setText(fav_str + " " + emoj_str);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final StackOverflowApi stackOverflowApi = retrofit.create(StackOverflowApi.class);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!Objects.equals(query, "")) {
                    stackOverflowApi.getAnswers(query).enqueue(new Callback<StackAnswersModel>() {
                        @Override
                        public void onResponse(Call<StackAnswersModel> call, Response<StackAnswersModel> response) {
                            adapter = new RecyclerAdapter(response.body());
                            recyclerView.setAdapter(adapter);
                            }
                        @Override
                        public void onFailure(Call<StackAnswersModel> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                searchView.clearFocus();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
