package modulebank.modulebanktestapp;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StackAnswersModel.Items> posts;

    public RecyclerAdapter() {
        posts = new ArrayList<>();

    }

    public RecyclerAdapter(List<StackAnswersModel.Items> posts) {
        this.posts.addAll(posts);
    }

    public List<StackAnswersModel.Items> getPosts() {
        return posts;
    }

    public void setPosts(List<StackAnswersModel.Items> posts) {
        this.posts.addAll(posts);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.answersCount.setText(String.valueOf(posts.get(position).getAnswerCount()));
        vh.authorName.setText(posts.get(position).getOwner().getDisplayName());
        vh.questionName.setText(Html.fromHtml(posts.get(position).getTitle()));
        vh.votesCount.setText(String.valueOf(posts.get(position).getScore()));
        vh.viewsCount.setText(String.valueOf(posts.get(position).getViewCount()));

    }


    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    public void clear() {
        this.posts.clear();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView votesCount;
        TextView answersCount;
        TextView viewsCount;
        TextView questionName;
        TextView authorName;


        ViewHolder(View itemView) {
            super(itemView);
            questionName = (TextView) itemView.findViewById(R.id.questionName);
            authorName = (TextView) itemView.findViewById(R.id.authorName);
            viewsCount = (TextView) itemView.findViewById(R.id.views_count);
            answersCount = (TextView) itemView.findViewById(R.id.answers_count);
            votesCount = (TextView) itemView.findViewById(R.id.votes_count);

        }
    }
}

