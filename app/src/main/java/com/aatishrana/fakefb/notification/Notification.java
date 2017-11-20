package com.aatishrana.fakefb.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aatishrana.fakefb.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class Notification extends Fragment implements NotificationAdapter.NotificationClickListener
{
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Noti> sampleData;

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

    private void initViews(View view)
    {

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_notification_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sampleData = getSampleData();
        adapter = new NotificationAdapter(sampleData, Notification.this);
        recyclerView.setAdapter(adapter);
    }

    public List<Noti> getSampleData()
    {
        List<Noti> data = new ArrayList<>();

        data.add(new Noti(1, "Dilpreet likes your photo", "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", "Yesterday at 2:04 PM", 1, true));
        data.add(new Noti(2, "Dilpreet likes your photo", "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", "Yesterday at 2:04 PM", 1, false));
        data.add(new Noti(3, "Dilpreet likes your photo", "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", "Yesterday at 2:04 PM", 1, true));
        data.add(new Noti(4, "Dilpreet likes your photo", "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", "Yesterday at 2:04 PM", 1, false));
        data.add(new Noti(5, "Dilpreet likes your photo", "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", "Yesterday at 2:04 PM", 1, true));

        return data;
    }

    @Override
    public void onClick(int index)
    {
        sampleData.get(index).setRead(true);
        adapter.setData(sampleData);
        adapter.notifyItemChanged(index);
    }
}
