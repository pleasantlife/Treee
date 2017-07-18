package com.mdy.android.treee;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by MDY on 2017-07-11.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {

    List<Memo> data = new ArrayList<>();
    LayoutInflater inflater;
    int clickCount;


    /**
     * 아답터 생성자
     * @param context - 컨텍스트
     */
    public ListAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 데이터 세팅
     * @param data - List<Memo>
     */
    public void setListData(List<Memo> data){
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Memo memo = data.get(position);
        holder.txtDate.setText(memo.date);
        Glide.with(inflater.getContext())
                .load(memo.fileUriString)
                .bitmapTransform(new GrayscaleTransformation(inflater.getContext()), new CropSquareTransformation(inflater.getContext()), new RoundedCornersTransformation(inflater.getContext(), 20, 5))
                .into(holder.imageView);

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



    class Holder extends RecyclerView.ViewHolder{
        private int position;
        TextView txtDate;
        ImageView imageView;
        CheckBox checkBoxOff;

        public Holder(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewLogo);
            checkBoxOff = (CheckBox) itemView.findViewById(R.id.checkBoxOff);

            checkBoxOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBoxOff.isChecked() == true){
                        data.get(position).check_flag = true;
                    } else if (checkBoxOff.isChecked() == false) {
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
        public void setPosition(int position){
            this.position = position;
        }

    }

    public void postListStatus(int num){
        clickCount = num;
    }

}