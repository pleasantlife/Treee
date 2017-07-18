package com.mdy.android.treee;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdy.android.treee.domain.Memo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDY on 2017-07-11.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.Holder> {

    List<Memo> data = new ArrayList<>();
    LayoutInflater inflater;
    int clickCount;

    public FeedAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setFeedData(List<Memo> data) {
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Memo memo = data.get(position);
        holder.txtContent1.setText(memo.content1);
        holder.txtContent2.setText(memo.content2);
        holder.txtContent3.setText(memo.content3);
        holder.txtDate.setText(memo.date);

        // 이미지가 없으면 cardview 안보이게
        if(memo.fileUriString != null && !"".equals(memo.fileUriString)) {
            Log.w("memo.fileUriString", "111===================== memo.fileUriString ====================" + memo.fileUriString);
            Glide.with(inflater.getContext())
                    .load(memo.fileUriString)
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Log.w("memo.fileUriString", "222===================== memo.fileUriString ====================" + memo.fileUriString);
            holder.cardviewImage.setVisibility(View.INVISIBLE);
        }

        holder.setPosition(position);

        if(clickCount %2 == 1){
            holder.checkBoxOff.setVisibility(View.VISIBLE);
        } else if (clickCount %2 == 0){
            memo.check_flag = false;
            holder.checkBoxOff.setChecked(false);
            holder.checkBoxOff.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private int position;
        TextView txtContent1, txtContent2, txtContent3;
        TextView txtDate;
        ImageView imageView;
        CheckBox checkBoxOff;
        CardView cardviewImage;

        public Holder(View itemView) {
            super(itemView);
            txtContent1 = (TextView) itemView.findViewById(R.id.txtContent1);
            txtContent2 = (TextView) itemView.findViewById(R.id.txtContent2);
            txtContent3 = (TextView) itemView.findViewById(R.id.txtContent3);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            checkBoxOff = (CheckBox) itemView.findViewById(R.id.checkBoxOff);
            cardviewImage = (CardView) itemView.findViewById(R.id.cardviewImage);

            checkBoxOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBoxOff.isChecked() == true){
                        data.get(position).check_flag = true;
                    } else if (checkBoxOff.isChecked() == false){
                        data.get(position).check_flag = false;
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ReadActivity.class);
                    intent.putExtra("LIST_POSITION", position);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public void postFeedStatus(int num){
        clickCount = num;
    }
}