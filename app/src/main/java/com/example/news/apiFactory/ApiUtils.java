package com.example.news.apiFactory;

/*
 *this class is helper class for retrofit
 *to get data from apis
 * */
public class ApiUtils {

    //this base url for covid-19,and use it in different way.
    public static final String BASE_URL = "https://disease.sh/v3/covid-19/";

    /*
     * method to prepare the Url to use it in retrofit.
     *
     * return object of reportClient interface.
     * */
    public static reportClient getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(reportClient.class);
    }
}