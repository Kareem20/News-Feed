package com.example.news.Adapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.R;
import com.example.news.apiFactory.newsLoader;
import com.example.news.modals.newsList;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for the base structure for any article fragment.
 * */
public class baseArticles extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<newsList>>, SwipeRefreshLayout.OnRefreshListener {

    /**
     * Constant value for the news loader ID.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;

    /**
     * TextView that is displayed when the recycler view is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * The {@link SwipeRefreshLayout} that detects swipe gestures and
     * triggers callbacks in the app.
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //image for no internet connection.
    private ImageView imageView;
    /**
     * Loading indicator that is displayed before the first load is completed
     */
    private View loadingIndicator;

    /*Empty constructor*/
    public baseArticles() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);

        //find a reference to the image of no internet connection.
        imageView = rootView.findViewById(R.id.no_internet);

        //find a reference to the progress bar.
        loadingIndicator = rootView.findViewById(R.id.prograss_bar);

        // find a reference to the listView.
        EmptyRecyclerView listViewRecycler = rootView.findViewById(R.id.list_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listViewRecycler.setHasFixedSize(true);
        // Set the layoutManager on the {@link RecyclerView}
        listViewRecycler.setLayoutManager(layoutManager);

        // find a reference to the textView(NO NEWS FOUND).
        mEmptyStateTextView = rootView.findViewById(R.id.empty_text);
        listViewRecycler.setEmptyView(mEmptyStateTextView);

        // find a reference to the Swipe for refresh.
        mSwipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Create a new adapter {@link ArrayAdapter} of news
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<newsList>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listViewRecycler.setAdapter(mAdapter);
        boolean internetConnection = checkInternet();
        if (internetConnection) {
            androidx.loader.app.LoaderManager loaderManager = getLoaderManager();
            //fetching the data.
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            //disappear image of no Internet Connection.
            imageView.setVisibility(View.GONE);
        } else {
            //if there is no Internet Connection
            // appear the image of no internet connection and return false..
            loadingIndicator.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }


    /*
     * method to check if there is internet connection of not
     * if yes return true
     * if not return false.
     * */
    private boolean checkInternet() {
        // get a reference to the connectivityManager to check state of network connectivity.
        ConnectivityManager comMang = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on the currently active defualt data network.
        NetworkInfo networkInfo = comMang.getActiveNetworkInfo();
        // if there is a network connection , fetch data
        return networkInfo != null && networkInfo.isConnected();
    }


    /*
     * three implementation method to get data from api
     * and do this action in separate thread.
     *
     * onCreateLoader for the fetching data.
     * onLoadFinished for the action after fetching data.
     * onLoaderReset for the re-fetching the data.
     * */
    @NonNull
    @Override
    public androidx.loader.content.Loader<List<newsList>> onCreateLoader(int id, @Nullable Bundle args) {

        Uri.Builder uriBuilder = articlesUriBuilder.getPreferredUri(getContext());

        return new newsLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<newsList>> loader, List<newsList> data) {

        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_News);
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<newsList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /*
     * method is called when user refresh the fragments.
     * and called the the process of fetching data again from api.
     * */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        //check internet Connection first.
        if (checkInternet()) {
            imageView.setVisibility(View.GONE);
            // if there is internet Connection,then fetch the data.
            androidx.loader.app.LoaderManager loaderManager = getLoaderManager();
            //fetching the data.
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            Toast.makeText(getActivity(), "Articles is Updated.", Toast.LENGTH_SHORT).show();
        } else {
            // if there is internet Connection,then check if there is data on
            // the fragment or not.
            if (mAdapter.getItemCount() == 0) {
                // if not ,then show "no Internet Connection" message.
                imageView.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setText(R.string.No_Internet_Connection);
            } else {
                //if there is data , then don't show "no Internet Connection" message.
                imageView.setVisibility(View.GONE);
                mEmptyStateTextView.setText("");
            }
            //And show the Toast.
            Toast.makeText(getActivity(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
