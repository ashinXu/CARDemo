package como.example.wangzhe.cardemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangzhe on 2017/8/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemHolder> {


    private Context context;
    private List<String> mDatas;

    public MyAdapter(Context context, List<String> data) {
        this.context = context;
        this.mDatas = data;
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, null, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.text.setText(mDatas.get(position));
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ItemHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }


}
