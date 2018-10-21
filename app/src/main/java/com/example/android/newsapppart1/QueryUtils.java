package com.example.android.newsapppart1;

/**
 * Implementation based on the HTTPHandler class from Pokemon app from udacity nano-degree.
 * The source code for that project can be found here:
 * https://github.com/udacity/Pokemon/blob/master/app/src/main/java/udacity/pokemon/HttpHandler.java
 * <p>
 * Also on the Soonami and Earthquake app's QueryUtils from the udacity nano-degree.
 * The source code for those projects can be found here:
 * https://github.com/udacity/ud843_Soonami/blob/solution/app/src/main/java/
 * and here:
 * https://github.com/udacity/ud843-QuakeReport
 */

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
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    /**
     * Tag for the log messages for troubleshooting
     */
    public static final String LOG_TAG_MSSG = QueryUtils.class.getSimpleName();

    /*Empty constructor set to private to prevent creating an instance of Query Utils.
     * This class is only used for static variables and methods with no need for an object instance of the class*/
    private QueryUtils() {

    }

    /**
     * Query the Guardian Api using a String URL passed as a parameter and return the results a List of NewsData onjects.
     * Use generic List to support both ArrayLists and LinkedList objects
     */
    public static List<NewsData> fetchNewsData(String guardianRequestUrl) {

        //Create a URL object from the given String parameter
        URL url = createUrl(guardianRequestUrl);

        //Attempt to establish HTTP Request to retrieve JSON Output
        ArrayList<NewsData> storiesList = new ArrayList<NewsData>();
        String jsonRawResponse = "";
        try {
            jsonRawResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG_MSSG, "Problem making the HTTP request to Guardian API.", e);
        }

        //Log JSON Response for toubleshooting
        Log.e(LOG_TAG_MSSG, "Response from url: " + jsonRawResponse);

        //Extracts the news stories as a list from the raw JSON output given by the Guardian's API
        storiesList = (ArrayList<NewsData>) extractNewsStoriesList(jsonRawResponse);
        return storiesList;
    }

    /*
     * Takes a string and formats into a URL object
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG_MSSG, "Error while creating URL from String", e);
        }
        return url;
    }

    //Makes an HTTP request to the given url and return the JSON response as a String
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early with an empty string
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //Create URL connection and read in results
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            //Send a get request to the API
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Response code 200 occurs for a succesful query, so read in the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                //Gets input stream to read in successful query
                inputStream = urlConnection.getInputStream();

                //Parse the input stream into a String of the JSON response
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG_MSSG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG_MSSG, "Problem parsing the Guardian API JSON results", e);
        } finally {
            //Close the URLConnection and inputStream in case they were opened. This will run for successful or unsuccessful queries.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Takes the Api's response as an input stream and converts it into a String object containing the JSON Response
     * If this throws an IO exception it is caught up in the makeHttpRequest() function that calls it.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        //If an input stream exists, uses a buffered reader to add a line at a time.
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        //Converts the output to a String for return
        return output.toString();
    }

    private static List<NewsData> extractNewsStoriesList(String jsonRawResponse) {
        ArrayList<NewsData> storiesList = new ArrayList<NewsData>();

        try {
            JSONObject newsStories = new JSONObject(jsonRawResponse);
            JSONObject responseObject = newsStories.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // If there are results in the response array, pull individual story's details and add to a list of NewsData objects
            for (int i = 0; i < resultsArray.length(); i++) {
                // Extract out the first result(which is a single news story)
                JSONObject results = resultsArray.getJSONObject(i);


                // Extract out the title, section, web publication date, and story url
                String title = results.getString("webTitle");
                String section = results.getString("sectionName");
                String date = results.getString("webPublicationDate");
                String webURL = results.getString("webUrl");
                date = date.substring(5, 7) + "-" + date.substring(8, 10) + "-" + date.substring(0, 4);

                //Extracts the tag JSON array containing the authors/contributors and loops over them to create a final author string
                String author = "Contributors: ";
                JSONArray tagArray = results.getJSONArray("tags");
                for (int j = 0; j < tagArray.length(); j++) {
                    JSONObject tags = tagArray.getJSONObject(j);
                    //Extract contributors from tags
                    author = author + tags.getString("webTitle") + "; ";
                }

                if (author == "Contributors: ") {
                    author = "";
                }


                // Create a new {@link NewsData} object
                storiesList.add(new NewsData(title, author, section, date, webURL));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG_MSSG, "Problem parsing the Guardian API JSON results", e);
        }

        return storiesList;
    }

}
