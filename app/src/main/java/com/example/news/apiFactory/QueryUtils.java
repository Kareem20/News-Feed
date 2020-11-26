package com.example.news.apiFactory;

import android.util.Log;

import com.example.news.modals.newsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving News articles from the guardian.
 */
public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();

    /*
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /*
     * put the api data and return a list of news.
     */
    public static List<newsList> fetchArticlesData(String requestUrl) {
        // the time of the progress Bar.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create an object
        // Return the object as the result fo the
        return extractNews(jsonResponse);
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // if the URl is null , then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //this lines (from 161 to 164) to get the information of articles from server.
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            //this line to make connection with internet .
            urlConnection.connect();
            //if the request was successful (response will be 200)
            // then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem to return the articles JSON result.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();

    }

    /*
     * Create the URL .
     * */
    private static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL ( يا غبي)", exception);
            return null;
        }
        return url;
    }

    private static ArrayList<newsList> extractNews(String articlesJSON) {

        // Create an empty ArrayList that we can start adding  articles.
        ArrayList<newsList> newsLists = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject obj = new JSONObject(articlesJSON);

            // Extract the JSONObject associated with the key called "response"
            JSONObject response = obj.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results"
            JSONArray results = response.getJSONArray("results");

            // For each element in the resultsArray,create a{@link News} object
            for (int i = 0; i < results.length(); i++) {
                // Get a single news at position i within the list of news
                JSONObject object = results.getJSONObject(i);

                // For a given news, extract the value for the key called "webTitle"
                String titleOfArticle = object.getString("webTitle");

                // For a given news, extract the value for the key called "webUrl"
                String url = object.getString("webUrl");

                // For a given news, extract the value for the key called "sectionName"
                String section = object.getString("sectionName");

                // For a given news, extract the value for the key called "webPublicationDate"
                String date = object.getString("webPublicationDate");

                // For a given news, if it contains the key called "fields", extract JSONObject
                // associated with the key "fields"
                String image = null;
                String trailText = null;
                String articleText = null;
                String lastModified = null;
                if (object.has("fields")) {
                    // Extract the JSONObject associated with the key called "fields"
                    JSONObject imageObject = object.getJSONObject("fields");

                    // If there is the key called "image of article", extract the value for the key called "thumbnail"
                    if (imageObject.has("thumbnail")) {
                        image = imageObject.getString("thumbnail");
                    }
                    //If there is the key called "trailText", extract the value for the key called "trailText".
                    if (imageObject.has("trailText")) {
                        trailText = imageObject.getString("trailText");
                    }
                    //If there is the key called "bodyText", extract the value for the key called "bodyText".
                    if (imageObject.has("bodyText")) {
                        articleText = imageObject.getString("bodyText");
                    }
                    //If there is the key called "lastModified", extract the value for the key called "lastModified".
                    if (imageObject.has("lastModified")) {
                        lastModified = imageObject.getString("lastModified");
                    }
                }

                // For a given news, if it contains the key called "tags", extract JSONArray
                // associated with the key "tags"
                String author = null;
                String imageOfAuthor = null;
                if (object.has("tags")) {
                    // Extract the JSONArray associated with the key called "tags"
                    JSONArray tags = object.getJSONArray("tags");
                    if (tags.length() != 0) {
                        // Extract the first JSONObject in the tagsArray
                        JSONObject firstTagsItem = tags.getJSONObject(0);
                        // Extract the value for the key called "webTitle"
                        author = firstTagsItem.getString("webTitle");

                        if (firstTagsItem.has("bylineImageUrl")) {
                            // Extract the value for the key called "bylineImageUrl"
                            imageOfAuthor = firstTagsItem.getString("bylineImageUrl");
                        }
                    }

                }
                // list of newList to add items of article.
                newsList list = new newsList(titleOfArticle, date, url, section, image, author, trailText
                        , articleText, lastModified, imageOfAuthor);
                newsLists.add(list);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the New JSON results -__-", e);
        }

        // Return the list of articles.
        return newsLists;
    }

}
