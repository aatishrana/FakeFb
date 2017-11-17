package com.aatishrana.fakefb.findFriend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.utils.H;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aatish Rana on 17-Nov-17.
 */

public class FindFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<Friend> requests;
    private List<Friend> suggestions;
    private OnFriendClickListener friendClickListener;

    public FindFriendAdapter(List<Friend> requests, List<Friend> suggestions, OnFriendClickListener friendClickListener)
    {
        this.requests = requests;
        this.suggestions = suggestions;
        this.friendClickListener = friendClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == R.layout.find_friend_header)
            return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (getItemViewType(position))
        {
            case R.layout.find_friend_header:
                HeadViewHolder header = (HeadViewHolder) holder;
                if (position == 0) header.bind("friend requests", this.requests.size());
                else header.bind("people you may know", 0);
                break;
            case R.layout.find_friend_item:
                ItemViewHolder item = (ItemViewHolder) holder;
                if (position <= requests.size())
                    item.bind(requests.get(position - 1), true);
                else
                {
                    int pos = position - (requests.size() + 2);
                    item.bind(suggestions.get(pos), false);
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0 || position == requests.size() + 1)
            return R.layout.find_friend_header;
        else
            return R.layout.find_friend_item;
    }

    @Override
    public int getItemCount()
    {
        return this.requests.size() + this.suggestions.size() + 2;//2 for headers
    }

    public void setData(List<Friend> requests, List<Friend> suggestions)
    {
        setRequests(requests);
        setSuggestions(suggestions);
    }

    public void setRequests(List<Friend> requests)
    {
        this.requests = requests;
    }

    public void setSuggestions(List<Friend> suggestions)
    {
        this.suggestions = suggestions;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView tvUserName, tvMutualFriendCount;
        private ImageView ivUserPic;
        private Button btnLeft, btnRight;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.find_friend_item_tv_user_name);
            tvMutualFriendCount = (TextView) itemView.findViewById(R.id.find_friend_item_tv_mutual_friends_count);

            ivUserPic = (ImageView) itemView.findViewById(R.id.find_friend_item_iv_pic);

            btnLeft = (Button) itemView.findViewById(R.id.find_friend_item_btn_left);
            btnRight = (Button) itemView.findViewById(R.id.find_friend_item_btn_right);

            itemView.setOnClickListener(ItemViewHolder.this);
            btnLeft.setOnClickListener(ItemViewHolder.this);
            btnRight.setOnClickListener(ItemViewHolder.this);
        }

        public void bind(Friend friend, boolean isRequest)
        {
            tvUserName.setText(friend.getName());
            if (friend.getMutualFriends() > 0)
            {
                tvMutualFriendCount.setVisibility(View.VISIBLE);
                tvMutualFriendCount.setText(String.valueOf(friend.getMutualFriends() + " mutual friends"));
            } else
                tvMutualFriendCount.setVisibility(View.GONE);

            Image image = friend.getPicUrl();
            if (image != null && image.isValid())
            {
                Picasso
                        .with(itemView.getContext())
                        .load(image.getUrl())
                        .into(this.ivUserPic);
            }

            if (isRequest)
            {
                btnLeft.setText("Confirm".toUpperCase());
                btnRight.setText("Delete".toUpperCase());
            } else
            {
                btnLeft.setText("Add Friend".toUpperCase());
                btnRight.setText("Remove".toUpperCase());
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.find_friend_item_rel:
                    if (friendClickListener != null)
                        friendClickListener.onUserClicked(getAdapterPosition());
                    break;
                case R.id.find_friend_item_btn_left:
                    if (friendClickListener != null)
                        friendClickListener.onLeftClicked(getAdapterPosition());
                    break;
                case R.id.find_friend_item_btn_right:
                    if (friendClickListener != null)
                        friendClickListener.onRightClicked(getAdapterPosition());
                    break;
            }
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTitle, tvCount;

        public HeadViewHolder(View itemView)
        {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.find_friend_tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.find_friend_tv_count);
        }

        public void bind(String title, int count)
        {
            if (count > 0)
                this.tvCount.setText(String.valueOf(count));

            this.tvTitle.setText(title.toUpperCase());
        }
    }

    public interface OnFriendClickListener
    {

        void onUserClicked(int index);

        void onLeftClicked(int index);

        void onRightClicked(int index);
    }
}
