package com.example.news.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.news.R;

/*
* class to build the uri for main activity
* and all categories by it's name
* */
public class articlesUriBuilder {
    /**
     * Get Uri.Builder based on stored SharedPreferences.
     * @param context Context used to access SharedPreferences
     * @return Uri.Builder
     */
    public static Uri.Builder getPreferredUri(Context context) {

        //get reference of shared Preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //get the minimum number of article that user want form SharedPreferences.
        String miniNumberOfArticles =sharedPreferences.getString(context.getString(R.string.settings_min_numbers_of_article_key)
                ,context.getString(R.string.settings_min_numbers_of_article_default));

        //get the how order articles that user want form SharedPreferences.
        String orderBy = sharedPreferences.getString(context.getString(R.string.settings_order_by_article_key)
        ,context.getString(R.string.settings_order_by_default));

        //get the start date of article that user want form SharedPreferences.
        String fromDate = sharedPreferences.getString(context.getString(R.string.settings_from_date_key)
                ,context.getString(R.string.settings_from_date_default));

        //build the uri and add it's components.
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("section",context.getString(R.string.settings_order_by_default))
                .appendQueryParameter("order-by", orderBy)
                .appendQueryParameter("from-date",fromDate)
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("show-fields","thumbnail,trailText,bodyText,lastModified")
                .appendQueryParameter("page-size",miniNumberOfArticles)
                .appendQueryParameter("api-key", "164ddb0c-58d3-4abb-8e7f-b4dfa93991e4");

        return uriBuilder;
    }
    /**
     * this for different categories.
     * Returns String Url for query
     * @param context Context used to access getPreferredUri method
     */
    public static String getPreferredUrl(Context context,String section) {
        Uri.Builder uriBuilder = getPreferredUri(context);
        return uriBuilder.appendQueryParameter("section",section).toString();
    }
}
