package kidskeeper.sungshin.or.kr.kikee.Kids.KidsHome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kidskeeper.sungshin.or.kr.kikee.Model.request.TodoList;
import kidskeeper.sungshin.or.kr.kikee.R;

/**
 * Created by LG on 2018-08-23.
 */

public class ToDoKidsAdapter extends RecyclerView.Adapter<ToDoKidsAdapter.ItemViewHolder> {
    ArrayList<TodoList> list;
    private Context context;
    View.OnClickListener mOnClickListener;

    private int focusedItem = 0;

    public ToDoKidsAdapter(Context context, ArrayList<TodoList> list,View.OnClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    public void setFocusedItem(int position) {
        notifyItemChanged(focusedItem);
        focusedItem = position;
        notifyItemChanged(focusedItem);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }


    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);
            return true;
        }

        return false;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_do_list_kids, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ItemViewHolder(view);
    }

    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.textViewTodo.setText(list.get(position).getTodo());
        if (list.get(position).getIsdo().equals("0")) {
            holder.imageViewIsdo.setImageResource(R.drawable.unchecked);
        } else {
            holder.imageViewIsdo.setImageResource(R.drawable.checked);
        }
        holder.itemView.setSelected(focusedItem == position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTodo;
        private ImageView imageViewIsdo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewTodo = (TextView) itemView.findViewById(R.id.todo_list_todo);
            imageViewIsdo = (ImageView) itemView.findViewById(R.id.todo_list_isdo);
        }
    }

}
