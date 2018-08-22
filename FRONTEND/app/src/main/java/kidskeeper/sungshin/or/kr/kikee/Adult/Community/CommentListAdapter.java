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

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_comment_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView commentTextView = (TextView) convertView.findViewById(R.id.comment_list_comment);
        TextView nicknameTextView = (TextView) convertView.findViewById(R.id.comment_list_nickname);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CommentListViewItem listViewItem = commentsListViewitem.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        commentTextView.setText(listViewItem.getComment());
        nicknameTextView.setText(listViewItem.getNickname());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return commentsListViewitem.get(position);
    }


    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String nickname) {
        CommentListViewItem item = new CommentListViewItem();
        item.setComment(title);
        item.setNickname(nickname);

        commentsListViewitem.add(item);
    }

}
