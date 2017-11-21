package com.aatishrana.fakefb.findFriend;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.aatishrana.fakefb.findFriend.presenter.FindFriendPresenter;
import com.aatishrana.fakefb.findFriend.presenter.FindFriendPresenterFactory;
import com.aatishrana.fakefb.findFriend.presenter.FindFriendView;
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
        adapter = new FindFriendAdapter(getSampleRequest(), getSampleSuggestions(), FindFriend.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (presenter != null)
        {
            presenter.getData();
        }
    }

    private List<Friend> getSampleRequest()
    {
        List<Friend> requests = new ArrayList<>();
        requests.add(new Friend("Bill", "Gates", 37, new Image("http://news.thewindowsclubco.netdna-cdn.com/wp-content/uploads/2013/01/Bill-Gates-100x100.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("Elon", "Musk", 0, new Image("https://cdn.geekwire.com/wp-content/uploads/2014/09/elonmusk-300x300.jpeg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        return requests;
    }

    public List<Friend> getSampleSuggestions()
    {
        List<Friend> requests = new ArrayList<>();
        requests.add(new Friend("Mark", "zuckerberg", 999, new Image("http://assets.summit.vanityfair.com.s3.amazonaws.com/speaker_thumbnail_large_d02844b9686230238cd4a45ed736a6b2b0f4c730.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("diljit", "dosanjh", 12, new Image("http://www.askmenumber.com/wp-content/uploads/2016/09/Singer-Diljit-Dosanjh-Contact-300x300.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("barack", "obama", 0, new Image("https://cdn.inquisitr.com/wp-content/uploads/2015/04/Obama-100x100.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("Green", "Arrow", 0, new Image("https://yt3.ggpht.com/-PEK91MeBByY/AAAAAAAAAAI/AAAAAAAAAAA/O1s_J93B_LE/s100-c-k-no-mo-rj-c0xffffff/photo.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("Bill", "Gates", 37, new Image("http://news.thewindowsclubco.netdna-cdn.com/wp-content/uploads/2013/01/Bill-Gates-100x100.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("Elon", "Musk", 0, new Image("https://cdn.geekwire.com/wp-content/uploads/2014/09/elonmusk-300x300.jpeg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("Mark", "zuckerberg", 999, new Image("http://assets.summit.vanityfair.com.s3.amazonaws.com/speaker_thumbnail_large_d02844b9686230238cd4a45ed736a6b2b0f4c730.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("diljit", "dosanjh", 12, new Image("http://www.askmenumber.com/wp-content/uploads/2016/09/Singer-Diljit-Dosanjh-Contact-300x300.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("barack", "obama", 0, new Image("https://cdn.inquisitr.com/wp-content/uploads/2015/04/Obama-100x100.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        requests.add(new Friend("Green", "Arrow", 0, new Image("https://yt3.ggpht.com/-PEK91MeBByY/AAAAAAAAAAI/AAAAAAAAAAA/O1s_J93B_LE/s100-c-k-no-mo-rj-c0xffffff/photo.jpg", H.dToPi(getContext(), 100), H.dToPi(getContext(), 100), "")));
        return requests;
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
}
