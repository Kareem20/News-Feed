package com.example.news.categories;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;

import com.example.news.Adapters.articlesUriBuilder;
import com.example.news.Adapters.baseArticles;
import com.example.news.R;
import com.example.news.modals.newsList;
import com.example.news.apiFactory.newsLoader;

import java.util.List;

public class science extends baseArticles {

    //get the name of this class
    private final static String LOG_TAG = science.class.getName();

    @NonNull
    @Override
    public Loader<List<newsList>> onCreateLoader(int id, @Nullable Bundle args) {
        String scienceUrl = articlesUriBuilder.getPreferredUrl(getContext(), getString(R.string.science));
        Log.e(LOG_TAG, scienceUrl);

        // Create a new loader for the given URL
        return new newsLoader(getActivity(), scienceUrl);
    }
}