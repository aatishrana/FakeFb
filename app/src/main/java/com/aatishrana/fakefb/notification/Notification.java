package com.aatishrana.fakefb.notification;

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
import com.aatishrana.fakefb.notification.presenter.NotificationPresenter;
import com.aatishrana.fakefb.notification.presenter.NotificationPresenterFactory;
import com.aatishrana.fakefb.notification.presenter.NotificationView;
import com.aatishrana.fakefb.notification.presenter.NotificationViewModel;

import java.util.List;


/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class Notification extends BasePresenterFragment<NotificationPresenter, NotificationView> implements NotificationAdapter.NotificationClickListener, NotificationView
{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Noti> sampleData;
    private NotificationPresenter presenter;

    public Notification()
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.getData();
    }

    private void initViews(View view)
    {

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_notification_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new NotificationAdapter(Notification.this);
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
    public void onClick(int index)
    {
        sampleData.get(index).setRead(true);
        adapter.setData(sampleData);
        adapter.notifyItemChanged(index);
    }


    @NonNull
    @Override
    protected PresenterFactory<NotificationPresenter> getPresenterFactory()
    {
        return new NotificationPresenterFactory(((MainActivity) getActivity()).getRepository());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull NotificationPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void render(NotificationViewModel viewModel)
    {
        if (viewModel.getData() != null && !viewModel.getData().isEmpty() && !viewModel.isLoading())
        {
            //show data
            if (refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
            sampleData = viewModel.getData();
            adapter.setData(sampleData);
            adapter.notifyDataSetChanged();
        } else if (viewModel.getData() == null && viewModel.isLoading())
        {
            //show loading
            refreshLayout.setRefreshing(true);
        } else if (viewModel.getData() == null && !viewModel.isLoading())
        {
            //show error
            Toast.makeText(getContext(), "Notification not fetched", Toast.LENGTH_SHORT).show();
        } else if (viewModel.getData() != null && viewModel.getData().isEmpty() && !viewModel.isLoading())
        {
            //show empty
            Toast.makeText(getContext(), "No Notifications", Toast.LENGTH_SHORT).show();
        }
    }
}
