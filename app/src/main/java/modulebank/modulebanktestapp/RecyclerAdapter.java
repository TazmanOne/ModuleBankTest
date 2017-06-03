package modulebank.modulebanktestapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int typeLoad;
    private List<StackAnswersModel.Items> posts;
    private List<StackAnswersModel.Items> postsCopy;

    public RecyclerAdapter(int typeLoad) {
        posts = new ArrayList<>();
        postsCopy = new ArrayList<>();
        this.typeLoad = typeLoad;

    }

    public List<StackAnswersModel.Items> getPosts() {
        return postsCopy;
    }

    public void setPosts(List<StackAnswersModel.Items> posts) {
        this.postsCopy.addAll(posts);
        this.posts.addAll(posts);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);

        return (typeLoad == 1 ? new MainViewHolder(v) : new LocalViewHolder(v));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (typeLoad) {
            case 1: {
                MainViewHolder vh = (MainViewHolder) holder;
                vh.answersCount.setText(String.valueOf(postsCopy.get(position).getAnswerCount()));
                vh.authorName.setText(postsCopy.get(position).getOwner().getDisplayName());
                vh.questionName.setText(Html.fromHtml(postsCopy.get(position).getTitle()));
                vh.votesCount.setText(String.valueOf(postsCopy.get(position).getScore()));
                vh.viewsCount.setText(String.valueOf(postsCopy.get(position).getViewCount()));

                vh.questionItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BrowseAnswerFragment fragment = new BrowseAnswerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("link", postsCopy.get(position).getLink());
                        bundle.putString("title", postsCopy.get(position).getTitle());
                        bundle.putString("name", postsCopy.get(position).getOwner().getDisplayName());
                        fragment.setArguments(bundle);
                        FragmentManager manager = MainActivity.getInstance().getFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.container, fragment, BrowseAnswerFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                        MainActivity.getInstance().searchView.clearFocus();

                    }
                });
            }
            break;
            case 2: {
                LocalViewHolder lvh = (LocalViewHolder) holder;
                lvh.statLayout.setVisibility(View.GONE);
                lvh.vLine.setVisibility(View.GONE);
                lvh.authorName.setText(postsCopy.get(position).getOwner().getDisplayName());
                lvh.questionName.setText(Html.fromHtml(postsCopy.get(position).getTitle()));
                lvh.questionItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BrowseAnswerFragment fragment = new BrowseAnswerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("link", postsCopy.get(position).getLink());
                        bundle.putString("title", postsCopy.get(position).getTitle());
                        bundle.putString("name", postsCopy.get(position).getOwner().getDisplayName());
                        fragment.setArguments(bundle);
                        FragmentManager manager = MainActivity.getInstance().getFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.container, fragment, BrowseAnswerFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                        MainActivity.getInstance().searchView.clearFocus();

                    }
                });
            }
            break;
        }

    }


    @Override
    public int getItemCount() {
        if (postsCopy == null)
            return 0;
        return postsCopy.size();
    }

    public void clear() {
        this.postsCopy.clear();
    }

    public void filter(String text) {
        postsCopy.clear();
        if (text.isEmpty()) {
            postsCopy.addAll(posts);
        } else {
            text = text.toLowerCase();
            for (StackAnswersModel.Items item : posts) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    postsCopy.add(item);
                }
            }
        }
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {
        TextView votesCount;
        TextView answersCount;
        TextView viewsCount;
        TextView questionName;
        TextView authorName;
        LinearLayout questionItem;

        MainViewHolder(View itemView) {
            super(itemView);
            questionName = (TextView) itemView.findViewById(R.id.questionName);
            authorName = (TextView) itemView.findViewById(R.id.authorName);
            viewsCount = (TextView) itemView.findViewById(R.id.views_count);
            answersCount = (TextView) itemView.findViewById(R.id.answers_count);
            votesCount = (TextView) itemView.findViewById(R.id.votes_count);
            questionItem = (LinearLayout) itemView.findViewById(R.id.questionItem);


        }
    }

    private class LocalViewHolder extends RecyclerView.ViewHolder {
        TextView questionName;
        TextView authorName;
        LinearLayout questionItem;
        LinearLayout statLayout;
        ImageView vLine;

        LocalViewHolder(View itemView) {
            super(itemView);
            questionName = (TextView) itemView.findViewById(R.id.questionName);
            authorName = (TextView) itemView.findViewById(R.id.authorName);
            questionItem = (LinearLayout) itemView.findViewById(R.id.questionItem);
            statLayout = (LinearLayout) itemView.findViewById(R.id.statLayout);
            vLine = (ImageView) itemView.findViewById(R.id.vLine);

        }
    }
}

