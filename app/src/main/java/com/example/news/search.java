package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Adapters.EmptyRecyclerView;
import com.example.news.Adapters.NewsAdapter;
import com.example.news.apiFactory.newsLoader;
import com.example.news.modals.newsList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class search extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<newsList>>
        , SwipeRefreshLayout.OnRefreshListener {

    // Adapter for the list of news
    private NewsAdapter mAdapter;
    //Constant value for the news loader ID.
    private static final int NEWS_LOADER_ID = 1;
    // The {@link SwipeRefreshLayout} that detects swipe gestures and
    // triggers callbacks in the app.
    private SwipeRefreshLayout swipeRefreshLayout;
    //textView for no Internet Connection.
    private TextView noInternetConnection;
    //ImageView for no Internet Connection.
    private ImageView imageViewNoInternetConnection;
    //ImageView for no result found.
    private ImageView imageViewNoSearch;
    //TextView for no result (text).
    private TextView noResult;
    //TextView for no result (text).
    private TextView noMatchingOne;
    //TextView for no result (text).
    private TextView noMatchingTwo;
    // Loading indicator that is displayed before the first load is completed
    private View loadingIndicator;
    //the Search text that user type it in search bar.
    private String search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //get the text that user search about it.
        Intent i = getIntent();
        search = i.getStringExtra("search");

        //find reference of swipe Refresh.
        swipeRefreshLayout = findViewById(R.id.swiperefresh2);
        swipeRefreshLayout.setOnRefreshListener(this);
        //find reference of no Internet connection (text).
        noInternetConnection = findViewById(R.id.no_internet2);
        //find reference of image of no Internet connection.
        imageViewNoInternetConnection = findViewById(R.id.no_internet_icon);
        //find reference of image of no result found.
        imageViewNoSearch = findViewById(R.id.no_search_icon);
        //find reference of no result found (text).
        noResult = findViewById(R.id.no_result);
        //find reference of no result found (text).
        noMatchingOne = findViewById(R.id.no_matching);
        //find reference of no result found (text).
        noMatchingTwo = findViewById(R.id.no_matching2);
        //find reference of progress bar.
        loadingIndicator = findViewById(R.id.prograss_bar2);

        // find a reference to the listView recycler.
        EmptyRecyclerView listViewRecycler = findViewById(R.id.list2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listViewRecycler.setHasFixedSize(true);
        // Set the layoutManager on the {@link RecyclerView}
        listViewRecycler.setLayoutManager(layoutManager);
        // Create a new adapter {@link ArrayAdapter} of news
        mAdapter = new NewsAdapter(this, new ArrayList<newsList>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listViewRecycler.setAdapter(mAdapter);
        checkInternet();

        //go back to Main activity.
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    private boolean checkInternet() {
        // get a reference to the connectivityManager to check state of network connectivity.
        ConnectivityManager comMang = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on the currently active default data network.
        NetworkInfo networkInfo = comMang.getActiveNetworkInfo();
        // if there is a network connection , fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            androidx.loader.app.LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            imageViewNoSearch.setVisibility(View.GONE);
            imageViewNoInternetConnection.setVisibility(View.GONE);
            return true;
        } else {
            loadingIndicator.setVisibility(View.GONE);
            imageViewNoInternetConnection.setVisibility(View.VISIBLE);
            imageViewNoSearch.setVisibility(View.GONE);
            noInternetConnection.setText(R.string.No_Internet_Connection);
            return false;

        }
    }

    @NonNull
    @Override
    public Loader<List<newsList>> onCreateLoader(int id, @Nullable Bundle args) {

        Uri.Builder uriBuilder = new Uri.Builder();

        uriBuilder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("q", search)
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("show-fields", "thumbnail,trailText,bodyText,lastModified")
                .appendQueryParameter("page-size", "15")
                .appendQueryParameter("api-key", "164ddb0c-58d3-4abb-8e7f-b4dfa93991e4");


        return new newsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<newsList>> loader, List<newsList> data) {

        loadingIndicator.setVisibility(View.GONE);

        mAdapter.clear();
        // If there is a valid list of {@link articles}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            appearSearchGroup();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<newsList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (checkInternet()) {
            noInternetConnection.setText("");
            Toast.makeText(this, "Articles is Updated.", Toast.LENGTH_SHORT).show();
        } else {
            if (mAdapter.getItemCount() == 0) {
                imageViewNoInternetConnection.setVisibility(View.VISIBLE);
                noInternetConnection.setText(R.string.No_Internet_Connection);
            } else {
                imageViewNoInternetConnection.setVisibility(View.GONE);
                noInternetConnection.setText("");
            }
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    // Go back to the MainActivity when up button in action bar is clicked on.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent myIntent = new Intent(this, MainActivity.class);
            search.this.startActivity(myIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void appearSearchGroup() {
        imageViewNoSearch.setVisibility(View.VISIBLE);
        noResult.setText(R.string.No_result_found);
        noMatchingOne.setText(R.string.No_matching_1);
        noMatchingTwo.setText(R.string.No_matching_2);
    }

}
