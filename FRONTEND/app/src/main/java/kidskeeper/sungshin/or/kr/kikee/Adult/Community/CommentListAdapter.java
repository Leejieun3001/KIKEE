package kidskeeper.sungshin.or.kr.kikee.Adult.Community;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kidskeeper.sungshin.or.kr.kikee.Model.response.comments;
import kidskeeper.sungshin.or.kr.kikee.R;

/**
 * Created by LG on 2018-08-22.
 */

public class CommentListAdapter extends BaseAdapter {
    private ArrayList<CommentListViewItem> commentsListViewitem = new ArrayList<>();

    public CommentListAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return commentsListViewitem.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_comment_list, parent, false);
        }

        TextView commentTextView = (TextView) convertView.findViewById(R.id.comment_list_comment);
        TextView nicknameTextView = (TextView) convertView.findViewById(R.id.comment_list_nickname);

        CommentListViewItem listViewItem = commentsListViewitem.get(position);

        commentTextView.setText(listViewItem.getComment());
        nicknameTextView.setText(listViewItem.getNickname());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return commentsListViewitem.get(position);
    }

    public void addItem(String title, String nickname) {
        CommentListViewItem item = new CommentListViewItem();
        item.setComment(title);
        item.setNickname(nickname);

        commentsListViewitem.add(item);
    }

}
