package com.aatishrana.fakefb.notification;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.utils.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aatish Rana on 20-Nov-17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>
{
    private List<Noti> data;
    private NotificationClickListener clickListener;

    public NotificationAdapter(List<Noti> data, NotificationClickListener clickListener)
    {
        this.data = data;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public void setData(List<Noti> data)
    {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView ivUerPic, ivIcon;
        private TextView tvDesc, tvTime;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ivUerPic = (ImageView) itemView.findViewById(R.id.noti_item_iv_pic);
            ivIcon = (ImageView) itemView.findViewById(R.id.noti_item_iv_type_icon);
            tvDesc = (TextView) itemView.findViewById(R.id.noti_item_tv_one);
            tvTime = (TextView) itemView.findViewById(R.id.noti_item_tv_two);

            itemView.setOnClickListener(ViewHolder.this);
        }

        public void bind(Noti noti)
        {
            tvDesc.setText(noti.getTitle());
            tvTime.setText(noti.getTime());

            if (noti.getUserPic().length() > 0)
            {
                Picasso
                        .with(itemView.getContext())
                        .load(noti.getUserPic())
                        .transform(new CropCircleTransformation())
                        .into(this.ivUerPic);
            }


            if (!noti.isRead())
            {
                tvTime.setTextColor(Color.parseColor("#0000ff"));
                itemView.setBackgroundColor(Color.parseColor("#c8e8ff"));
            } else
            {
                tvTime.setTextColor(Color.parseColor("#dfdfdf"));
                itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            //todo set icon
        }

        @Override
        public void onClick(View v)
        {
            if (clickListener != null)
                clickListener.onClick(getAdapterPosition());
        }
    }

    public interface NotificationClickListener
    {
        void onClick(int pos);
    }
}
