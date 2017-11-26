package com.aatishrana.fakefb.settings;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.utils.Const;

import java.util.List;

/**
 * Created by Aatish on 11/25/2017.
 */

public class FbDataAdapter extends RecyclerView.Adapter<FbDataAdapter.ViewHolder>
{
    private List<FbData> data;
    private OnFbDataClickListener listener;

    public FbDataAdapter(OnFbDataClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fb_data_item, parent, false));
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
//        private RadioButton rbIsSelected;
        private TextView tvName;
        private ImageButton ibClose;

        public ViewHolder(View itemView)
        {
            super(itemView);
//            rbIsSelected = (RadioButton) itemView.findViewById(R.id.fb_data_item_radio);
            tvName = (TextView) itemView.findViewById(R.id.fb_data_item_text);
            ibClose = (ImageButton) itemView.findViewById(R.id.fb_data_item_btn_close);

            itemView.setOnClickListener(ViewHolder.this);
            ibClose.setOnClickListener(ViewHolder.this);
        }

        public void bind(FbData fbData)
        {
            tvName.setText(fbData.getName());
            if (fbData.isSelected())
                itemView.setBackgroundColor(Color.parseColor("#c8e8ff"));
            else
                itemView.setBackgroundColor(Color.parseColor("#ffffff"));

            if (fbData.getName().equalsIgnoreCase(Const.DEFAULT_DATA))
                ibClose.setVisibility(View.INVISIBLE);
            else
                ibClose.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.fb_data_item_btn_close)
            {
                if (listener != null)
                    listener.onDeleteClick(getAdapterPosition());
                return;
            }
            if (listener != null)
                listener.onFbDataClick(getAdapterPosition());
        }
    }

    public FbData getItem(int pos)
    {
        if (pos < data.size())
            return data.get(pos);
        return null;
    }

    public void setData(List<FbData> data)
    {
        this.data = data;
    }

    public interface OnFbDataClickListener
    {
        void onFbDataClick(int adapterPosition);

        void onDeleteClick(int adapterPosition);
    }
}

