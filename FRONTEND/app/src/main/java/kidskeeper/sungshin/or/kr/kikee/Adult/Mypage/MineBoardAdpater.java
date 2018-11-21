package kidskeeper.sungshin.or.kr.kikee.Adult.Mypage;

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
 * Created by LG on 2018-08-24.
 */

public class MineBoardAdpater extends RecyclerView.Adapter<MineBoardAdpater.ViewHolder> {
    private final String TAG = "BoardAdapter";
    private Context context;
    private ArrayList<board> list;
    View.OnClickListener mOnClickListener;

    public MineBoardAdpater(Context context, ArrayList<board> list, View.OnClickListener mOnClickListener) {
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
    public MineBoardAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_board, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new MineBoardAdpater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MineBoardAdpater.ViewHolder holder, int position) {
        board item = list.get(position);
        holder.textViewMyTitle.setText(item.getTitle());
        holder.textViewMyContent.setText(item.getContent());

    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getIdx();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMyTitle;
        private TextView textViewMyContent;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewMyTitle = (TextView) itemView.findViewById(R.id.my_board_textview_title);
            textViewMyContent = (TextView) itemView.findViewById(R.id.my_board_textview_content);
        }
    }

}
