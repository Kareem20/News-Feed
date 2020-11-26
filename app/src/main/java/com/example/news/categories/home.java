package com.example.news.categories;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;

import com.example.news.Adapters.baseArticles;
import com.example.news.R;
import com.example.news.modals.newsList;
import com.example.news.apiFactory.newsLoader;

import java.util.List;

public class home extends baseArticles {

    //get the name of this class
    private final static String LOG_TAG = science.class.getName();

    @NonNull
    @Override
    public Loader<List<newsList>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String miniNumberOfArticles =sharedPreferences.getString(getContext().getString(R.string.settings_min_numbers_of_article_key)
                ,getContext().getString(R.string.settings_min_numbers_of_article_default));

        String fromDate = sharedPreferences.getString(getContext().getString(R.string.settings_from_date_key)
                ,getContext().getString(R.string.settings_from_date_default));
        String orderBy = sharedPreferences.getString(getContext().getString(R.string.settings_order_by_article_key)
                ,getContext().getString(R.string.settings_order_by_default));


        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", orderBy)
                .appendQueryParameter("from-date",fromDate)
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("show-fields","thumbnail,trailText,bodyText,lastModified")
                .appendQueryParameter("page-size",miniNumberOfArticles)
                .appendQueryParameter("api-key", "164ddb0c-58d3-4abb-8e7f-b4dfa93991e4");

        Log.e(LOG_TAG, uriBuilder.toString());

        // Create a new loader for the given URL
        return new newsLoader(getActivity(), uriBuilder.toString());
    }
}
