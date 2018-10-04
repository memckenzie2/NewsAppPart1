package com.example.android.newsapppart1;

import android.text.TextUtils;
import android.util.Log;

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
import java.nio.charset.Charset;

/**
 * Based on class from Pokemon app from udacity nano-degree: https://github.com/udacity/Pokemon/blob/master/app/src/main/java/udacity/pokemon/HttpHandler.java
 */

public class HTTPHandler {

    /** Tag for the log messages */
    public static final String LOG_TAG_MSSG = MainActivity.class.getSimpleName();

    //Required empty contructor
    public HTTPHandler(){

    }

    public String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early with blank jsonResponse.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //Start connection and set timeout in case of no/slow response
            urlConnection = (HttpURLConnection) url.openConnection();
            //Set a get request which retrieves information
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (Guardian API uses standard response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readStringFromStream(inputStream);
            } else {
                Log.e(LOG_TAG_MSSG, "Error response code from Guardian API: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG_MSSG, "Problem retrieving the Guardian API JSON results.", e);
        } finally {
            //If connection establish and input stream opened, disconnect/close both
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        //Return the current value of jsonReponse. Will be empty string if any errors encountered
        return jsonResponse;
    }

    /**
     * Also based on function from Soonami app from udacity nano-degree: https://github.com/udacity/ud843_Soonami/blob/solution/app/src/main/java/com/example/android/soonami/MainActivity.java
     * Convert the InputStream into a String of the JSON response from the server.
     */
    private String readStringFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            //While still lines in stream, keep adding lines to String version of JSON Response
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        //Converts stream out into string and returns value
        return output.toString();
    }

    /**
     * Also based on function from Soonami app from udacity nano-degree: https://github.com/udacity/ud843_Soonami/blob/solution/app/src/main/java/com/example/android/soonami/MainActivity.java
     * Return a NewsData object by parsing out information about the first earthquake from the input earthquakeJSON string.
     */
    private NewsData extractNewsStoryFromJson(String newsStoryJSON) {
        // If the JSON string is empty or null, then return early with null result.
        if (TextUtils.isEmpty(newsStoryJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(newsStoryJSON);
            JSONArray responseArray = baseJsonResponse.getJSONArray("response");

            // If there are results in the features array
            if (responseArray.length() > 0) {
                // Extract out the first feature (which is an earthquake)
                JSONObject response = responseArray.getJSONObject(0);
                JSONObject results = response.getJSONObject("response");

                // Extract out the title, time, and tsunami values
                String title = results.getString("webTitle");
                String date = results.getString("webPublicationDate");
                String webURL = results.getString("webUrl");

                // Create a new {@link Event} object
                return new NewsData(title, null, date , webURL);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG_MSSG, "Problem parsing the Guardian API JSON results", e);
        }
        return null;
    }


    /**
     * Based on function from Soonami app from udacity nano-degree: https://github.com/udacity/ud843_Soonami/blob/solution/app/src/main/java/com/example/android/soonami/MainActivity.java
     * Returns new URL object from the string URL parameter.
     */
    public URL makeUrl(String stringUrl) {
        URL newURL = null;
        try {
            newURL = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG_MSSG, "Error with creating URL from input.", exception);
            return null;
        }
        return newURL;
    }
}
