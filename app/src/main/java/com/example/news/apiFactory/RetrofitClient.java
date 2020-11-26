package com.example.news.apiFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*helper method to prepare the client of retrofit.*/
public class RetrofitClient {

    private static Retrofit retrofit = null;

    /*static method to return the retrofit object and use it in fetching data.*/
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}