package com.aatishrana.fakefb.findFriend;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aatishrana.fakefb.MainActivity;
import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.base.BasePresenterFragment;
import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.data.FindFriendData;
import com.aatishrana.fakefb.findFriend.presenter.FindFriendPresenter;
import com.aatishrana.fakefb.findFriend.presenter.FindFriendPresenterFactory;
import com.aatishrana.fakefb.findFriend.presenter.FindFriendView;
import com.aatishrana.fakefb.findFriend.presenter.FindFriendViewModel;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.newsFeed.NewsFeedDecorator;
import com.aatishrana.fakefb.utils.H;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class FindFriend extends BasePresenterFragment<FindFriendPresenter, FindFriendView> implements FindFriendAdapter.OnFriendClickListener, FindFriendView
{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private FindFriendAdapter adapter;
    private FindFriendPresenter presenter;

    public FindFriend()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_friend, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view)
    {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#dfdfdf"));
        drawable.setSize(300, 20);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_find_friend_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new NewsFeedDecorator(drawable));
        adapter = new FindFriendAdapter(FindFriend.this);
        recyclerView.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                presenter.cleanCache();
                presenter.getData();
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (presenter != null)
            presenter.getData();
    }

    @Override
    public void onUserClicked(int index)
    {
        Toast.makeText(getContext(), "User clicked " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftClicked(int index)
    {
        Toast.makeText(getContext(), "Left clicked " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightClicked(int index)
    {
        Toast.makeText(getContext(), "Right clicked " + index, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    protected PresenterFactory<FindFriendPresenter> getPresenterFactory()
    {
        return new FindFriendPresenterFactory(((MainActivity) getActivity()).getRepository());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull FindFriendPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void render(FindFriendViewModel viewModel)
    {
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);
        if (viewModel.getData() != null && !viewModel.isLoading())
        {
            //show data
            adapter.setData(viewModel.getData().getFriendRequests(), viewModel.getData().getFriendSuggestions());
            adapter.notifyDataSetChanged();
        } else if (viewModel.getData() == null && viewModel.isLoading())
        {
            //show loading
            refreshLayout.setRefreshing(true);
        } else if (viewModel.getData() == null && !viewModel.isLoading())
        {
            //show error
            Toast.makeText(getContext(), "Not able to load friends data", Toast.LENGTH_SHORT).show();
        }
    }
}
