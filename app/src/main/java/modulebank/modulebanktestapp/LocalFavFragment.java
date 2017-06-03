package modulebank.modulebanktestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 03.06.2017.
 */

public class LocalFavFragment extends Fragment {
    @BindView(R.id.local_recycler)
    RecyclerView local_recycler;
    @BindView(R.id.local_search_view)
    SearchView localSearch;
    @BindView(R.id.empty_state)
    LinearLayout empty_state;
    @BindView(R.id.back_arr)
    ImageButton back_arr;

    @OnClick(R.id.back_arr)
    void back_arr_clicked() {
        getFragmentManager().popBackStack();
    }


    @Override
    public void onStart() {

        List<StackAnswersModel.Items> listItem = new ArrayList<>();
        List<DBFavorite> dbFavorite = DBFavorite.listAll(DBFavorite.class);
        if (dbFavorite.size() > 0) {
            empty_state.setVisibility(View.GONE);
            for (DBFavorite fav : dbFavorite) {
                StackAnswersModel.Items item = new StackAnswersModel.Items();
                item.setTitle(fav.title);
                item.setLink(fav.link);
                item.setOwner(new StackAnswersModel.Owner(fav.author));
                listItem.add(item);
            }
            final RecyclerAdapter adapter = new RecyclerAdapter(2);
            adapter.setPosts(listItem);

            LinearLayoutManager llm = new LinearLayoutManager(MainActivity.getInstance());
            local_recycler.setLayoutManager(llm);

            local_recycler.setAdapter(adapter);


            localSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.filter(query);
                    adapter.notifyDataSetChanged();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    adapter.notifyDataSetChanged();
                    return true;

                }
            });

        } else {
            empty_state.setVisibility(View.VISIBLE);
        }
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }
}
