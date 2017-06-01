package modulebank.modulebanktestapp;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import android.support.v7.widget.RecyclerView;


/**
 * Created by user on 02.06.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private StackAnswersModel posts;

    public RecyclerAdapter(StackAnswersModel posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(posts.getItems().get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.getItems().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.itemName);

        }
    }
}

