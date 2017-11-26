package com.aatishrana.fakefb.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aatishrana.fakefb.MainActivity;
import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.base.BasePresenterFragment;
import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.settings.presenter.SettingsPresenter;
import com.aatishrana.fakefb.settings.presenter.SettingsPresenterFactory;
import com.aatishrana.fakefb.settings.presenter.SettingsView;

import java.util.List;


/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class Settings extends BasePresenterFragment<SettingsPresenter, SettingsView> implements SettingsView, FbDataAdapter.OnFbDataClickListener
{
    private TextView tvError;
    private Button btnGo;
    private ProgressBar pb;
    private RecyclerView recyclerView;
    private FbDataAdapter adapter;

    private SettingsPresenter presenter;

    public Settings()
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
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view)
    {
        final EditText etName = (EditText) view.findViewById(R.id.fragment_settings_et_fetch_new_json_name);
        final EditText etLink = (EditText) view.findViewById(R.id.fragment_settings_et_fetch_new_json);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_settings_recyclerView);
        tvError = (TextView) view.findViewById(R.id.fragment_settings_tv_error);
        btnGo = (Button) view.findViewById(R.id.fragment_settings_btn_fetch_new_json);
        pb = (ProgressBar) view.findViewById(R.id.fragment_settings_pb_fetch_new_json);
        pb.setVisibility(View.INVISIBLE);

        adapter = new FbDataAdapter(Settings.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        btnGo.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Editable name = etName.getText();
                        Editable link = etLink.getText();
                        if (name != null && name.length() > 0 && link != null && link.length() > 0)
                        {
                            presenter.createData(name.toString(), link.toString());
                        } else
                            Toast.makeText(getContext(), "Please enter data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refresh();
    }

    public void refresh()
    {
        presenter.showListOfAllData();
    }

    @NonNull
    @Override
    protected PresenterFactory<SettingsPresenter> getPresenterFactory()
    {
        return new SettingsPresenterFactory(((MainActivity) getActivity()).getRepository());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull SettingsPresenter presenter)
    {
        this.presenter = presenter;
    }


    @Override
    public void showError(String msg)
    {
        pb.setVisibility(View.INVISIBLE);
        btnGo.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(String.valueOf(msg + " "));
    }

    @Override
    public void showSuccess()
    {
        pb.setVisibility(View.INVISIBLE);
        btnGo.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        tvError.setText("");
        refresh();
        Toast.makeText(getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading()
    {
        tvError.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.INVISIBLE);
        tvError.setText("");
    }

    @Override
    public void showAllDataList(List<FbData> listOfAllData)
    {
        adapter.setData(listOfAllData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadDataResponse(Boolean value)
    {
        if (value)
        {
            refresh();
            Toast.makeText(getContext(), "New data selected kindly refresh", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), "Data was not selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveDataResponse(boolean value)
    {
        if (value)
        {
            refresh();
            Toast.makeText(getContext(), "Data removed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFbDataClick(int adapterPosition)
    {
        FbData fbData = adapter.getItem(adapterPosition);
        if (fbData != null)
            presenter.loadData(fbData.getName());
    }

    @Override
    public void onDeleteClick(int adapterPosition)
    {
        FbData fbData = adapter.getItem(adapterPosition);
        if (fbData != null)
            presenter.removeData(fbData.getName());
    }
}
