package com.aatishrana.fakefb.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Aatish on 11/25/2017.
 */

public interface APIService
{
    @GET()
    Call<String> fetchData(@Url String url);
}
