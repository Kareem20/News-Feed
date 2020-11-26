package com.example.news.apiFactory;

import com.example.news.modals.Country;
import com.example.news.modals.Worldwide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 *interface to prepare the Uri to get the correct data.
 */
public interface reportClient {

    //method to put the name of country,and get the data of specific country.
    //return object of call of country modal.
    @GET("countries/{Egypt}")
    Call<Country> getCountry(@Path("Egypt") String nameOfCountry);

    //method ro prepare the uri to get the data of worldwide;
    //return object of call of Worldwide modal.
    @GET("all")
    Call<Worldwide> getAll();
}

