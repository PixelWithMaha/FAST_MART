package com.example.assignment3.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;

public class DealViewHolder extends RecyclerView.ViewHolder {
    TextView tvName, tvDiscount, tvOriginalPrice, tvDesc;
    ImageView ivImage, ivFav;

    public DealViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_deal_name);
        tvDiscount = itemView.findViewById(R.id.tv_deal_discount);
        ivImage = itemView.findViewById(R.id.iv_deal_image);
        tvOriginalPrice = itemView.findViewById(R.id.tv_deal_original_price);
        tvDesc = itemView.findViewById(R.id.tv_deal_desc);
        ivFav = itemView.findViewById(R.id.iv_deal_heart);
    }
}