package com.example.harsh.aide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HARSH on 21-05-2017.
 */


    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name;
    View container;
    ItemClickCallback itemClickCallback;



    public RecyclerHolder(View itemView, ItemClickCallback itemClickCallback) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.tv_header_chat);
        container = itemView.findViewById(R.id.container);
        container.setOnClickListener(this);
        this.itemClickCallback=itemClickCallback;
    }

    @Override
    public void onClick(View view) {
            itemClickCallback.OnItemClick(getAdapterPosition());
    }
}
