package com.example.harsh.aide;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by HARSH on 21-04-2017.
 */

public class MsgSentHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    ImageView iv;
    TextView tv_message,tv_sender,tv_time;
    ItemClick itemClick;
    LinearLayout ll;
    CardView cv;

    public MsgSentHolder(View itemView,ItemClick itemClick) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.Container);
        iv = (ImageView) itemView.findViewById(R.id.iv_sent);
        tv_message = (TextView) itemView.findViewById(R.id.msg_sent);
        tv_sender =  (TextView) itemView.findViewById(R.id.tv_sender_sent);
        tv_time =  (TextView) itemView.findViewById(R.id.tv_pretty);
        cv = (CardView) itemView.findViewById(R.id.rcard);
        this.itemClick = itemClick;
        ll.setOnLongClickListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        itemClick.onItemClick(getAdapterPosition());
        return false;
    }
}

