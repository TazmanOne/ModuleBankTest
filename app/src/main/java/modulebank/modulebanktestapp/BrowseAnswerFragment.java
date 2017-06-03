package modulebank.modulebanktestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 03.06.2017.
 */

public class BrowseAnswerFragment extends Fragment {
    @BindView(R.id.webView)
    public WebView webView;
    @BindView(R.id.back_arr)
    ImageButton back_arr;
    @BindView(R.id.add_fav)
    ImageButton add_fav;
    private String link;
    private String name;
    private String title;

    @OnClick(R.id.add_fav)
    void add_fav_clicked() {
        if (add_fav.isSelected()) {
            List<DBFavorite> dbFavorite = DBFavorite.find(DBFavorite.class, "link = ?", link);
            DBFavorite.delete(dbFavorite.get(0));
            Toast.makeText(getActivity(), "Удалено из избранного", Toast.LENGTH_SHORT).show();
            add_fav.setSelected(false);
        } else {
            DBFavorite fav = new DBFavorite(title, link, name);
            fav.save();
            Toast.makeText(getActivity(), "Добавлено в избранное", Toast.LENGTH_SHORT).show();
            add_fav.setSelected(true);
            getFragmentManager().popBackStack();
        }

    }

    @OnClick(R.id.back_arr)
    void back_arr_clicked() {
        getFragmentManager().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            link = bundle.getString("link");
            title = bundle.getString("title");
            name = bundle.getString("name");
            List<DBFavorite> dbFavorite = DBFavorite.find(DBFavorite.class, "link = ?", link);
            if (dbFavorite.size() > 0) {
                add_fav.setSelected(true);
            } else {
                add_fav.setSelected(false);
            }
        }
        webView.loadUrl(link);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.getInstance().setFavCount();
    }
}
