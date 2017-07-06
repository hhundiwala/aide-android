package com.example.harsh.aide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by HARSH on 21-05-2017.
 */

public class RecyclerApointmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView apointee,task,date;
    View container;
    ItemClickCallback itemClickCallback;



    public RecyclerApointmentHolder(View itemView, ItemClickCallback itemClickCallback) {
        super(itemView);
        apointee = (TextView)itemView.findViewById(R.id.tv_apointee);
        task = (TextView)itemView.findViewById(R.id.tv_task);
        date = (TextView)itemView.findViewById(R.id.tv_date);
        container = itemView.findViewById(R.id.container);
        container.setOnClickListener(this);
        this.itemClickCallback=itemClickCallback;
    }

    @Override
    public void onClick(View view) {
        itemClickCallback.OnItemClick(getAdapterPosition());
    }
}
