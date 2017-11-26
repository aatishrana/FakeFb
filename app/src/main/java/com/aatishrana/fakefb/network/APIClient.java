package com.aatishrana.fakefb.network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Aatish on 11/25/2017.
 */

public class APIClient
{
    private static Retrofit retrofit = null;


    public static Retrofit getClient()
    {
        if (retrofit == null)
        {
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.myjson.com/bins/19ors7/")
//                    .baseUrl("/")
//                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
