package kidskeeper.sungshin.or.kr.kikee.Kids.Word;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import kidskeeper.sungshin.or.kr.kikee.R;

class ViewHolder extends RecyclerView.ViewHolder{
    TextView titleCategory;
    TextView levelText;
    public ViewHolder(View itemView){
        super(itemView);
        titleCategory=itemView.findViewById(R.id.categoryText);
        levelText = itemView.findViewById(R.id.levelText);
    }

}


public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private int resourceId;
    private List<RecyclerModel>dataList;

    public RecyclerAdapter(Context context, int resourceId,List<RecyclerModel>dataList){
        this.context =context;
        this.dataList = dataList;
        this.resourceId = resourceId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(resourceId,parent,false));
    }
    public void onBindViewHolder(ViewHolder holder, int position){
        RecyclerModel recyclerModel = dataList.get(position);
        holder.titleCategory.setText(recyclerModel.getCategory());
        holder.levelText.setText(( recyclerModel.getLevel()));
    }
    public int getItemCount(){
        return dataList.size();
    }
}
