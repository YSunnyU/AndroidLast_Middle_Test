package com.example.androidlast_middle_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<DataClassBean.ResultBean.DataBean> data;
    Context context;
    final int VIEW_ONE=0;
    final int VIEW_TWO=1;
    final int VIEW_THREE=2;

    public MainAdapter(List<DataClassBean.ResultBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0) {
            return VIEW_ONE;
        }if (position%2 != 0){
            return VIEW_TWO;
        }else {
            return VIEW_THREE;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        if (viewType == VIEW_ONE) {
            View item_one = from.inflate(R.layout.item_one, parent, false);
            holder = new HolderOne(item_one);
        }
        else  {
            View item_two = from.inflate(R.layout.item_two, parent, false);
            holder = new HolderTwo(item_two);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderOne) {
            ((HolderOne) holder).author_name.setText(data.get(position).getAuthor_name());
            ((HolderOne) holder).title_name.setText(data.get(position).getTitle());
            Picasso.with(context).load(data.get(position).getThumbnail_pic_s()).into(((HolderOne) holder).item_img_one);
            Picasso.with(context).load(data.get(position).getThumbnail_pic_s02()).into(((HolderOne) holder).item_img_two);
            Picasso.with(context).load(data.get(position).getThumbnail_pic_s03()).into(((HolderOne) holder).item_img_three);
            holder.itemView.setTag(position);
        }
        if (holder instanceof HolderTwo) {
            ((HolderTwo) holder).two_author_name.setText(data.get(position).getAuthor_name());
            ((HolderTwo) holder).two_title_name.setText(data.get(position).getTitle());
            Picasso.with(context).load(data.get(position).getThumbnail_pic_s()).into(((HolderTwo) holder).two_item_img_one);
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return data.isEmpty() ? 0 : data.size();
    }

    public interface setShortListener {
        void setonClick(View view, int position);
    }

    private setShortListener shortListener;

    public void OnshortListener(setShortListener shortListener) {
        this.shortListener = shortListener;
    }

    @Override
    public void onClick(View view) {
        if (shortListener != null) {
            shortListener.setonClick(view, (int) view.getTag());
        }
    }

    class HolderOne extends RecyclerView.ViewHolder {
        TextView author_name;
        TextView title_name;
        ImageView item_img_one;
        ImageView item_img_two;
        ImageView item_img_three;

        public HolderOne(View itemView) {
            super(itemView);
            author_name = itemView.findViewById(R.id.author_name);
            title_name = itemView.findViewById(R.id.title_name);
            item_img_one = itemView.findViewById(R.id.item_img_one);
            item_img_two = itemView.findViewById(R.id.item_img_two);
            item_img_three = itemView.findViewById(R.id.item_img_three);
        }
    }

    class HolderTwo extends RecyclerView.ViewHolder {
        TextView two_author_name;
        TextView two_title_name;
        ImageView two_item_img_one;

        public HolderTwo(View itemView) {
            super(itemView);
            two_author_name = itemView.findViewById(R.id.two_author_name);
            two_title_name = itemView.findViewById(R.id.two_title_name);
            two_item_img_one = itemView.findViewById(R.id.two_item_img_one);
        }
    }
}
