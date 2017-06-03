package modulebank.modulebanktestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    public String queryString;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.btn_favorite)
    Button btn_favorite;
    @BindView(R.id.badge_textView)
    TextView favorite_count;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_state)
    LinearLayout emptyFrame;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private String apiUrl = "https://api.stackexchange.com/";
    private RecyclerAdapter adapter;
    private boolean loading = true;
    private LinearLayoutManager llm;
    private int pageNum = 1;

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

        final Callback<StackAnswersModel> callback = new Callback<StackAnswersModel>() {
            @Override
            public void onResponse(Call<StackAnswersModel> call, Response<StackAnswersModel> response) {
                if (response.body().getItems().size() > 0) {
                    adapter.setPosts(response.body().getItems());
                    adapter.notifyDataSetChanged();
                    loading = true;

                    emptyFrame.setVisibility(View.GONE);
                } else {
                    emptyFrame.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<StackAnswersModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        };

        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisibleItems = llm.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            pageNum++;
                            stackOverflowApi.getAnswers(queryString, pageNum).enqueue(callback);
                        }
                    }
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryString = query;

                if (!Objects.equals(query, "")) {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    stackOverflowApi.getAnswers(query, pageNum).enqueue(callback);
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
