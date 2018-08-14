package kidskeeper.sungshin.or.kr.kikee.Adult.Community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kidskeeper.sungshin.or.kr.kikee.Model.response.board;
import kidskeeper.sungshin.or.kr.kikee.R;

/**
 * Created by LG on 2018-08-14.
 */

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private final String TAG = "BoardAdapter";
    private Context context;
    private ArrayList<board> list;
    View.OnClickListener mOnClickListener;

    public BoardAdapter(Context context, ArrayList<board> list, View.OnClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    public void setAdapter(ArrayList<board> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boardlist, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new BoardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardAdapter.ViewHolder holder, int position) {
        board item = list.get(position);
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewContent.setText(item.getContent());
        holder.textViewHits.setText(String.valueOf(item.getHits()));
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getIdx();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;
        private TextView textViewHits;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.itemboard_textview_title);
            textViewContent = (TextView) itemView.findViewById(R.id.itemboard_textview_content);
            textViewHits = (TextView) itemView.findViewById(R.id.itemboard_textview_hits);
        }
    }

}

