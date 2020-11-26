package com.example.news.apiFactory;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;


import com.example.news.modals.newsList;

import java.util.List;

/*
* this class to make the process of fetching the data
* in another thread by extend AsyncTaskLoader class.
* */
public class newsLoader extends AsyncTaskLoader<List<newsList>> {

    /*the url of data.*/
    private final String mUrl;
    /*the name of class for debugging.*/
    private static final String LOG_TAG = newsLoader.class.getName();

    public newsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "this when onStartLoading calling...");
        forceLoad();
    }

    @Nullable
    @Override
    public List<newsList> loadInBackground() {
        Log.i(LOG_TAG, "this when loadInBackGround calling...");
        //url is empty return null.
        if (mUrl == null) {
            return null;
        }
        // return list of items that i need.
        return QueryUtils.fetchArticlesData(mUrl);
    }
}
