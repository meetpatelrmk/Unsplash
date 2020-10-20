package com.nirmit.unsplash;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Activity activity;
    ArrayList<MainData> dataArrayList;

    public MainAdapter(Activity activity,ArrayList<MainData> dataArrayList){

        this.activity = activity;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data = dataArrayList.get(position);
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder().setBaseColor(Color.parseColor("#F3F3F3")).setBaseAlpha(1).setHighlightColor(Color.parseColor("#E7E7E7")).setHighlightAlpha(1).setDropoff(50).build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        Glide.with(activity).load(data.getImage()).placeholder(shimmerDrawable).into(holder.ivImage);

        holder.tvName.setText(data.getName());

        holder.tvurl.setText(data.getUrl());

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName,tvurl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvurl = itemView.findViewById(R.id.tv_url);

        }

    }

}
