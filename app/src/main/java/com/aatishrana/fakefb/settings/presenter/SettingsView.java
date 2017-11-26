package com.aatishrana.fakefb.settings.presenter;

import com.aatishrana.fakefb.settings.FbData;

import java.util.List;

/**
 * Created by Aatish on 11/23/2017.
 */

public interface SettingsView
{
    void showError(String msg);

    void showSuccess();

    void showLoading();

    void showAllDataList(List<FbData> listOfAllData);

    void onLoadDataResponse(Boolean value);

    void onRemoveDataResponse(boolean value);
}
