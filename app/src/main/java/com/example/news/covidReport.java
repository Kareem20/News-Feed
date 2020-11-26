package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news.apiFactory.ApiUtils;
import com.example.news.apiFactory.reportClient;
import com.example.news.modals.Country;
import com.example.news.modals.Worldwide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class covidReport extends AppCompatActivity {

    /*TextView that is the number of deaths of specific country. */
    private TextView death;

    /*TextView that is the number of cases of specific country. */
    private TextView cases;

    /*TextView that is the number of recovered of specific country. */
    private TextView recovered;

    /*TextView that is the number of  all deaths of worldwide. */
    private TextView allDeaths;

    /*TextView that is the number of  all cases of worldwide. */
    private TextView allCases;

    /*TextView that is the number of  all recovered of worldwide. */
    private TextView allRecovered;

    /*ImageView for the coronaVirus.*/
    private ImageView coronaVirusImage;

    /*Spinner for all countries. */
    private Spinner spinner;

    /*Object of reportClient interface to use it the retrofit process*/
    private reportClient mService;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_report);

        // find a reference to deaths on specific country.
        death = findViewById(R.id.death);
        // find a reference to cases on specific country.
        cases = findViewById(R.id.cases);
        // find a reference to recovered on specific country.
        recovered = findViewById(R.id.recovered);
        // find a reference to all deaths in worldwide.
        allDeaths = findViewById(R.id.tx_worldwide_number_deaths);
        // find a reference to all cases in worldwide.
        allCases = findViewById(R.id.tx_worldwide_number_cases);
        // find a reference to all recovered in worldwide.
        allRecovered = findViewById(R.id.tx_worldwide_number_recovered);
        //find a reference to the image of coronaVirus.
        coronaVirusImage = findViewById(R.id.imageView_coronaVirus);
        //find a reference to the spinner of all countries.
        spinner = findViewById(R.id.sp_countries);
        //declaration of mService by method in ApiUtils
        mService = ApiUtils.getSOService();

        //array of locale to get all available countries.
        Locale[] locales = Locale.getAvailableLocales();
        //arrayList to add all country in it
        // and ues it in spinner.
        ArrayList<String> countries = new ArrayList<>();
        //for loop to add country in arrayList.
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country) && !country.equals("World")) {
                countries.add(country);
            }
        }
        //sort arrayList alphabetically.
        Collections.sort(countries);
        //add arrayList to spinner.
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the your spinner
        spinner.setAdapter(countryAdapter);

        //check if user choose item or not.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //if user choose item,then make process of fetching the data.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //start the animation of image.
                coronaVirusImage.startAnimation(AnimationUtils.loadAnimation(covidReport.this, R.anim.rotation));
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //load the data from api.
                        loadData();
                    }
                }, 500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*
     * method to load the data from apis
     * and put it in user interface.
     * */
    public void loadData() {
        //load the data of specific country.
        //make this process in another thread.
        mService.getCountry((spinner.getSelectedItem().toString())).enqueue(new Callback<Country>() {
            //if the process of fetching the data call this method (onResponse).
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "posts loaded from API");
                    //put the data in textViews.
                    death.setText(String.valueOf(response.body().getDeaths()));
                    cases.setText(String.valueOf(response.body().getCases()));
                    recovered.setText(String.valueOf(response.body().getRecovered()));
                } else {
                    int statusCode = response.code();
                    if (statusCode == 404) {
                        Toast.makeText(covidReport.this, "Can not Find this Country."
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(covidReport.this, "Error in onResponse....!" + statusCode
                                , Toast.LENGTH_LONG).show();
                    }
                }
            }

            //if the process of fetching the data is failed,then call this method (onFailure).
            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                boolean internetConnection = checkInternet();
                if (!internetConnection) {
                    Toast.makeText(covidReport.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                } else {
                    Log.e(covidReport.class.getName(), "onFailure: " + t.getMessage() + "   ", t);
                    Toast.makeText(covidReport.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //load the data of worldwide.
        //make this process in another thread.
        mService.getAll().enqueue(new Callback<Worldwide>() {
            //if the process of fetching the data call this method (onResponse).
            @Override
            public void onResponse(Call<Worldwide> call, Response<Worldwide> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "posts loaded from API all");
                    //put the data in textViews.
                    allDeaths.setText(String.valueOf(response.body().getDeaths()));
                    allCases.setText(String.valueOf(response.body().getCases()));
                    allRecovered.setText(String.valueOf(response.body().getRecovered()));
                } else {
                    int statusCode = response.code();
                    if (statusCode == 404) {
                        Toast.makeText(covidReport.this, "Can not Find this Country."
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(covidReport.this, "Error in onResponse....!" + statusCode
                                , Toast.LENGTH_LONG).show();
                    }
                }
            }

            //if the process of fetching the data is failed,then call this method (onFailure).
            @Override
            public void onFailure(Call<Worldwide> call, Throwable t) {
                boolean internetConnection = checkInternet();
                if (!internetConnection) {
                    Toast.makeText(covidReport.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                } else {
                    Log.e(covidReport.class.getName(), "onFailure: " + t.getMessage() + "   ", t);
                    Toast.makeText(covidReport.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*method to check the internet Connection.*/
    private boolean checkInternet() {
        // get a reference to the connectivityManager to check state of network connectivity.
        ConnectivityManager comMang = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on the currently active default data network.
        NetworkInfo networkInfo = comMang.getActiveNetworkInfo();
        // if there is a network connection , fetch data
        return networkInfo != null && networkInfo.isConnected();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}